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
        if (this.isVerticalTo(that)) return getVerticalLocationsUpTo(that);
        else if (this.isHorizontalTo(that)) return getHorizontalLocationsUpTo(that);
        else throw new IllegalArgumentException("The locations are not inline with each other");
    }

    /**
     * Return all the locations inline horizontally from the called <code>GridLocation</code>
     * to the given one.
     * The locations will be organized in order starting from the one the method is called on,
     * until the given <code>GridLocation</code> considering the <code>x</code> coordinate.
     * @param that given <code>GridLocation</code>
     * @return a list with all the locations ordered from the one the method is called on,
     * until the given <code>GridLocation</code>.
     */
    public List<GridLocation> getHorizontalLocationsUpTo(GridLocation that) {
        List<GridLocation> list = new ArrayList<>();
        int max = Math.max(this.getCol(), that.getCol());
        int min = Math.min(this.getCol(), that.getCol());
        for (int r = min; r <= max; r++) {
            list.add(new GridLocation(this.getRow(), r));
        }
        if (this.getCol() == max) {
            Collections.reverse(list);
        }
        return list;
    }

    /**
     * Return all the locations inline vertically from the called <code>GridLocation</code>
     * to the given one.
     * The locations will be organized in order starting from the one the method is called on,
     * until the given <code>GridLocation</code> considering the <code>y</code> coordinate.
     * @param that given <code>GridLocation</code>
     * @return a list with all the locations ordered from the one the method is called on,
     * until the given <code>GridLocation</code>.
     */
    public List<GridLocation> getVerticalLocationsUpTo(GridLocation that) {
        List<GridLocation> list = new ArrayList<>();
        int max = Math.max(this.getRow(), that.getRow());
        int min = Math.min(this.getRow(), that.getRow());
        for (int c = min; c <= max; c++) {
            list.add(new GridLocation(c, this.getCol()));
        }
        if (this.getRow() == max) {
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
        int newY = col +dx;
        if ((0<= newR)&&(newR<rowBound)&&(0<=newY)&&(newY<colBound)) {
            return Optional.of(new GridLocation(newR,newY));
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
