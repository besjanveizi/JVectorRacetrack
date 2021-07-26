package it.unicam.cs.pa2021.jVectorRacetrack.controller;

import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Zone;

import java.io.IOException;

/**
 * Represents the loader for a 2D version <code>Racetrack</code>
 */
public interface Track2DLoader {

    /**
     * Returns the height of the track.
     * @return height of the track.
     */
    int getHeight();

    /**
     * Returns the width of the track.
     * @return width of the track.
     */
    int getWidth();

    /**
     * Returns the zones for the <code>Racetrack</code> loading.
     * @return a 2D array of <code>Zones</code>
     * @throws IOException if the file does not comply with the chosen parsing characters
     */
    Zone[][] getZones() throws IOException;

    /**
     * Check if the file is valid for loading the <code>Racetrack</code> and playing the game.
     * @return true if the file is valid, otherwise false.
     */
    boolean isValid();
}
