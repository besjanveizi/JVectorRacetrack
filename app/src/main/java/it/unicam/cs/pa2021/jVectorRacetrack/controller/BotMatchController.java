package it.unicam.cs.pa2021.jVectorRacetrack.controller;

import it.unicam.cs.pa2021.jVectorRacetrack.model.player.BotPlayer;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Cell;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.GridLocation;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Zone;

import java.util.Random;
import java.util.Set;

/**
 * Represents a controller for a match between only bot players
 */
public class BotMatchController extends GameController{
    private final int numPlayers;

    public BotMatchController(String trackFilePath, int numPlayers) {
        super(trackFilePath, numPlayers);
        this.numPlayers = numPlayers;
    }

    @Override
    public void loadPlayers() {
        Set<Cell<GridLocation>> possibleStartPoints = getTrack().getCellsWith(z -> z == Zone.START);
        for (int i = 0; i < numPlayers; i++) {
            Cell<GridLocation> start = possibleStartPoints.stream()
                    .skip(new Random().nextInt(possibleStartPoints.size()))
                    .findFirst().orElse(null);
            players.add(new BotPlayer(getTrack(), start));
        }
    }

}
