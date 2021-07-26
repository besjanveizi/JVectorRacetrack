package it.unicam.cs.pa2021.jVectorRacetrack.model.rules;

import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Cell;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Racetrack;

import java.util.Set;

/**
 * Represents a default <code>Rule</code> used to calculate the 8 neighbour cells
 * of the known ending <code>Cell</code> of a <code>Movement</code>.
 */
public class DefaultRule<L> implements Rule<L> {

    @Override
    public Set<Cell<L>> apply(Racetrack<L> track, Cell<L> currentEndCell, Cell<L> nextEndCell) {
        Set<Cell<L>> cellSet = track.getNeighbourCells(nextEndCell.getLocation());
        cellSet.remove(currentEndCell);
        return cellSet;
    }
}
