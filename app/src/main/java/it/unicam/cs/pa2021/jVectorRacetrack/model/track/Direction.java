package it.unicam.cs.pa2021.jVectorRacetrack.model.track;

/**
 * Represents the direction of the <code>Vector</code>
 */
public enum Direction {
    NORTH       ( 0, 1),
    NORTH_WEST  (-1, 1),
    WEST        (-1, 0),
    SOUTH_WEST  (-1,-1),
    SOUTH       ( 0,-1),
    SOUTH_EAST  ( 1,-1),
    EAST        ( 1, 0),
    NORTH_EAST  ( 1, 1);

    private final int dx;
    private final int dy;

    /**
     * Creates a new <code>Direction</code> given the horizontal and vertical coordinates.
     * @param dx horizontal coordinate
     * @param dy vertical coordinate
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the horizontal coordinate of the <code>Direction</code>
     * @return horizontal coordinate.
     */
    public int getDx() {
        return dx;
    }

    /**
     * Returns the vertical coordinate of the <code>Direction</code>
     * @return vertical coordinate
     */
    public int getDy() {
        return dy;
    }
}