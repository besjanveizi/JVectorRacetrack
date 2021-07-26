package it.unicam.cs.pa2021.jVectorRacetrack.view;

import it.unicam.cs.pa2021.jVectorRacetrack.model.player.Player;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.GridLocation;

import java.util.List;

/**
 * Represents the game results in String format.
 */
public final class GameResults {

    private final List<Player<GridLocation>> players;

    public GameResults(List<Player<GridLocation>> players) {
        this.players = players;
    }

    private String getEachPlayerResults() {
        StringBuilder results = new StringBuilder("\n\n=======================================");
        results.append("\n\tGAME RESULTS").append("\n=======================================");
        for (Player<GridLocation> p : players) {
            results.append("\n---------------------------------------");
            results.append("\nPLAYER: ").append(p.getName());
            if (p.hasFinished()) results.append("\nSTATUS: ").append(p.getStatus());
            else results.append("\nSTATUS: NOT FINISHED");
            results.append("\nTOTAL CRASHES: ").append(p.getCrashes());
            results.append("\nTOTAL MOVES: ").append(p.getMoves());
        }
        results.append("\n\n");
        return results.toString();
    }

    @Override
    public String toString() {
        return getEachPlayerResults();
    }
}
