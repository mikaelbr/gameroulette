/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest;

import jgame.*;
import jgame.impl.JGEngineInterface;
import jgame.platform.*;
import jgtest.ui.UIElements;

/** Space Run III, a variant on Space Run, illustrating scrolling and wrapping
 * playfield. */
public class SpaceRunIIIOpponent extends StdGame {

    private Player player;

    public SpaceRunIIIOpponent(JGPoint size) {
        initEngineComponent(size.x, size.y);
        setFocusable(false);
    }

    public void initCanvas() {
        setCanvasSettings(20, 15, 16, 16, null, null, null);
        if (isMidlet()) {
            setScalingPreferences(3.0 / 4.0, 4.0 / 3.0, 0, 7, 0, 7);
        }
    }

    public void initGame() {
        defineMedia("space_run.tbl");
        if (isMidlet()) {
            setFrameRate(18, 1);
            setGameSpeed(2.0);
        } else {
            setFrameRate(35, 1);
        }
        lives_img = "ship";
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
//        removeObjects(null, 0);

        leveldone_ingame = true;
        setPFSize(150, 40);
        setPFWrap(false, false, 0, 0);
        int tunnelheight = 11 - level / 2;
        int tunnelpos = pfTilesY() / 2 - tunnelheight / 2;
        fillBG("#");
        int firstpart = 15;
        int oldpos = 0;
        for (int x = 0; x < pfTilesX(); x++) {
            for (int y = tunnelpos; y < tunnelpos + tunnelheight; y++) {
                setTile(x, y, "");
            }
            if (firstpart > 0) {
                firstpart--;
            } else {
                oldpos = tunnelpos;
                tunnelpos += random(-1, 1, 2);
                if (tunnelpos < 1) {
                    tunnelpos = 1;
                }
                if (tunnelpos + tunnelheight >= pfTilesY() - 1) {
                    tunnelpos = pfTilesY() - tunnelheight - 1;
                }
            }
        }
        player = new Player(0, pfHeight() / 2, 3, this);
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
        checkBGCollision(1, 1); // bg hits player
        setViewOffset((int) getObject("player").x + 100, (int) getObject("player").y, true);
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
        drawString("Stage " + (stage + 1) + " Clear !", 160, 50, 0,
                getZoomingFont(title_font, seqtimer + 80, 0.2, 1 / 80.0),
                title_color);
    }

    public void paintFrameTitle() {
        startGame();
    }

    public class Player extends JGObject {

        public Player(double x, double y, double speed, JGEngineInterface engine) {
            super("player", false, x, y, 1, "ship", 0, 0, speed, speed, -1, engine);
        }

        public void move() {
            setDir(0, 0);

            if (!isOnPF(0, 0)) {
                levelDone();
            }
        }

        public void hit(JGObject obj) {
            
        }

        public void hit_bg(int tilecid) {
            
        }
    }
}
