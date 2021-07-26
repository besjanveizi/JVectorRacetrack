package it.unicam.cs.pa2021.jVectorRacetrack.model.track;

import com.google.common.base.Objects;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the coordinates in a grid used to locate a <code>Cell</code>
 */
public class GridLocation {
    private final int row;
    private final int col;

    /**
     * Creates a location that refers to the given <code>x</code> and <code>y</code> values.
     * @param row <code>x</code> value of the location.
     * @param col <code>y</code> value of the location.
     */
    public GridLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Return the <code>x</code> coordinate of the <code>GridLocation</code>.
     * @return the <code>x</code> coordinate of the <code>GridLocation</code>.
     */
    public int getRow() {
        return row;
    }

    /**
     * Return the <code>y</code> coordinate of the <code>GridLocation</code>.
     * @return the <code>y</code> coordinate of the <code>GridLocation</code>.
     */
    public int getCol() {
        return col;
    }

    /**
     * Check if the location is vertically aligned to another one.
     * @param that the location to evaluate the vertical alignment.
     * @return true if the locations are vertically aligned, otherwise false.
     */
    public boolean isVerticalTo(GridLocation that) {
        return this.horizontalDistanceFrom(that)==0;
    }

    /**
     * Check if the location is horizontally aligned to another one.
     * @param that the location to evaluate the horizontal alignment.
     * @return true if the locations are horizontally aligned, otherwise false.
     */
    public boolean isHorizontalTo(GridLocation that) {
        return this.verticalDistanceFrom(that)==0;
    }

    /**
     * Return the horizontal distance from another <code>GridLocation</code>.
     * @param that the location to evaluate the horizontal distance from.
     * @return a positive number if the given <code>GridLocation</code> further right the called one,
     * negative if it's further left, 0 if the horizontal distance between the two is the same
     */
    public int horizontalDistanceFrom(GridLocation that) {
        return that.getCol() - this.getCol();
    }

    /**
     * Return the vertical distance from another <code>GridLocation</code>.
     * @param that the location to evaluate the vertical distance from.
     * @return a positive number if the given <code>GridLocation</code> is further above the called one,
     * negative if it's further below, 0 if the vertical distance between the two is the same
     */
    public int verticalDistanceFrom(GridLocation that) {
        return that.getRow() - this.getRow();
    }

    /**
     * Return all the locations inline (horizontally or vertically) from the called <code>GridLocation</code>
     * to the given one.
     * @param that given <code>GridLocation</code>
     * @return a list with all the inline locations.
     * @throws IllegalArgumentException in case the locations are not inline (neither vertically nor horizontally).
     */
    public List<GridLocation> getInlineLocationsUpTo(GridLocation that) throws IllegalArgumentException {
        if (this.isVerticalTo(that))
            return getInlineLocationsUpTo(that, Math.max(this.getRow(), that.getRow()), Math.min(this.getRow(), that.getRow()));
        else if (this.isHorizontalTo(that))
            return getInlineLocationsUpTo(that, Math.max(this.getCol(), that.getCol()), Math.min(this.getCol(), that.getCol()));
        else throw new IllegalArgumentException("The locations are not inline with each other");
    }

    private List<GridLocation> getInlineLocationsUpTo(GridLocation that, int max, int min) {
        List<GridLocation> list = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            list.add(new GridLocation(isHorizontalTo(that) ? this.getRow() : i, isVerticalTo(that) ? this.getCol() : i));
        }
        if ((isVerticalTo(that) ? this.getRow() : this.getCol()) == max) {
            Collections.reverse(list);
        }
        return list;
    }

    /**
     * Returns the neighbour locations that surround the current <code>GridLocation</code> that respect
     * the given boundaries.
     * @param rowBound x coordinate boundary
     * @param colBound y coordinate boundary
     * @return a <code>Set</code> of the neighbour locations
     */
    public Set<GridLocation> getNeighbourLocations(int rowBound, int colBound) {
        return Stream.of(this.top(rowBound,colBound),
                this.top_left(rowBound,colBound),
                this.top_right(rowBound,colBound),
                this.bottom(rowBound,colBound),
                this.bottom_left(rowBound,colBound),
                this.bottom_right(rowBound,colBound),
                this.left(rowBound,colBound),
                this.right(rowBound,colBound)).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
    }


    public Optional<GridLocation> top(int rowBound, int colBound) {
        return getNeighbour(rowBound,colBound,0,-1);
    }

    public Optional<GridLocation> top_left(int rowBound, int colBound) {
        return getNeighbour(rowBound,colBound,-1,-1);
    }

    public Optional<GridLocation> top_right(int rowBound, int colBound) {
        return getNeighbour(rowBound,colBound,+1,-1);
    }

    public Optional<GridLocation> bottom(int rowBound, int colBound) {
        return getNeighbour(rowBound,colBound,0, +1);
    }

    public Optional<GridLocation> bottom_left(int rowBound, int colBound) {
        return getNeighbour(rowBound,colBound,-1,+1);
    }

    public Optional<GridLocation> bottom_right(int rowBound, int colBound) {
        return getNeighbour(rowBound,colBound,+1,+1);
    }

    public Optional<GridLocation> left(int rowBound, int colBound) {
        return getNeighbour(rowBound,colBound,-1, 0);
    }

    public Optional<GridLocation> right(int rowBound, int colBound) {
        return getNeighbour(rowBound,colBound,+1,0);
    }

    private Optional<GridLocation> getNeighbour(int rowBound, int colBound, int dx, int dy) {
        int newR = row +dy;
        int newC = col +dx;
        if ((0<= newR)&&(newR<rowBound)&&(0<=newC)&&(newC<colBound)) {
            return Optional.of(new GridLocation(newR,newC));
        } else {
            return Optional.empty();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridLocation that = (GridLocation) o;
        return getRow() == that.getRow() && getCol() == that.getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getRow(), getCol());
    }

    @Override
    public String toString() {
        return "[" + row +
                ", " + col +
                ']';
    }
}
