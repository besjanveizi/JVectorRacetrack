package it.unicam.cs.pa2021.jVectorRacetrack.model.track;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class GridLocationTest{
    private final int height = 4;
    private final int width = 4;
    private final GridLocation[][] grid = new GridLocation[height][width];

    @Before
    public void setUp(){
        for(int r = 0; r< height; r++ ) {
            for( int c=0 ; c<width ; c++ ) {
                grid[r][c] = new GridLocation(r,c);
            }
        }
    }

    @Test
    public void shouldBeVertical() {
        assertTrue(grid[3][1].isVerticalTo(grid[1][1]));
    }

    @Test
    public void shouldBeDistant2_vertically() {
        assertEquals(2, grid[1][1].verticalDistanceFrom(grid[3][1]));
    }

    @Test
    public void shouldBeHorizontal() {
        assertTrue(grid[1][3].isHorizontalTo(grid[1][1]));
    }
    @Test
    public void shouldBeDistant2_horizontally() {
        assertEquals(2, grid[1][1].horizontalDistanceFrom(grid[1][3]));
    }

    @Test
    public void shouldThrowException_LocationsNotInline() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> grid[1][1].getInlineLocationsUpTo(grid[3][3]));
        assertEquals("The locations are not inline with each other", exception.getMessage());
    }

    @Test
    public void shouldBeTheSameLocation_horizontal() {
        GridLocation start = grid[1][1];
        GridLocation end = grid[1][3];

        assertEquals(Arrays.asList(grid[1][1], grid[1][2], grid[1][3]), start.getInlineLocationsUpTo(end));
    }

    @Test
    public void shouldBeTheSameLocation_horizontal_reverseOrder() {
        GridLocation start = grid[1][3];
        GridLocation end = grid[1][1];

        assertEquals(Arrays.asList(grid[1][3], grid[1][2], grid[1][1]), start.getInlineLocationsUpTo(end));
    }

    @Test
    public void shouldBeTheSameLocation_vertical() {
        GridLocation start = grid[1][1];
        GridLocation end = grid[3][1];

        assertEquals(Arrays.asList(grid[1][1], grid[2][1], grid[3][1]), start.getInlineLocationsUpTo(end));
    }

    @Test
    public void shouldBeTheSameLocation_vertical_reverseOrder() {
        GridLocation start = grid[3][1];
        GridLocation end = grid[1][1];

        assertEquals(Arrays.asList(grid[3][1], grid[2][1], grid[1][1]), start.getInlineLocationsUpTo(end));
    }

    @Test
    public void shouldGetAll8Neighbours() {
        GridLocation loc = grid[2][1];
        Set<GridLocation> set = Set.of(grid[1][2], grid[2][2], grid[3][2],
                grid[1][1], /* myLoc */ grid[3][1],
                grid[1][0], grid[2][0], grid[3][0]);

        assertEquals(set, loc.getNeighbourLocations(4, 4));
    }

    @Test
    public void shouldGetNeighbour_left_bottomLeft_bottom() {
        GridLocation loc = grid[3][3];
        Set<GridLocation> set = Set.of(grid[2][3], /* myLoc */
                grid[2][2], grid[3][2]);

        assertEquals(set, loc.getNeighbourLocations(4, 4));
    }
}