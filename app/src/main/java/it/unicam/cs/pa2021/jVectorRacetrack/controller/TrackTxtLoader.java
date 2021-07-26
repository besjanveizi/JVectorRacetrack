package it.unicam.cs.pa2021.jVectorRacetrack.controller;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Zone;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the loader of the zones for a <code>GridRacetrack</code> using a textual file.
 */
public class TrackTxtLoader implements Track2DLoader{
    private final static Logger logger = Logger.getLogger(GameController.class.getName());
    private final int width;
    private final int height;
    private final char[][] trackMatrix;

    /**
     * Creates the getter for the zones that have to be loaded from the given file path.
     * @param filePath given file path from where the zone will be loaded.
     */
    public TrackTxtLoader(String filePath){
        trackMatrix = get2DCharArray(filePath);
        width = getWidth();
        height = getHeight();
    }

    @Override
    public boolean isValid() {
        return !isEmpty() && !hasDifferentLineLengths();
    }

    private boolean hasDifferentLineLengths() {
        for (int i = 1; i < trackMatrix.length; i++) {
            if (trackMatrix[i].length != width) return true;
        }
        return false;
    }

    private boolean isEmpty() {
        return width == 0;
    }

    public int getHeight() {
        return trackMatrix.length;
    }
    public int getWidth() {
        return trackMatrix[0].length;
    }

    @Override
    public Zone[][] getZones() throws IOException {
        Zone[][] zones = new Zone[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (trackMatrix[i][j]) {
                    case '#' :
                        zones[i][j] = Zone.OUTSIDE;
                        break;
                    case ' ' :
                        zones[i][j] = Zone.INSIDE;
                        break;
                    case '0' :
                        zones[i][j] = Zone.START;
                        break;
                    case '9' :
                        zones[i][j] = Zone.FINISH;
                        break;
                    case '1' :
                        zones[i][j] = Zone.CHECKPOINT1;
                        break;
                    case '2' :
                        zones[i][j] = Zone.CHECKPOINT2;
                        break;
                    case '3' :
                        zones[i][j] = Zone.CHECKPOINT3;
                        break;
                    case '4' :
                        zones[i][j] = Zone.CHECKPOINT4;
                        break;
                    default : try {
                        throw new IOException("The file does not follow the correct zone parsing");
                    } catch (IOException e) {
                        logger.log(Level.WARNING, "Loaded track is not valid\n", e);
                        throw e;
                    }
                }
            }
        }
        return zones;
    }

    private char[][] get2DCharArray(String filePath) {
        try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error: Couldn't read the file '"+ filePath + "'.\n\n", e);
            e.printStackTrace();
        }
        return new char[0][0];
    }
}