/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import jb2dtest.ClientInfo;
import jb2dtest.MultiplayerConnect;
import jgame.*;
import jgame.impl.JGEngineInterface;
import jgame.platform.*;
import jgtest.sound.SoundEffects;
import jgtest.ui.UIElements;
import rmi.stubbs.Gamer;

/** Space Run III, a variant on Space Run, illustrating scrolling and wrapping
 * playfield. */
public class SpaceRunIII extends StdGame {

    private Player player;
    private int viewGameSpeed = 3;
    private int xOffsetDefault = 500;
    private int xView = xOffsetDefault;
    private ClientInfo cInfo = new ClientInfo();
    private boolean pushed = false;
    private int frameCount = 0;
    private Gamer myself;
    private SpaceRunIIIOpponent opponentEngine;
    private boolean continueGame = false;
    private GamerScore totScore;

    private JFrame parent;

    public SpaceRunIII() {
        initEngineApplet();
    }

    public SpaceRunIII(JGPoint size, SpaceRunIIIOpponent opponentEngine, JFrame parent) {
        this(size, opponentEngine, new GamerScore(), parent);
    }

    public SpaceRunIII(JGPoint size, SpaceRunIIIOpponent opponentEngine, GamerScore totScore, JFrame parent) {
        initEngineComponent(size.x, size.y);
        this.opponentEngine = opponentEngine;
        this.totScore = totScore;
        myself = MultiplayerConnect.getMySelf();
        this.parent = parent;
    }

    public void initCanvas() { // 20,15,32,32
        setCanvasSettings(30, 15, 32, 32, null, null, null);
//        if (isMidlet()) {
//            setScalingPreferences(3.0 / 4.0, 4.0 / 3.0, 0, 7, 0, 7);
//        }
    }

    public void initGame() {
        defineMedia("gr.tbl");
        setBGImage(0, "citynight_bg", false, false);
        setFrameRate(35, 1);

        int totalTime = (int) (LevelDesign.LEVEL_LENGTH_TIME * getFrameRate());
        new JGTimer(totalTime, true, this) {

            @Override
            public void alarm() {
                System.out.println("Her inne");
                setGameState("GameOver");
                opponentEngine.setGameState("GameOver");
//                startGameOver();
            }
        };

        SoundEffects.playRandomMusic();

        startgame_ingame = true;
        leveldone_ingame = true;
        title_color = new JGColor(0, 168, 255);
        title_bg_color = new JGColor(116, 116, 116);
        title_font = new JGFont("Arial", 0, 50);
    }

    public GamerScore getTotalScore() {
        return totScore;
    }

    public void defineLevel() {
//        timer = 0;
//        removeAllTimers();

        UIElements.getInstance().setTime(LevelDesign.LEVEL_LENGTH_TIME - ((int) (timer / getFrameRate())));


        lives = 9999;
        initial_lives = 9999;

        removeObjects(null, 0);
        xView = xOffsetDefault;

        leveldone_ingame = true;
        setPFSize(400, 30);

        setOffscreenMargin(50, 50);

        setPFWrap(false, false, 32, 32);

        fillBG(".");
        String[] map = LevelDesign.LEVEL_1;
        setTilesMulti(0, 0, map);
        player = new Player(32, 50, 3, this);

    }

    public Player getPlayer() {
        return player;
    }

    public ClientInfo getClientInfo() {
        return cInfo;
    }

    public void setClientInfo(ClientInfo cInfo) {
        this.cInfo = cInfo;
    }

    public void initNewLife() {
        defineLevel();
    }

    public void startGameOver() {
//        removeObjects(null, 0);
        SoundEffects.stopAllMusic();

//        if (score >= opponentEngine.score) {
//            totScore.incrementTotalScore(score);
//        }

//        continueGame = true;
//        destroyApp(true);
//        opponentEngine.destroyApp(true);
    }

    public boolean continueGame() {
        return continueGame;
    }

    public void doFrameInGame() {
        moveObjects();

        UIElements.getInstance().setTime(LevelDesign.LEVEL_LENGTH_TIME - ((int) (timer / getFrameRate())));

        checkCollision(4, 1); // coin hit player
        checkBGCollision(2, 1); // bg hits player
        checkBGCollision(4, 1);

        xView += viewGameSpeed;
        frameCount++;

        setViewOffset((int) xView, (int) getObject("player").y, true);

        // Player off screen. Push player.
        if (xView > (getPlayer().x + 500 - 32)) {

            if (!getKey(key_right)) {
                getPlayer().x = getPlayer().x + 5;
                pushed = true;
            }


            if (xView > (getPlayer().x + 480)) {
                lifeLost();
            }
        } else {
            pushed = false;
        }

        cInfo.setPfx((int) xView);
        cInfo.setPfy((int) getObject("player").y);
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

        drawString("Start over!", 450, 40, 0, getZoomingFont(title_font, seqtimer, 0.9, 1 / 40.0), title_color);
        score = 0;
        UIElements.getInstance().setP1Score(myself, score, totScore.getTotalScore());
    }

    public void doFrameGameOver () {
        if (seqtimer > 300) {
            if (score >= opponentEngine.score) {
                totScore.incrementTotalScore(score);
                try {
                    myself.setScore(totScore.getTotalScore());
                } catch (RemoteException ex) {
                    Logger.getLogger(SpaceRunIII.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            parent.setVisible(false);
            parent.dispose();
        }
    }

    @Override
    public void paintFrameGameOver() {
        setColor(title_bg_color);
        setStroke(1);

        continueGame = true;
        System.out.println("paintFrameGameOver: " + seqtimer + " and : " + (seqtimer * 6));



        drawRect(450, 0, seqtimer * 8, seqtimer * 6, true, true, false);
        drawString("Match done. You " + ((cInfo.getScore() >= opponentEngine.getClientInfo().getScore()) ? "WON" : "LOST"), 450, 40, 0, title_font, title_color);

        JGFont infoText = new JGFont(title_font.name, title_font.getStyle(), title_font.getSize() / 1.5);
        drawString("Press space to go to next match or ESC to save score", 450, 40 + title_font.getSize(), 0, infoText, title_color);
        drawString("Your score: " + cInfo.getScore(), 450, 40 + title_font.getSize() + infoText.getSize(), 0, infoText, title_color);
        drawString("Opponent score: " + opponentEngine.getClientInfo().getScore(), 450, 40 + title_font.getSize() + infoText.getSize() * 2, 0, infoText, title_color);
    }

    public void paintFrameStartGame() {
        drawString("Run!", 450, 40, 0, getZoomingFont(title_font, seqtimer, 0.9, 1 / 40.0), title_color);
    }

    public void paintFrameStartLevel() {
        // drawString("Run!", 450, 40, 0, getZoomingFont(title_font, seqtimer, 0.9, 1 / 40.0), title_color);
    }

    public void paintFrameLevelDone() {
        drawString("Run!", 450, 40, 0, getZoomingFont(title_font, seqtimer, 0.9, 1 / 40.0), title_color);
    }

    public void paintFrameTitle() {
        drawString("Play SOME_NAME_HERE!", 450, 40, 0, getZoomingFont(title_font, seqtimer, 0.3, 1 / 40.0), title_color);

        drawString("Press " + getKeyDesc(key_startgame) + " to start", 450, 200, 0, getZoomingFont(title_font, seqtimer, 0.9, 1 / 40.0), title_color);

//        if (!isMidlet()) {
//            drawString("Press " + getKeyDesc(key_gamesettings) + " for settings",
//                    160, 160, 0, getZoomingFont(title_font, seqtimer, 0.3, .03),
//                    title_color);
//        }
    }

    public class Player extends JGObject {

        double speed;
        double jumpHeight = 10;
        int jumptime = 0;
        int falltime = 0;
        int bullettime = 0;
        int dir = 1;
        int startHeight = -1;
        boolean maxHeight = false;
        boolean jumping_up = false, jumping_down = false;

        public Player(double x, double y, double speed, JGEngineInterface engine) {
            super("player", false, x, y, 1, "player_r1", 0, 0, 32, 32, engine);
            this.speed = speed;

//            if (this.speed == 0) {
            this.speed = 6;
//            }
        }

        public void move() {
            moveNorm();
            cInfo.setX(x);
            cInfo.setY(y);

            if (getKey(KeyEvent.VK_SPACE)) {
                lifeLost();
            }

//            System.out.println("("+getOffscreenMarginX()+","+getOffscreenMarginY()+")");
            if (!isOnPF(32, 32)) {
                lifeLost();
            }
        }

        public void moveNorm() {

            snapToGrid(speed / 2, 3); // ensure we can fall through small holes
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

//                if (!isYAligned(jumpHeight)) {
//                    System.out.println("her");
//                    /* fall until aligned */
//                    y += jumpHeight;
//                } else {
//                System.out.println("her2");
                if (cid <= 0 || cid == 4 || !isYAligned(speed)) {
//                    System.out.println("her 3");
                    /* no support -> fall */
                    y += jumpHeight;
                    // make sure the player is tile aligned when it falls
                    // off a tile, or it might find support when it should
                    // fall through a hole.
                    snapToGrid(speed / 2.0, 3);
                }

                /* stand on ground */
                snapToGrid(3, speed);
                if (getKey(key_left) && (xView < x + 500) && !pushed) {
                    setAnim("player_l");
                    cInfo.setPlayerState(1);
                    startAnim();
                    dir = -1;
                    x -= speed;
                }
                if (getKey(key_right) && (xView + 900 > x + 500)) {
                    setAnim("player_r");
                    cInfo.setPlayerState(2);
                    startAnim();
                    dir = 1;
                    x += speed;
                }
                if ((getKey(key_up)) && cid > 0 && cid != 4) {
                    SoundEffects.jump();
                    jumptime = 30;
                    startHeight = cts.y;
                }

//                }

            } else { /* jumping */
                if (jumptime > 15 && (getKey(key_up)) && startHeight - cts.y < 6 && startHeight != -1) { /* up */
                    y -= jumpHeight;
                    jumping_up = true;
                    jumping_down = false;
                } else { /* down */
                    cid = 0;
                    for (int tx = 0; tx < ts.width; tx++) {
                        cid |= getTileCid(ts.x + tx, cts.y + 1);
                    }

                    if (cid == 0 || cid == 4) {
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

                    if (cid <= 0 || cid == 4 || !isYAligned(speed)) {
//                        System.out.println("her 4");
                        /* no support -> fall */
                        y += jumpHeight;
                        // make sure the player is tile aligned when it falls
                        // off a tile, or it might find support when it should
                        // fall through a hole.
                        snapToGrid(speed / 2.0, 3);
                    }
                }
                if (startHeight - cts.y >= 6) {
                    startHeight = -1;
                }
                if (getKey(key_left) && (xView < x + 500) && !pushed) {
                    setAnim("player_l");
                    cInfo.setPlayerState(1);
                    startAnim();
                    x -= jumpHeight;
                    dir = -1;
                }
                if (getKey(key_right) && (xView + 900 > x + 500)) {
                    setAnim("player_r");
                    cInfo.setPlayerState(2);
                    startAnim();
                    x += jumpHeight;
                    dir = 1;
                }
                if (!getKey(key_up)) {
                    startHeight = -1;
                    jumptime--;
                }
            }
        }

        public void hit(JGObject obj) {
            if (and(obj.colid, 4)) {
                score += 20;
                obj.remove();

                UIElements.getInstance().setP1Score(myself, score, totScore.getTotalScore());
                new StdScoring("pts", obj.x, obj.y, 0, -1.0, 40, "20 pts", scoring_font, new JGColor[]{JGColor.red, JGColor.yellow}, 2, getEngine());
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
                            UIElements.getInstance().setP1Score(myself, score, totScore.getTotalScore());
                            cInfo.setScore(score);

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
