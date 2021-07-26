package it.unicam.cs.pa2021.jVectorRacetrack.model.track;

import java.util.Deque;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Represents a track for the Vector Racetrack Game,
 * made of cells that are grouped in different zones.
 * @param <L> Type of location for the zones in the track
 */
public interface Racetrack<L> {

    /**
     * Returns the <code>Cell</code> at the given location
     * @param loc the <code>Cell</code> location
     * @return the <code>Cell</code> in the desired location
     * @throws IndexOutOfBoundsException if the given location is not valid
     */
    Cell<L> getCellAt(L loc) throws IndexOutOfBoundsException;

    /**
     * Returns all the cells that satisfy the given <code>Zone</code> predicate
     * @param zonePredicate the <code>Zone</code> predicate
     * @return a set of the cell that satisfy the required <code>Zone</code> predicate
     */
    Set<Cell<L>> getCellsWith(Predicate<Zone> zonePredicate);

    /**
     * Returns the cells inline (either vertically or horizontally) between two locations.
     * @param start start location.
     * @param end end location.
     * @return a list of the cells ordered, starting from the start location until the end location.
     * @see GridLocation#getInlineLocationsUpTo(GridLocation)
     */
    List<Cell<L>> getInlineCells(L start, L end);

    /**
     * Returns the neighbour cells of a given <code>Cell</code> location.
     * @param loc <code>Cell</code> location from where to get the relative neighbours.
     * @return a <code>Set</code> of the neighbour cells.
     */
    Set<Cell<L>> getNeighbourCells(L loc) throws IndexOutOfBoundsException;

    /**
     * Returns the target stack of checkpoints that have to be traversed in order to complete the track.
     *
     * @return a double ended queue of all the checkpoints.
     */
    Deque<Zone> getTargetStack();

    /**
     * Returns the zone on the given the location
     * @param loc the location on the track
     * @return the zone on the given location
     */
    default Zone getZoneAt(L loc) {
        return getCellAt(loc).getZone();
    }

    /**
     * Checks if the racetrack is circular.
     * @return true if the racetrack is circular, otherwise false.
     */
    boolean isCircular();

    /**
     * Checks if a track is valid to play the game.
     * That is, if there is at least a starting point and a place to move to.
     * @return true if the track is valid, otherwise false.
     */
    boolean isValid();
}
