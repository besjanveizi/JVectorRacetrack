package it.unicam.cs.pa2021.jVectorRacetrack.model.track;

import java.util.ArrayList;
import java.util.List;

public final class GridCellSelector implements CellSelector<GridLocation> {

    private final Racetrack<GridLocation> track;
    private final Movement<GridLocation> movement;
    private final List<Cell<GridLocation>> traversedCells;

    private final GridLocation startLoc;
    private final GridLocation endLoc;

    private final int columnDistance;
    private final int rowDistance;

    public GridCellSelector(Racetrack<GridLocation> track, Movement<GridLocation> movement) {
        this.track = track;
        this.movement = movement;
        startLoc = movement.getStartingCell().getLocation();
        endLoc = movement.getEndingCell().getLocation();

        traversedCells = new ArrayList<>();

        columnDistance = Math.abs(startLoc.horizontalDistanceFrom(endLoc));
        rowDistance = Math.abs(startLoc.verticalDistanceFrom(endLoc));
    }

    @Override
    public List<Cell<GridLocation>> select() {
        if (rowDistance > columnDistance) {
            stepSelectVertically(startLoc, endLoc);
        } else if (columnDistance > rowDistance) {
            stepSelectHorizontally(startLoc, endLoc);
        } else {
            stepSelectInBisection(startLoc, endLoc);
        }
        return traversedCells;
    }

    private double calculateBorderCoordinate(GridLocation startLoc, GridLocation endLoc, double knownCoordinate ) {
        int x1 = startLoc.getRow();
        int y1 = startLoc.getCol();
        int x2 = endLoc.getRow();
        int y2 = endLoc.getCol();
        if (columnDistance > rowDistance)
            return y1 + (knownCoordinate-x1)/(x2-x1)*(y2-y1);
        else
            return x1 + (knownCoordinate-y1)/(y2-y1)*(x2-x1);
    }

    private void stepSelectInBisection(GridLocation startLoc, GridLocation endLoc) {
        Direction direction = movement.getDirection();
        for (int i = 0; i < rowDistance; i++) {
            if (direction.getDx() - direction.getDy() == 0) {
                bisectionStep(startLoc.getRow() - i * direction.getDx(), startLoc.getCol() + i * direction.getDy(), direction);
            }
            else {
                bisectionStep(startLoc.getRow() + i * direction.getDx(), startLoc.getCol() - i * direction.getDy(), direction);
            }
        }
        traversedCells.add(track.getCellAt(endLoc));
    }

    private void bisectionStep(int row, int col, Direction direction) {
        traversedCells.add(track.getCellAt(new GridLocation(row, col)));
        bisectionSelectTwoObliques(new GridLocation(row, col), direction);
    }

    private void stepSelectHorizontally(GridLocation startLoc, GridLocation endLoc) {
        if (startLoc.isHorizontalTo(endLoc)) traversedCells.addAll(track.getInlineCells(startLoc, endLoc));
        else {
            double coordinate = calculateBorderCoordinate(movement.getStartingCell().getLocation(), endLoc, startLoc.getRow() - movement.getDirection().getDy()*(0.5));
            if (coordinate > (int)coordinate+0.5) {
                traversedCells.addAll( track.getInlineCells(startLoc, new GridLocation(startLoc.getRow(), (int)coordinate+1)) );
                stepSelectHorizontally(new GridLocation(startLoc.getRow()-movement.getDirection().getDy(), (int)coordinate+1), endLoc);
            }
            else if( coordinate < (int)coordinate+0.5) {
                traversedCells.addAll( track.getInlineCells(startLoc, new GridLocation(startLoc.getRow(), (int)coordinate)) );
                stepSelectHorizontally(new GridLocation(startLoc.getRow()-movement.getDirection().getDy(), (int)coordinate), endLoc);
            }
            else {
                if (movement.getDirection().getDx() == -1) coordinate = coordinate + 1;
                traversedCells.addAll( track.getInlineCells(startLoc, new GridLocation(startLoc.getRow(), (int)coordinate) ));
                bisectionSelectTwoObliques( new GridLocation(startLoc.getRow(), (int)coordinate), movement.getDirection());
                stepSelectHorizontally( new GridLocation(startLoc.getRow()-movement.getDirection().getDy(), (int)coordinate+movement.getDirection().getDx()), endLoc);
            }
        }
    }

    private void stepSelectVertically(GridLocation startLoc, GridLocation endLoc) {
        if (startLoc.isVerticalTo(endLoc)) traversedCells.addAll(track.getInlineCells(startLoc, endLoc));
        else {
            double coordinate = calculateBorderCoordinate(movement.getStartingCell().getLocation(), endLoc, startLoc.getCol() + movement.getDirection().getDx()*(0.5));
            if (coordinate > (int)coordinate+0.5) {
                traversedCells.addAll( track.getInlineCells(startLoc, new GridLocation((int)coordinate+1, startLoc.getCol())) );
                stepSelectVertically(new GridLocation((int)coordinate+1, startLoc.getCol()+movement.getDirection().getDx()), endLoc);
            }
            else if( coordinate < (int)coordinate+0.5) {
                traversedCells.addAll( track.getInlineCells(startLoc, new GridLocation((int)coordinate, startLoc.getCol())) );
                stepSelectVertically(new GridLocation((int)coordinate, startLoc.getCol()+movement.getDirection().getDx()), endLoc);
            }
            else {
                if (movement.getDirection().getDy() == 1) coordinate = coordinate + 1;
                traversedCells.addAll( track.getInlineCells( startLoc, new GridLocation((int)coordinate, startLoc.getCol())));
                bisectionSelectTwoObliques( new GridLocation((int)coordinate, startLoc.getCol()), movement.getDirection());
                stepSelectVertically( new GridLocation((int)coordinate-movement.getDirection().getDy(), startLoc.getCol()+movement.getDirection().getDx()), endLoc);
            }
        }
    }

    private void bisectionSelectTwoObliques(GridLocation gridLocation, Direction direction) {
        GridLocation loc1 = new GridLocation(gridLocation.getRow(), gridLocation.getCol()+direction.getDx());
        GridLocation loc2 = new GridLocation(gridLocation.getRow()-direction.getDy(), gridLocation.getCol());
        if (track.getCellAt(loc1).getZone() == track.getCellAt(loc2).getZone()) {
            traversedCells.add(track.getCellAt(loc1));
            traversedCells.add(track.getCellAt(loc2));
        }
    }
}
