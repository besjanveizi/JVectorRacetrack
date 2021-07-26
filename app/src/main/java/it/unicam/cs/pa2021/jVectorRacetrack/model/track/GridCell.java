package it.unicam.cs.pa2021.jVectorRacetrack.model.track;

/**
 * Represents a <code>Cell</code> in a 2D <code>Racetrack</code>
 * @param <L> type of location of the cell.
 */
public class GridCell<L extends GridLocation> implements Cell<L> {

    private final L cellLoc;
    private final Zone cellZone;
    private final Racetrack<L> track;

    /**
     * Creates a new <code>GridCell</code>
     * @param location location of the cell.
     * @param cellZone zone type of the cell.
     * @param track track which the cell belongs to.
     */
    public GridCell(L location, Zone cellZone, Racetrack<L> track) {
        this.cellLoc = location;
        this.cellZone = cellZone;
        this.track = track;
    }

    @Override
    public L getLocation() {
        return this.cellLoc;
    }

    @Override
    public Zone getZone() {
        return this.cellZone;
    }

    @Override
    public Racetrack<L> getTrack() {
        return this.track;
    }

    @Override
    public String toString() {
        return "[" + cellLoc.getRow() +
                ", " + cellLoc.getCol() + "]" +
                " - cellZone: " + cellZone;
    }
}
