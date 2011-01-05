/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest;

import java.awt.event.KeyEvent;
import jgame.*;
import jgame.impl.JGEngineInterface;
import jgame.platform.*;
import jgtest.ui.UIElements;

/** Space Run III, a variant on Space Run, illustrating scrolling and wrapping
 * playfield. */
public class SpaceRunIII extends StdGame {

    private Player player;
    private int viewGameSpeed = 2;
    private int xOffsetDefault = 500;
    private int xView = xOffsetDefault;

    public static void main(String[] args) {
        new SpaceRunIII(parseSizeArgs(args, 0));
    }

    public SpaceRunIII() {
        initEngineApplet();
    }

    public SpaceRunIII(JGPoint size) {
        initEngineComponent(size.x, size.y);
    }

    public void initCanvas() { // 20,15,32,32
        setCanvasSettings(30, 15, 32, 32, null, null, null);
//        if (isMidlet()) {
//            setScalingPreferences(3.0 / 4.0, 4.0 / 3.0, 0, 7, 0, 7);
//        }
    }

    public void initGame() {
        defineMedia("gr.tbl");
        if (isMidlet()) {
            setFrameRate(18, 1);
            setGameSpeed(2.0);
        } else {
            setFrameRate(35, 1);
        }

        startgame_ingame = true;
        leveldone_ingame = true;
        title_color = JGColor.yellow;
        title_bg_color = new JGColor(140, 0, 0);
        title_font = new JGFont("Arial", 0, 20);
        setHighscores(10, new Highscore(0, "nobody"), 15);
        highscore_title_color = JGColor.red;
        highscore_title_font = new JGFont("Arial", 0, 20);
        highscore_color = JGColor.yellow;
        highscore_font = new JGFont("Arial", 0, 16);
    }

    public void defineLevel() {
        removeObjects(null, 0);
        xView = xOffsetDefault;

        leveldone_ingame = true;
        setPFSize(400, 16);
        setPFWrap(false, false, 0, 0);
        System.out.println("dsa: " + pfTilesY());
        int tunnelpos = pfTilesY() / 2 + 1;
        fillBG(".");
        String[] map = LevelDesign.TEST_LEVEL;
        setTilesMulti(0, 0, map);
//        int firstpart = 15;
//        int oldpos = 0;
//        for (int x = 0; x < pfTilesX(); x++) {
//            for (int y = tunnelpos; y < tunnelpos + tunnelheight; y++) {
//                setTile(x, y, "");
//            }
//            if (firstpart > 0) {
//                firstpart--;
//            } else {
//
//                oldpos = tunnelpos;
//                tunnelpos += random(-1, 1, 2);
//                if (tunnelpos < 1) {
//                    tunnelpos = 1;
//                }
//                if (tunnelpos + tunnelheight >= pfTilesY() - 1) {
//                    tunnelpos = pfTilesY() - tunnelheight - 1;
//                }
//            }
//        }
        player = new Player(32, pfHeight() / 2 - 100, 3, this);
    }

    public Player getPlayer() {
        return player;
    }

    public void initNewLife() {
        defineLevel();
    }

    public void startGameOver() {
        removeObjects(null, 0);
    }

    public void doFrameInGame() {
        moveObjects();
        checkCollision(2 + 4, 1); // enemies, pods hit player
        checkBGCollision(2, 1); // bg hits player

        xView += viewGameSpeed;

        setViewOffset((int) xView, (int) getObject("player").y, true);

        // Player off screen. Push player.
        if (xView > (getPlayer().x + 500 - 32)) {
            getPlayer().x = getPlayer().x + 5;

            if (xView > (getPlayer().x + 500)) {
                defineLevel();
            }
        }
    }

    public void incrementLevel() {
        if (level < 10) {
            level++;
        }
        stage++;
    }
    JGFont scoring_font = new JGFont("Arial", 0, 8);

    public void paintFrameLifeLost() {
        setColor(title_bg_color);
        drawRect(160, 50, seqtimer * 7, seqtimer * 5,
                true, true, false);
        int ypos = posWalkForwards(-24, viewHeight(), seqtimer,
                80, 50, 20, 10);
        drawString("You're hit !", 160, ypos, 0,
                getZoomingFont(title_font, seqtimer, 0.2, 1 / 40.0),
                title_color);
    }

    public void paintFrameGameOver() {
        setColor(title_bg_color);
        setStroke(1);
        drawRect(160, 51, seqtimer * 2, seqtimer / 2, true, true, false);
        drawString("Game Over !", 160, 50, 0, getZoomingFont(title_font, seqtimer, 0.2, 1 / 120.0), title_color);
    }

    public void paintFrameStartGame() {
        drawString("Get Ready!", 160, 50, 0,
                getZoomingFont(title_font, seqtimer, 0.2, 1 / 80.0),
                title_color);
    }

    public void paintFrameStartLevel() {
        drawString("Stage " + (stage + 1), 160, 50 + seqtimer, 0,
                getZoomingFont(title_font, seqtimer, 0.2, 1 / 80.0),
                title_color);
    }

    public void paintFrameLevelDone() {
        drawString("Start over!", 160, 50, 0,
                getZoomingFont(title_font, seqtimer + 80, 0.2, 1 / 80.0),
                title_color);
    }

    public void paintFrameTitle() {
        drawString("Space Run III", 160, 50, 0,
                getZoomingFont(title_font, seqtimer + 20, 0.3, 0.03),
                title_color);
        drawString("Press " + getKeyDesc(key_startgame) + " to start", 160, 120, 0,
                getZoomingFont(title_font, seqtimer + 5, 0.3, 0.03),
                title_color);
        if (!isMidlet()) {
            drawString("Press " + getKeyDesc(key_gamesettings) + " for settings",
                    160, 160, 0, getZoomingFont(title_font, seqtimer, 0.3, .03),
                    title_color);
        }
    }

    public class Player extends JGObject {

        double speed;
        double jumpHeight = 16;
        int jumptime = 0;
        int falltime = 0;
        int bullettime = 0;
        int dir = 1;
        int startHeight = -1;
        boolean maxHeight = false;
        boolean jumping_up = false, jumping_down = false;

        public Player(double x, double y, double speed, JGEngineInterface engine) {
            super("player", false, x, y, 1, "player_l1", 0, 0, 32, 32, engine);
            this.speed = speed;

//            if (this.speed == 0) {
            this.speed = 8;
//            }
        }

        public void move() {
            moveNorm();
//            System.out.println("("+getOffscreenMarginX()+","+getOffscreenMarginY()+")");
            if (!isOnPF(32, 32)) {
                levelDone();
            }
        }

        public void moveNorm() {
            
            snapToGrid(speed / 2, 0); // ensure we can fall through small holes
            JGRectangle ts = getTiles();
            JGRectangle cts = getCenterTiles();
            int cid = 0;
            for (int tdx = 0; tdx < ts.width; tdx++) {
                cid |= getTileCid(ts.x + tdx, cts.y + 1);
            }
            stopAnim();
            if (jumptime <= 0) {
                jumping_up = false;
                jumping_down = false;
//                if (isYAligned(speed)) {
                if ((cid & 3) == 0) {
                    /* no support -> fall */
                    y += jumpHeight;
                    // make sure the player is tile aligned when it falls
                    // off a tile, or it might find support when it should
                    // fall through a hole.
                    snapToGrid(speed / 2.0, 0);
                }
                /* stand on ground */
                snapToGrid(0, speed);
                if (getKey(key_left) && (xView < x + 500 - 32)) {
                    setAnim("player_l");
                    startAnim();
                    dir = -1;
                    x -= speed;
                }
                if (getKey(key_right) && (xView + 900 > x + 500 - 32)) {
                    setAnim("player_r");
                    startAnim();
                    dir = 1;
                    x += speed;
                }
                if ((getKey(key_up) || getKey(KeyEvent.VK_SPACE)) && cid > 0) {
                    jumptime = 30;
                    startHeight = cts.y;
                }

//                } else {
//                    /* fall until aligned */
//                    y += speed;
//                }
            } else { /* jumping */
                if (jumptime > 15 && (getKey(key_up) || getKey(KeyEvent.VK_SPACE)) && startHeight - cts.y < 6 && startHeight != -1) { /* up */
                    y -= jumpHeight;
                    jumping_up = true;
                    jumping_down = false;
                } else { /* down */


                    cid = 0;
                    for (int tx = 0; tx < ts.width; tx++) {
                        cid |= getTileCid(ts.x + tx, cts.y + 1);
                    }

                    if (cid == 0) {
                        y += jumpHeight;
                        jumping_up = false;
                        jumping_down = true;
                    }
                    /* see if we hit the ground */
                    if (isYAligned(jumpHeight)) {

                        if ((cid & 3) != 0) {
                            jumptime = 0;
                        }
                    }
                }
                if (startHeight - cts.y >= 6) {
                    startHeight = -1;
                }
                if (getKey(key_left) && (xView < x + 500 - 32)) {
                    setAnim("player_l");
                    startAnim();
                    x -= speed;
                    dir = -1;
                }
                if (getKey(key_right) && (xView + 900 > x + 500 - 32)) {
                    setAnim("player_r");
                    startAnim();
                    x += speed;
                    dir = 1;
                }
                if (!getKey(key_up) && !getKey(KeyEvent.VK_SPACE)) {
                    startHeight = -1;
                    jumptime--;
                }
            }
        }

        public void hit(JGObject obj) {
            if (and(obj.colid, 2)) {
                lifeLost();
            } else {
                score += 5;
                obj.remove();

                UIElements.getInstance().setP1Score(score);
                UIElements.getInstance().setP2Score(score);
                new StdScoring("pts", obj.x, obj.y, 0, -1.0, 40, "5 pts", scoring_font, new JGColor[]{JGColor.red, JGColor.yellow}, 2, getEngine());
            }
        }

        public void hit_bg(int tilecid, int tx, int ty, int txsize, int tysize) {
//            System.out.println("HER?");
            if ((tilecid & 2) != 0) {
                /* what we should do here is complex and depends on our state.
                 * But, we only need to handle bumping into things here.
                 * Support is handled by the regular move routine.  We need
                 * concern ourselves only with type 2 material.
                 *
                 * if we are jumping up, we should be blocked by type 2
                 * material.  If we bump our head on type 2 material the jump
                 * should be aborted.
                 *
                 * if we are jumping down or are not jumping, we should be
                 * blocked on our sides by type 2 material.
                 */
                if (jumping_up) {
                    if (isYAligned(speed * 2)) {
                        boolean bump_head = false;
                        JGRectangle cts = getCenterTiles();
                        for (int tdx = 0; tdx < txsize; tdx++) {
                            boolean topwall =
                                    (getTileCid(tx + tdx, cts.y - 1) & 2) != 0;
                            boolean botwall =
                                    (getTileCid(tx + tdx, cts.y) & 2) != 0;
                            if (topwall && !botwall) {
                                bump_head = true;
                                break;
                            }
                        }
                        if (bump_head) {
                            jumptime = 0;
                            snapToGrid(speed, speed);
                        } else {
                            snapToGrid(speed, 0);
                        }
                    } else {
                        snapToGrid(speed, 0);
                    }
                } else {
                    snapToGrid(speed, 0);
                }
                /*for (int tdx=0; tdx<txsize; tdx++) {
                cid |= canvas.getTileCid(tx+tdx, ty);
                }*/
            }
            // lifeLost();
        }
    }

    class BombDropper extends JGObject {

        public BombDropper(JGEngineInterface engine) {
            super("enemy", true, random(pfWidth() / 3, pfWidth()),
                    random(0, pfHeight()), 2, "enemy", engine);
            setSpeed(random(-0.7, -0.3), random(-0.5, 0.5));
        }

        public void move() {
            if (random(0, 1) < 0.005) {
                JGPoint cen = getCenterTile();
                setTile(cen.x, cen.y, "#");
            }
        }
    }
}
