
package com.gameroulette.client;

/**
 * Holds information about total score.
 * For transfering between matches/games.
 *
 * @author mikaelbrevik
 */
public class GamerScore {

    int totScore = 0;

    public GamerScore () {}
    public GamerScore (int totScore) {
        this.totScore = totScore;
    }

    public int getTotalScore () {
        return totScore;
    }

    public void incrementTotalScore (int increment) {
        totScore += increment;
    }

    public void setTotalScore (int totScore) {
        this.totScore = totScore;
    }
}