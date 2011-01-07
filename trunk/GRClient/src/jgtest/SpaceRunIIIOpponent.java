/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest;

import jb2dtest.ClientInfo;
import jgame.*;
import jgame.impl.JGEngineInterface;
import jgame.platform.*;
import jgtest.ui.UIElements;

/** Space Run III, a variant on Space Run, illustrating scrolling and wrapping
 * playfield. */
public class SpaceRunIIIOpponent extends StdGame {

    private Player player;
    private ClientInfo cInfo;

    public SpaceRunIIIOpponent(JGPoint size) {
        initEngineComponent(size.x, size.y);
        setFocusable(false);
    }

    public void initCanvas() {
        setCanvasSettings(30, 15, 32, 32, null, null, null);
//        if (isMidlet()) {
//            setScalingPreferences(3.0 / 4.0, 4.0 / 3.0, 0, 7, 0, 7);
//        }
    }

    public void setClientInfo(ClientInfo cInfo) {
        this.cInfo = cInfo;
    }

    public ClientInfo getClientInfo() {
        return cInfo;
    }

    public void initGame() {
        defineMedia("gr.tbl");
        setBGImage(0, "citynight_bg", false, false);
        setFrameRate(35, 1);

        startgame_ingame = true;
        leveldone_ingame = true;
        title_color = new JGColor(0, 168, 255);
        title_bg_color = new JGColor(140, 0, 0);
        title_font = new JGFont("Arial", 0, 50);
        setHighscores(10, new Highscore(0, "nobody"), 15);
        highscore_title_color = JGColor.red;
        highscore_title_font = new JGFont("Arial", 0, 20);
        highscore_color = JGColor.yellow;
        highscore_font = new JGFont("Arial", 0, 16);
    }

    public void defineLevel() {
        removeObjects(null, 0);

        leveldone_ingame = true;
        setPFSize(400, 16);
        setPFWrap(false, false, 0, 0);
        System.out.println("dsa: " + pfTilesY());
//        int tunnelpos = pfTilesY() / 2 + 1;
        fillBG(".");
        String[] map = LevelDesign.TEST_LEVEL;

        setTilesMulti(0, 0, map);


        player = new Player(32, pfHeight() / 2 - 32, 3, this);
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
        checkCollision(4, 1); // coin hit player
        checkBGCollision(2, 1); // bg hits player
        checkBGCollision(4, 1);

        if (getClientInfo() != null) {
            setViewOffset(getClientInfo().getPfx(), getClientInfo().getPfy(), true);
        } else {
            setViewOffset((int)getObject("player").x + 100, (int)getObject("player").y, true);

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
//        setColor(title_bg_color);
//        drawRect(160, 50, seqtimer * 7, seqtimer * 5,
//                true, true, false);
//        int ypos = posWalkForwards(-24, viewHeight(), seqtimer,
//                80, 50, 20, 10);
//        drawString("You're hit !", 160, ypos, 0,
//                getZoomingFont(title_font, seqtimer, 0.2, 1 / 40.0),
//                title_color);
    }

    public void paintFrameGameOver() {
//        setColor(title_bg_color);
//        setStroke(1);
//        drawRect(160, 51, seqtimer * 2, seqtimer / 2, true, true, false);
//        drawString("Game Over !", 160, 50, 0, getZoomingFont(title_font, seqtimer, 0.2, 1 / 120.0), title_color);
    }

    public void paintFrameStartGame() {
//        drawString("Get Ready!", 160, 50, 0,
//                getZoomingFont(title_font, seqtimer, 0.2, 1 / 80.0),
//                title_color);
    }

    public void paintFrameStartLevel() {
//        drawString("Stage " + (stage + 1), 160, 50 + seqtimer, 0,
//                getZoomingFont(title_font, seqtimer, 0.2, 1 / 80.0),
//                title_color);
    }

    public void paintFrameLevelDone() {
//        drawString("Start over!", 160, 50, 0,
//                getZoomingFont(title_font, seqtimer + 80, 0.2, 1 / 80.0),
//                title_color);
    }

    public void paintFrameTitle() {
        startGame();
    }

    public class Player extends JGObject {

        double speed;
        double jumpHeight = 12;
        int jumptime = 0;
        int falltime = 0;
        int bullettime = 0;
        int dir = 1;
        boolean jumping_up = false, jumping_down = false, swimming = false;

        public Player(double x, double y, double speed, JGEngineInterface engine) {
            super("player", false, x, y, 1, "player_l1", 0, 0, 32, 32, engine);
            this.speed = speed;

//            if (this.speed == 0) {
            this.speed = 6;
//            }
        }

        public void move() {
            moveNorm();
//            System.out.println("("+getOffscreenMarginX()+","+getOffscreenMarginY()+")");
//            if (!isOnPF(32, 32)) {
//                levelDone();
//            }
        }

        public void moveNorm() {
//            setAnim(getClientInfo().getPlayerState());
            snapToGrid(speed / 2, 0); // ensure we can fall through small holes
        }

        public void hit(JGObject obj) {
//            if (and(obj.colid, 2)) {
//                lifeLost();
//            } else {
//                score += 5;
//                obj.remove();
//
//                UIElements.getInstance().setP2Score(score);
//                new StdScoring("pts", obj.x, obj.y, 0, -1.0, 40, "5 pts", scoring_font,
//                        new JGColor[]{JGColor.red, JGColor.yellow}, 2, getEngine());
//            }
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
                    if (isYAligned(jumpHeight * 2)) {
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
                            snapToGrid(jumpHeight, jumpHeight);
                        } else {
                            snapToGrid(jumpHeight, 0);
                        }
                    } else {
                        snapToGrid(jumpHeight, 0);
                    }
                } else {
                    snapToGrid(jumpHeight, 0);
                }
                /*for (int tdx=0; tdx<txsize; tdx++) {
                cid |= canvas.getTileCid(tx+tdx, ty);
                }*/
            }
            if ((tilecid & 4) != 0) {
//                playAudio("pickup");
                for (int x = 0; x < txsize; x++) {
                    for (int y = 0; y < tysize; y++) {
                        if ((getTileCid(tx + x, ty + y) & 4) != 0) {
                            score += 25;
                            UIElements.getInstance().setP2Score(getClientInfo().getScore());

                            new StdScoring("pts", this.x, this.y, 0, -1.0, 40, "25 pts", scoring_font, new JGColor[]{JGColor.red, JGColor.yellow}, 2, getEngine());
                            if ((getTileCid(tx + x, ty + y) & 8) != 0) {
                                setTile(tx + x, ty + y, "w");
                            } else {
                                setTile(tx + x, ty + y, ".");
                            }
                        }
                    }
                }
            }
            // lifeLost();
        }
    }
}
