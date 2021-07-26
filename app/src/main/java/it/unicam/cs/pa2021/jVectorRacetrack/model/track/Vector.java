package it.unicam.cs.pa2021.jVectorRacetrack.model.track;

import it.unicam.cs.pa2021.jVectorRacetrack.model.rules.Rule;

import java.util.Set;

/**
 * Represents a <code>Movement</code> in a 2D track.
 */
public class Vector implements Movement<GridLocation> {

    private final Cell<GridLocation> startingCell;
    private final Cell<GridLocation> endingCell;
    private final Racetrack<GridLocation> track;

    private final CellSelector<GridLocation> selector;
    private Direction direction;

    private final int horizontalSpeed;
    private final int verticalSpeed;

    /**
     * Creates a new <code>Vector</code>.
     * @param startingCell <code>Cell</code> where the <code>Vector</code> starts.
     * @param endingCell <code>Cell</code> where the <code>Vector</code> ends.
     * @param track <code>Racetrack</code> where the <code>Vector</code> is operating.
     */
    public Vector(Cell<GridLocation> startingCell, Cell<GridLocation> endingCell, Racetrack<GridLocation> track) {
        this.track = track;
        this.startingCell = startingCell;
        this.endingCell = endingCell;
        setDirection(startingCell.getLocation(), endingCell.getLocation());

        selector = new GridCellSelector(track, this);

        horizontalSpeed = getHorizontalSpeed();
        verticalSpeed = getVerticalSpeed();
    }

    private void setDirection(GridLocation start, GridLocation end) {
        if (start.isVerticalTo(end)) {
            if (start.verticalDistanceFrom(end) > 0) this.direction = Direction.SOUTH;
            else this.direction = Direction.NORTH;
        }
        else if (start.horizontalDistanceFrom(end) < 0) {
            if (start.verticalDistanceFrom(end) > 0) this.direction = Direction.SOUTH_WEST;
            else if (start.isHorizontalTo(end)) this.direction = Direction.WEST;
            else this.direction = Direction.NORTH_WEST;
        }
        else {
            if (start.verticalDistanceFrom(end) < 0) this.direction = Direction.NORTH_EAST;
            else if (start.isHorizontalTo(end)) this.direction = Direction.EAST;
            else this.direction = Direction.SOUTH_EAST;
        }
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public Set<Cell<GridLocation>> apply(Rule<GridLocation> rule) {
        return rule.apply(track, this.endingCell, getNextEndingCell());
    }

    @Override
    public Cell<GridLocation> getNextEndingCell() throws IndexOutOfBoundsException{
        return this.track.getCellAt(new GridLocation(endingCell.getLocation().getRow() + verticalSpeed,
                                                     endingCell.getLocation().getCol() + horizontalSpeed));
    }

    private int getVerticalSpeed() {
        return startingCell.getLocation().verticalDistanceFrom(endingCell.getLocation());
    }

    private int getHorizontalSpeed() {
        return startingCell.getLocation().horizontalDistanceFrom(endingCell.getLocation());
    }

    @Override
    public Cell<GridLocation> getStartingCell() {
        return this.startingCell;
    }

    @Override
    public Cell<GridLocation> getEndingCell() {
        return this.endingCell;
    }

    @Override
    public CellSelector<GridLocation> getSelector() {
        return this.selector;
    }
}
