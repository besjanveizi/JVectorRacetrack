package it.unicam.cs.pa2021.jVectorRacetrack.model.track;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

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
        Assert.assertTrue(grid[3][1].isVerticalTo(grid[1][1]));
    }

    @Test
    public void shouldBeDistant2_vertically() {
        Assert.assertEquals(2, grid[1][1].verticalDistanceFrom(grid[3][1]));
    }

    @Test
    public void shouldBeHorizontal() {
        Assert.assertTrue(grid[1][3].isHorizontalTo(grid[1][1]));
    }
    @Test
    public void shouldBeDistant2_horizontally() {
        Assert.assertEquals(2, grid[1][1].horizontalDistanceFrom(grid[1][3]));
    }

    @Test
    public void shouldThrowException_LocationsNotInline() {
        IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class,
                () -> grid[1][1].getInlineLocationsUpTo(grid[3][3]));
        Assert.assertEquals("The locations are not inline with each other", exception.getMessage());
    }

    @Test
    public void shouldBeTheSameLocation_horizontal() {
        GridLocation start = grid[1][1];
        GridLocation end = grid[1][3];

        Assert.assertEquals(Arrays.asList(grid[1][1], grid[1][2], grid[1][3]), start.getHorizontalLocationsUpTo(end));
    }

    @Test
    public void shouldBeTheSameLocation_horizontal_reverseOrder() {
        GridLocation start = grid[1][3];
        GridLocation end = grid[1][1];

        Assert.assertEquals(Arrays.asList(grid[1][3], grid[1][2], grid[1][1]), start.getHorizontalLocationsUpTo(end));
    }

    @Test
    public void shouldBeTheSameLocation_vertical() {
        GridLocation start = grid[1][1];
        GridLocation end = grid[3][1];

        Assert.assertEquals(Arrays.asList(grid[1][1], grid[2][1], grid[3][1]), start.getVerticalLocationsUpTo(end));
    }

    @Test
    public void shouldBeTheSameLocation_vertical_reverseOrder() {
        GridLocation start = grid[3][1];
        GridLocation end = grid[1][1];

        Assert.assertEquals(Arrays.asList(grid[3][1], grid[2][1], grid[1][1]), start.getVerticalLocationsUpTo(end));
    }

    @Test
    public void shouldGetAll8Neighbours() {
        GridLocation loc = grid[2][1];
        Set<GridLocation> set = Set.of(grid[1][2], grid[2][2], grid[3][2],
                                       grid[1][1], /* myLoc */ grid[3][1],
                                       grid[1][0], grid[2][0], grid[3][0]);

        Assert.assertEquals(set, loc.getNeighbourLocations(4, 4));
    }

    @Test
    public void shouldGetNeighbour_left_bottomLeft_bottom() {
        GridLocation loc = grid[3][3];
        Set<GridLocation> set = Set.of(grid[2][3], /* myLoc */
                                       grid[2][2], grid[3][2]);

        Assert.assertEquals(set, loc.getNeighbourLocations(4, 4));
    }
}