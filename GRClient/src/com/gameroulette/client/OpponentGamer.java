/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameroulette.client;

import com.gameroulette.network.ClientInfo;
import com.gameroulette.network.MultiplayerConnect;
import jgame.*;
import jgame.impl.JGEngineInterface;
import jgame.platform.*;
import com.gameroulette.ui.UIElements;
import rmi.stubbs.Gamer;

/**
 * Game engine for opponent (read only/view)
 *
 * Reads coordinates from ClientInfo.
 *
 * @author mikaelbrevik
 */
public class OpponentGamer extends StdGame {

    private Player player;
    private ClientInfo cInfo = new ClientInfo();
    private String[] translatePlayerState = {"player_l", "player_l", "player_r"};
    private Gamer opponent;
    private GamerScore totScore;

    public boolean continueGame() {
        return false;
    }

    public OpponentGamer(JGPoint size) {
        this(size, new GamerScore());
    }

    public OpponentGamer(JGPoint size, GamerScore totScore) {
        initEngineComponent(size.x, size.y);
        setFocusable(false);
        this.totScore = totScore;
        opponent = MultiplayerConnect.getOpponent();
//        opponent = 
    }

    public void initCanvas() {
        setCanvasSettings(30, 15, 32, 32, null, null, null);
//        if (isMidlet()) {
//            setScalingPreferences(3.0 / 4.0, 4.0 / 3.0, 0, 7, 0, 7);
//        }
    }

    public GamerScore getTotalScore() {
        return totScore;
    }

    public void setClientInfo(ClientInfo cInfo) {
        this.cInfo = cInfo;
    }

    public ClientInfo getClientInfo() {
        return cInfo;
    }
    private String[] map;

    public void initGame() {
        defineMedia("gr.tbl");
        setBGImage(0, "citynight_bg", false, false);
        setFrameRate(35, 1);

        map = MultiplayerConnect.getLevelDesign();

        startgame_ingame = true;
        leveldone_ingame = true;
        title_color = new JGColor(0, 168, 255);
        title_bg_color = new JGColor(116, 116, 116);
        title_font = new JGFont("Arial", 0, 50);
        setHighscores(10, new Highscore(0, "nobody"), 15);
        highscore_title_color = JGColor.red;
        highscore_title_font = new JGFont("Arial", 0, 20);
        highscore_color = JGColor.yellow;
        highscore_font = new JGFont("Arial", 0, 16);
    }

    public void defineLevel() {
        lives = 9999;
        initial_lives = 9999;

        removeObjects(null, 0);

        leveldone_ingame = true;
        setPFSize(400, 40);
        setPFWrap(false, false, 0, 0);

        fillBG(".");
        setTilesMulti(0, 0, map);
        player = new Player(32, pfHeight() / 2 - 100, 3, this);
        setViewOffset(cInfo.getPfx(), cInfo.getPfy(), true);
    }

    public Player getPlayer() {
        return player;
    }

    public void initNewLife() {
        defineLevel();
    }

    public void startGameOver() {
    }

    public void doFrameInGame() {
        // Check if we should reset game. This way we reload all coins and objects.
        if (cInfo.isResetGame() == 1) {
            defineLevel();
        }

        moveObjects();
        checkCollision(4, 1); // coin hit player
        checkBGCollision(2, 1); // bg hits player
        checkBGCollision(4, 1); // coin hit player

        UIElements.getInstance().setP2Score(opponent, cInfo.getScore(), totScore.getTotalScore());

        // Move screen in the same speed as opponent.
        if (cInfo != null) {
            setViewOffset(cInfo.getPfx(), cInfo.getPfy(), true);
        } else {
            setViewOffset((int) getObject("player").x + 100, (int) getObject("player").y, true);
        }

    }

    public void incrementLevel() {
    }
    JGFont scoring_font = new JGFont("Arial", 0, 8);

    public void paintFrameLifeLost() {
    }

    /**
     * Shows a text in view with opponent name. To seperate windows.
     */
    public void paintFrameInGame() {
        try {
            drawString("Opponent: " + opponent.getUsername(), 450, 40, 0, title_font, title_color);
        } catch (Exception ex) {
            drawString("Opponent", 450, 40, 0, title_font, title_color);
        }
    }

    public void paintFrameGameOver() {
    }

    public void paintFrameStartGame() {
    }

    public void paintFrameStartLevel() {
    }

    public void paintFrameLevelDone() {
    }

    public void paintFrameTitle() {
        startGame(); // auto start.
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

            this.speed = 6;
        }

        public void move() {
            moveNorm();
            if (!isOnPF(32, 32)) {
                lifeLost();
            }
        }

        public void moveNorm() {
            setAnim(translatePlayerState[getClientInfo().getPlayerState()]);
            x = cInfo.getX();
            y = cInfo.getY();
            snapToGrid(speed / 2, 0); // ensure we can fall through small holes
        }

        public void hit(JGObject obj) {
        }

        /**
         * Have our own collision detection, even though it's just a view.
         * This way we can interact better.
         * 
         * @param tilecid
         * @param tx
         * @param ty
         * @param txsize
         * @param tysize
         */
        public void hit_bg(int tilecid, int tx, int ty, int txsize, int tysize) {
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
                for (int x = 0; x < txsize; x++) {
                    for (int y = 0; y < tysize; y++) {
                        if ((getTileCid(tx + x, ty + y) & 4) != 0) {
                            score += 25;
                            UIElements.getInstance().setP2Score(opponent, cInfo.getScore(), totScore.getTotalScore());

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
