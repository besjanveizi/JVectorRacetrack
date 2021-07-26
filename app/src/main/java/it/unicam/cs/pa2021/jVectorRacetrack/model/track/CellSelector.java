package it.unicam.cs.pa2021.jVectorRacetrack.model.track;

import java.util.List;

/**
 * This interface represents a selector of cells.
 * It is used to select all cells traversed by a <code>Movement</code>.
 * @param <L> type of location of the cells.
 */
public interface CellSelector<L> {

    /**
     * Selects all the traversed cells and returns them in order of traversing.
     * @return an ordered list of all traversed cells.
     */
    List<Cell<L>> select();
}
