package it.unicam.cs.pa2021.jVectorRacetrack.model.track;

import it.unicam.cs.pa2021.jVectorRacetrack.model.rules.Rule;

import java.util.List;
import java.util.Set;

/**
 * Represents the way a <code>Player</code> moves in the <code>Racetrack</code>
 * @param <L> Type of the location the movement happens
 */
public interface Movement<L> {

    /**
     * Applies a rule to the current movement to obtain a set of all possible next ending locations.
     * @param rule rule to apply.
     * @return a set of all possible next ending locations.
     */
    Set<Cell<L>> apply(Rule<L> rule);

    /**
     * Returns the movement's <code>Direction</code>
     * @return the movement's <code>Direction</code>
     */
    Direction getDirection();

    /**
     * Returns the ending <code>Cell</code> of the movement
     * @return the ending <code>Cell</code> of the movement
     */
    Cell<L> getEndingCell();

    /**
     * Returns the next ending <code>Cell</code> based on the current movement
     * @return the next ending <code>Cell</code>
     */
    Cell<L> getNextEndingCell() throws IndexOutOfBoundsException;

    /**
     * Returns the movement's <code>CellSelector</code>.
     * @return the movement's <code>CellSelector</code>.
     */
    CellSelector<L> getSelector();

    /**
     * Provides the starting <code>Cell</code> of the movement
     * @return starting <code>Cell</code> of the movement
     */
    Cell<L> getStartingCell();

    /**
     * Returns the cells the movement went through.
     * @return the traversed cells.
     */
    default List<Cell<L>> getTraversedCells() {
        return getSelector().select();
    }
}
