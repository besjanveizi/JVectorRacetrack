package it.unicam.cs.pa2021.jVectorRacetrack.model.track;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents a <code>Racetrack</code> in a 2D grid.
 */
public class GridRacetrack implements Racetrack<GridLocation> {

    public final Cell<GridLocation> [][] grid;
    private final int width;
    private final int height;
    private boolean inFlag = false;
    private boolean sFlag = false;
    private boolean fFlag = false;
    private boolean c1Flag = false;
    private boolean c2Flag = false;
    private boolean c3Flag = false;
    private boolean c4Flag = false;

    /**
     * Creates a 2D track with the given dimensions and setting each <code>Cell</code> <code>Zone</code> accordingly.
     * @param width width of the track
     * @param height height of the track
     * @param zones 2D array used to set the zone for each <code>Cell</code>
     */
    public GridRacetrack(int width, int height, Zone[][] zones) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[height][width];
        for( int r=0 ; r < height ; r++ ) {
            for( int c=0 ; c < width ; c++ ) {
                if (!allFlagsAreSet()) setFlags(zones[r][c]);
                this.grid[r][c] = new GridCell(new GridLocation(r,c), zones[r][c], this);
            }
        }
    }


    @Override
    public final Cell<GridLocation> getCellAt(GridLocation loc) throws IndexOutOfBoundsException {
        if (!isValid(loc)) {
            throw new IndexOutOfBoundsException("Location not valid");
        }
        return grid[loc.getRow()][loc.getCol()];
    }

    @Override
    public Set<Cell<GridLocation>> getNeighbourCells(GridLocation loc) throws IndexOutOfBoundsException {
        Set<Cell<GridLocation>> cellSet = getCells(loc.getNeighbourLocations(height, width));
        if (cellSet.isEmpty()) {
            throw new IndexOutOfBoundsException("No neighbour cells");
        }
        else {
            return cellSet;
        }
    }

    @Override
    public Set<Cell<GridLocation>> getCellsWith(Predicate<Zone> zonePredicate) {
        return Arrays.stream(grid).flatMap(Arrays::stream).filter((cell) -> zonePredicate.test(cell.getZone()))
                .collect(Collectors.toSet());
    }

    @Override
    public List<Cell<GridLocation>> getInlineCells(GridLocation startLoc, GridLocation endLoc){
        try {
            return getCells(startLoc.getInlineLocationsUpTo(endLoc));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Deque<Zone> getTargetStack() {
        Deque<Zone> targetStack = new LinkedList<>();
        if (isCircular()) targetStack.offerFirst(Zone.START);
        else targetStack.offerFirst(Zone.FINISH);
        if (c4Flag) targetStack.offerFirst(Zone.CHECKPOINT4);
        if (c3Flag) targetStack.offerFirst(Zone.CHECKPOINT3);
        if (c2Flag) targetStack.offerFirst(Zone.CHECKPOINT2);
        if (c1Flag) targetStack.offerFirst(Zone.CHECKPOINT1);
        return targetStack;
    }

    private void setFlags(Zone zone) {
        switch (zone) {
            case INSIDE: inFlag = true; break;
            case START: sFlag = true; break;
            case FINISH: fFlag = true; break;
            case CHECKPOINT1: c1Flag = true; break;
            case CHECKPOINT2: c2Flag = true; break;
            case CHECKPOINT3: c3Flag = true; break;
            case CHECKPOINT4: c4Flag = true; break;
        }
    }

    private boolean allFlagsAreSet() {
        return inFlag && sFlag && fFlag && c1Flag && c2Flag && c3Flag && c4Flag;
    }

    private List<Cell<GridLocation>> getCells(List<GridLocation> inlineLocations) {
        return inlineLocations.stream().map(this::getCellAt).collect(Collectors.toList());
    }

    @Override
    public boolean isCircular() {
        return isValid() && !fFlag;
    }

    @Override
    public boolean isValid() {
        return sFlag && (inFlag || c1Flag || c2Flag || c3Flag || c4Flag || fFlag);
    }

    private Set<Cell<GridLocation>> getCells(Set<GridLocation> neighbourLocations) {
        return neighbourLocations.stream().map(this::getCellAt).collect(Collectors.toSet());
    }

    /**
     * Check if a location is valid, therefore inside the track boundaries
     * @param loc location to check the validity
     * @return true if the location is valid, otherwise false
     */
    public boolean isValid(GridLocation loc){
        return isValid(loc.getRow(), loc.getCol());
    }

    private boolean isValid(int row, int column) {
        return (0<=column)&&(column<width)&&(0<=row)&&(row<height);
    }
}
