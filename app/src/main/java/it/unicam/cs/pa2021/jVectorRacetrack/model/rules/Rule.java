package it.unicam.cs.pa2021.jVectorRacetrack.model.rules;

import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Cell;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Racetrack;

import java.util.Set;

/**
 * Represents the rule that is applied for the possible next movements a <code>Player</code> can do.
 * @param <L> type of the location of cells the <code>Player</code> makes a movement.
 */
public interface Rule<L> {

    /**
     * Given a <code>Movement</code> and the ending <code>Cell</code> of the next movement,
     * applies rules to obtain a set of all the other possible ending locations in the next movement
     * besides the already known ending cell.
     *
     * @param track <code>Racetrack</code> where the movement is done.
     * @param currentEndCell ending <code>Cell</code> of the current movement.
     * @param nextEndCell ending <code>Cell</code> of the next movement.
     * @return set of all the possible ending cells besides the given one.
     */
    Set<Cell<L>> apply(Racetrack<L> track, Cell<L> currentEndCell, Cell<L> nextEndCell);
}
