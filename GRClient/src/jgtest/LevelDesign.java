/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest;

/**
 *
 * @author mikaelbrevik
 */
public class LevelDesign {

    public static final int LEVEL_LENGTH_TIME = 30;

    public static final String[] TEST_LEVEL = {
        "r",
        "r",
        "r",
        "r",
        "r",
        "r",
        "r",
        "r",
        "r t t t t t t r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l t t t t t t t r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . #",
        "r # # # # # # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l # # # # # # # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . #",
        "r # # # # # # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l t t t t l # # # # # # # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l t t r . . . . . . . . #",
        "r # # # # # # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l # # # # l # # # # # # # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l # # r . . . . . . . . #",
        "r # # # # # # r . . . . . . . . . . . . . . . . . * * . . . . . . . . . * . l # # # # l # # # # # # # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l # # r . . . . . . . . # ",
        "r # # # # # # r . . . . . . . * . * . . . . . . * . . * . . . . . * . l t t l # # # # l # # # # # # # r . . . . . l t t t t t t t t t t t t t t t t t t r . . . . . l t t t t t t t t t t t t t t t t t t r . . . . . l t t t t t t t t t t t t t t t t t t r . . . . . l t t t t t t t t t t t t t t t t t t r . . . . l t t t l # # r . . . . . . . . #",
        "r # # # # # # r . . . . . l t t t t t r . . . . . . . . . . . . l t t l # # l # # # # l # # # # # # # r . . . . . l # # # # # # # # # # # # # # # # # # r . . . . . l # # # # # # # # # # # # # # # # # # r . . . . . l # # # # # # # # # # # # # # # # # # r . . . . . l # # # # # # # # # # # # # # # # # # r . . . . l # # # l # # r . . . . . . . . #",
        "r # # # # # # r . . . . . l # # # # # r . . . l t t t t r . . . l # # l # # l # # # # l # # # # # # # r . . . . . l # # # # # # # # # # # # # # # # # # r . . . . . l # # # # # # # # # # # # # # # # # # r . . . . . l # # # # # # # # # # # # # # # # # # r . . . . . l # # # # # # # # # # # # # # # # # # r . . . . l # # # l # # r . . . . . . . . #",};


    public static final String[] LEVEL_1 = {
        "r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . . . . . . * * . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . . . . . . * * . . . . . . . . . . . . . . . . . . . . . . . . . * . . . . . . . * . . . . . . . . . l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . . . . . . * * . . . . . . . . . . . . . . . . . . . . . . . . . * . . . . . . . * . . . . . . . . . l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . . . . . . . . . . . . * . . . . . . . . . . . . . . . . . . . . * . . . . . . . * . . . . . . . . . l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . . . . l t t t t r . . . * . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . . . * l # # # # r . . . . * . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . . . * l # # # # r . . . * . . . . . . . . . . . . . . . . . . l t r . . . . . l t r . . . . . . . . l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . . . * l # # # # r . . . . * . . . . . . . . . . . . . . . . . l # r . . . . . l # r . . . . . . . . l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . . . * l # # # # r . . . * . . . . . . . . . . . . . . . . . . l # r . . * . . l # r . . . . . . . . l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . . l t l # # # # r . . . . * . . . . . . . . . . . . . . . . . l # r . . * . . l # r . . . . . . . . l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . . l # l # # # # r . . . . * . . . . . . . . . . . . l t r . . l # r . . * . . l # r t t t t t t t t l . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . * * . . l # l # # # # r . . . . * . . . . . . . . . . . . l # r . . l # r . . * . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . * * . l l # l # # # # r . . . . * . . . . . . . . . l t t l # r . . l # r . . * . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . l l # l # # # # r . . . . * . . . . . . . . . l # # l # r . . l # r . . * . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r . . . . . l l # l # # # # r . . . . * . . . . . . . . . l # # l # r . . l # r . . * . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r t t l t t l l # l # # # # r . . . . * . . . . . . . . . l # # l # r . . l # r . . * . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r # # l # # l l # l # # # # r . . . . . . . . . . . . . l l # # l # r . . l # r . . . . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r # # l # # l l # l # # # # r t t t t t t t t r . . . . l l # # l # r . . l # r . . . . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r # # l # # l l # l # # # # r # # # # # # # # r . . . . l l # # l # r . . l # r . . . . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r # # l # # l l # l # # # # r # # # # # # # # r . . . l l l # # l # r . . l # r . . . . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r t t l # # l l # l # # # # r # # # # # # # # r r . . l l l # # l # r . . l # r . . . . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r # # l # # l l # l # # # # r # # r * * * * * * * * * l l l # # l # r . . l # r . . . . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r # # l # # l l # l # # # # r # # r * * * * * * * * * l l l # # l # r . . l # r . . . . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r # # l # # l l # l # # # # r # # r * * * * * * * * * l l l # # l # r . . l # r . . . . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r # # l # # l l # l # # # # r # # r t t t t . t t t t l l l # # l # r . . l # r . . . . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
        "r # # l # # l l # l # # # # r # # r # # # # . # # # # l l l # # l # r . . l # r . . . . . l # r . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . l",
    };
    private static String[] createBuilding(int nrTilesX, int nrTilesY, int marginLeftX, int pfNrTilesY) {

        String[] base = new String[pfNrTilesY];

        // Fill building template with empty tiles to get proper size.
        for (int i = 0; i < pfNrTilesY - nrTilesY; i++) {
            for (int j = 0; j < nrTilesX + marginLeftX; j++) {
                base[i] = (base[i] == null) ? " ." : base[i] + " .";
            }
        }

        for (int i = pfNrTilesY - nrTilesY; i < pfNrTilesY; i++) {
            for (int j = 0; j < nrTilesX + marginLeftX; j++) {
                // Check for margin
                if (base[i] == null || base[i].length() < marginLeftX * 2) {
                    // No building here, just some margin.
                    base[i] = (base[i] == null) ? " ." : base[i] + " .";
                } else {
                    // No more margin. Start building a sky scraper.
                    base[i] = (base[i] == null) ? " #" : base[i] + " #";
                }

            }
        }

        return base;
    }

    public static String[] createRandomMap (int pfNrTilesX, int pfNrTilesY) {
        // build enough buildings to fill the pfNrTilesX and pfNrTilesY and merge them.
        return null;
    }

    private static String[] createBuilding(int[] size) {
        if (size.length != 4) {
            throw new IllegalArgumentException("Param size must have 4 indexes.");
        }
        return createBuilding(size[0], size[1], size[2], size[3]);
    }

    public static int[] getRandomSize() {
        return new int[]{4, 5, 4, 20};
    }

    public static void main(String[] args) {
        String[] map = createBuilding(getRandomSize());
        for (String l : map) {
            System.out.println(l);
        }
    }
}
