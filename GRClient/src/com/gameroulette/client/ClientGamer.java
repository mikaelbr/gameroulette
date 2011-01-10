/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameroulette.client;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import com.gameroulette.network.ClientInfo;
import com.gameroulette.network.MultiplayerConnect;
import jgame.*;
import jgame.impl.JGEngineInterface;
import jgame.platform.*;
import com.gameroulette.sound.SoundEffects;
import com.gameroulette.ui.UIElements;
import rmi.stubbs.Gamer;

/** 
 * Main game function. Extends a standard version of the JGEngine.
 * Is a wrapper of the game loop. 
 *
 */
public class ClientGamer extends StdGame {

    private Player player;
    private int viewGameSpeed = 3;
    private int xOffsetDefault = 300;
    private int xView = xOffsetDefault;
    private ClientInfo cInfo = new ClientInfo();
    private boolean pushed = false;
    private Gamer myself;
    private OpponentGamer opponentEngine;
    private boolean continueGame = false;
    private GamerScore totScore;
    private JFrame parent;
    private boolean isSaved = false;

    public ClientGamer() {
        initEngineApplet();
    }

    public ClientGamer(JGPoint size, OpponentGamer opponentEngine, JFrame parent) {
        this(size, opponentEngine, new GamerScore(), parent);
    }

    public ClientGamer(JGPoint size, OpponentGamer opponentEngine, GamerScore totScore, JFrame parent) {
        initEngineComponent(size.x, size.y);
        this.opponentEngine = opponentEngine;
        this.totScore = totScore;
        myself = MultiplayerConnect.getMySelf();
        this.parent = parent;
    }

    public void initCanvas() { // 20,15,32,32
        setCanvasSettings(30, 15, 32, 32, null, null, null);
    }
    private String[] map;

    public void initGame() {
        defineMedia("gr.tbl");
        setBGImage(0, "citynight_bg", false, false);
        setFrameRate(35, 1);

        int totalTime = (int) (LevelDesign.LEVEL_LENGTH_TIME * getFrameRate());
        new JGTimer(totalTime, true, this) {

            @Override
            public void alarm() {
                setGameState("GameOver");
                opponentEngine.setGameState("GameOver");
            }
        };

        map = MultiplayerConnect.getLevelDesign();

        SoundEffects.playRandomMusic(); // Play random music.

        // JGEngine-relevant settings (global for engine instance)
        startgame_ingame = true;
        leveldone_ingame = true;
        title_color = new JGColor(0, 168, 255);
        title_bg_color = new JGColor(116, 116, 116);
        title_font = new JGFont("Arial", 0, 50);
    }

    /**
     * Get total score for user.
     */
    public GamerScore getTotalScore() {
        return totScore;
    }

    /**
     * Load new level and objects (player and coins)
     */
    @Override
    public void defineLevel() {
        // Indicates if the opponent viewer with the opponent should restart or not.
        cInfo.setResetGame(0);

        UIElements.getInstance().setTime(LevelDesign.LEVEL_LENGTH_TIME - ((int) (timer / getFrameRate())));

        // "infinete" lives..
        lives = 9999;
        initial_lives = 9999;

        removeObjects(null, 0);
        xView = xOffsetDefault;

        startgame_ingame = true;
        leveldone_ingame = true;
        setPFSize(400, 40);

        setOffscreenMargin(50, 50);
        setPFWrap(false, false, 32, 32);

        // Set std filling of map (empty tiles)
        fillBG(".");

        // Draw map
        setTilesMulti(0, 0, map);
        player = new Player(32, 50, 3, this);

    }

    /**
     * Get player object (for collision detection etc)
     *
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get client info. Holds information of player/view movement.
     *
     * @return ClientInfo
     */
    public ClientInfo getClientInfo() {
        return cInfo;
    }

    /**
     * Set new client info. Determens movement of player, view.
     */
    public void setClientInfo(ClientInfo cInfo) {
        this.cInfo = cInfo;
    }

    /**
     * Life lost init.
     */
    public void initNewLife() {
        defineLevel();
    }

    /**
     * Game done init.
     */
    public void startGameOver() {
        SoundEffects.stopAllMusic(); // Deactivate sound.
    }


    public boolean continueGame() {
        return continueGame;
    }

    /**
     * Game loop.
     */
    public void doFrameInGame() {
        cInfo.setResetGame(0);
        moveObjects();

        UIElements.getInstance().setP1Score(myself, score, totScore.getTotalScore());
        cInfo.setScore(score);

        UIElements.getInstance().setTime(LevelDesign.LEVEL_LENGTH_TIME - ((int) (timer / getFrameRate())));

        checkCollision(4, 1); // coin hit player
        checkBGCollision(2, 1); // bg hits player
        checkBGCollision(4, 1);

        xView += viewGameSpeed;

        setViewOffset((int) xView, (int) getObject("player").y, true);

        // Player off screen. Push player.
        if (xView > (getPlayer().x + 500 - 32)) {

            if (!getKey(key_right)) {
                getPlayer().x = getPlayer().x + 5;
                pushed = true;
            }


            // Player still off screen. indicates squees.
            if (xView > (getPlayer().x + 480)) {
                lifeLost();
            }
        } else {
            pushed = false;
        }

        // Update ClientInfo with screen coordinates.
        cInfo.setPfx((int) xView);
        cInfo.setPfy((int) getObject("player").y);
    }

    public void incrementLevel() {
        
    }

    JGFont scoring_font = new JGFont("Arial", 0, 8);

    /**
     * Life lost game loop.
     */
    public void doFrameLifeLost() {
        // Set that opponent should restart opponent-view
        cInfo.setResetGame(1);
    }

    public void paintFrameLifeLost() {
        setColor(title_bg_color);
        UIElements.getInstance().setTime(LevelDesign.LEVEL_LENGTH_TIME - ((int) (timer / getFrameRate())));

        drawString("Start over!", 450, 40, 0, getZoomingFont(title_font, seqtimer, 0.9, 1 / 40.0), title_color);
        score = 0;
        UIElements.getInstance().setP1Score(myself, score, totScore.getTotalScore());
    }

    /**
     * Game Loop when no lives left or game over gamestate triggered.
     * Will add total score if win then close game window.
     */
    public void doFrameGameOver() {
        if (!isSaved) {
            isSaved = true;
            if (cInfo.getScore() >= opponentEngine.getClientInfo().getScore()) {
                totScore.incrementTotalScore(cInfo.getScore());
                try {
                    myself.setScore(totScore.getTotalScore());
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientGamer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            new Thread(new Runnable() {

                public void run() {
                    parent.setVisible(false);
                    parent.removeAll();
                    parent.dispose();
                }
            }).start();

        }
    }

    @Override
    public void paintFrameGameOver() {
        setColor(title_bg_color);
        setStroke(1);

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
    }

    public void paintFrameLevelDone() {
        drawString("Run!", 450, 40, 0, getZoomingFont(title_font, seqtimer, 0.9, 1 / 40.0), title_color);
    }

    public void doFrameTitle() {
    }

    public void paintFrameTitle() {
        drawString("Start by pressing space!", 450, 40, 0, getZoomingFont(title_font, seqtimer, 0.9, 1 / 40.0), title_color);
    }

    /**
     * Player object with I/O controllers.
     */
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

                if (cid <= 0 || cid == 4 || !isYAligned(speed)) {
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
            // Check if collision is between player and coin.
            if ((tilecid & 4) != 0) {
                for (int x = 0; x < txsize; x++) {
                    for (int y = 0; y < tysize; y++) {
                        if ((getTileCid(tx + x, ty + y) & 4) != 0) {
                            score += 25; // update score.
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
        }
    }
}
