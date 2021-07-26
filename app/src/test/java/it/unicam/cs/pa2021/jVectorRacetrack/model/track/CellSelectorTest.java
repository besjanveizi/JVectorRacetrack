package it.unicam.cs.pa2021.jVectorRacetrack.model.track;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CellSelectorTest {
    private Racetrack<GridLocation> track;
    private Movement<GridLocation> vector;

    @Before
    public void setUp() {
        int width = 20;
        int height = 20;
        Zone[][] matrix = new Zone[width][height];
        for (int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                if (r == 3 && c == 3) {
                    matrix[r][c] = Zone.OUTSIDE;
                }
                else matrix[r][c] = Zone.INSIDE;
            }
        }
        track = new GridRacetrack(width, height, matrix);
    }
    /*_____________________________________________________________________________________________*/

    @Test
    public void testSelection_vertical_NW() {
        setUp(new GridLocation(9,9), new GridLocation(4, 7));
        List<Cell<GridLocation>> expectedList = vertical_NW();
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void testSelection_vertical_SE() {
        setUp(new GridLocation(4, 7), new GridLocation(9,9));
        List<Cell<GridLocation>> expectedList = vertical_NW();
        Collections.reverse(expectedList);
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    private List<Cell<GridLocation>> vertical_NW() {
        return Arrays.asList(
                track.getCellAt(new GridLocation(9, 9)),
                track.getCellAt(new GridLocation(8, 9)),
                track.getCellAt(new GridLocation(8, 8)),
                track.getCellAt(new GridLocation(7, 8)),
                track.getCellAt(new GridLocation(6, 8)),
                track.getCellAt(new GridLocation(5, 8)),
                track.getCellAt(new GridLocation(5, 7)),
                track.getCellAt(new GridLocation(4, 7)));
    }

    @Test
    public void testSelection_vertical_SW() {
        setUp(new GridLocation(4, 9), new GridLocation(9,7));
        List<Cell<GridLocation>> expectedList = vertical_SW();
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void testSelection_vertical_NE() {
        setUp(new GridLocation(9,7), new GridLocation(4, 9));
        List<Cell<GridLocation>> expectedList = vertical_SW();
        Collections.reverse(expectedList);
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    private List<Cell<GridLocation>> vertical_SW() {
        return Arrays.asList(
                track.getCellAt(new GridLocation(4, 9)),
                track.getCellAt(new GridLocation(5, 9)),
                track.getCellAt(new GridLocation(5, 8)),
                track.getCellAt(new GridLocation(6, 8)),
                track.getCellAt(new GridLocation(7, 8)),
                track.getCellAt(new GridLocation(8, 8)),
                track.getCellAt(new GridLocation(8, 7)),
                track.getCellAt(new GridLocation(9, 7)));
    }

    /*_____________________________________________________________________________________________*/

    @Test
    public void testSelection_horizontal_NE() {
        setUp(new GridLocation(6,11), new GridLocation(4, 16));
        List<Cell<GridLocation>> expectedList = horizontal_NE();
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void testSelection_horizontal_SW() {
        setUp(new GridLocation(4, 16), new GridLocation(6,11));
        List<Cell<GridLocation>> expectedList = horizontal_NE();
        Collections.reverse(expectedList);
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void testSelection_horizontal_SE() {
        setUp(new GridLocation(4, 11), new GridLocation(6,16));
        List<Cell<GridLocation>> expectedList = horizontal_SE();
        //Collections.reverse(expectedList);
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void testSelection_horizontal_NW() {
        setUp(new GridLocation(6,16), new GridLocation(4, 11));
        List<Cell<GridLocation>> expectedList = horizontal_SE();
        Collections.reverse(expectedList);
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    private List<Cell<GridLocation>> horizontal_SE() {
        return Arrays.asList(
                track.getCellAt(new GridLocation(4, 11)),
                track.getCellAt(new GridLocation(4, 12)),
                track.getCellAt(new GridLocation(5, 12)),
                track.getCellAt(new GridLocation(5, 13)),
                track.getCellAt(new GridLocation(5, 14)),
                track.getCellAt(new GridLocation(5, 15)),
                track.getCellAt(new GridLocation(6, 15)),
                track.getCellAt(new GridLocation(6, 16)));
    }

    private List<Cell<GridLocation>> horizontal_NE() {
        return Arrays.asList(
                track.getCellAt(new GridLocation(6, 11)),
                track.getCellAt(new GridLocation(6, 12)),
                track.getCellAt(new GridLocation(5, 12)),
                track.getCellAt(new GridLocation(5, 13)),
                track.getCellAt(new GridLocation(5, 14)),
                track.getCellAt(new GridLocation(5, 15)),
                track.getCellAt(new GridLocation(4, 15)),
                track.getCellAt(new GridLocation(4, 16)));
    }

    /*_____________________________________________________________________________________________*/

    @Test
    public void testSelection_vertical_bisection_SE() {
        setUp(new GridLocation(1,2), new GridLocation(15, 4));
        List<Cell<GridLocation>> expectedList = vertical_bisection_SE();
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void testSelection_vertical_bisection_NW() {
        setUp(new GridLocation(15, 4), new GridLocation(1,2));
        List<Cell<GridLocation>> expectedList = vertical_bisection_SE();
        Collections.reverse(expectedList);
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void testSelection_vertical_bisection_SW() {
        setUp(new GridLocation(1, 4), new GridLocation(15,2));
        List<Cell<GridLocation>> expectedList = vertical_bisection_SW();
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void testSelection_vertical_bisection_NE() {
        setUp(new GridLocation(15,2), new GridLocation(1, 4));
        List<Cell<GridLocation>> expectedList = vertical_bisection_SW();
        Collections.reverse(expectedList);
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    private List<Cell<GridLocation>> vertical_bisection_SE() {
        return Arrays.asList(
                track.getCellAt(new GridLocation(1, 2)),
                track.getCellAt(new GridLocation(2, 2)),
                track.getCellAt(new GridLocation(3, 2)),
                track.getCellAt(new GridLocation(4, 2)),

                track.getCellAt(new GridLocation(4, 3)),
                track.getCellAt(new GridLocation(5, 2)),

                track.getCellAt(new GridLocation(5 , 3)),
                track.getCellAt(new GridLocation(6 , 3)),
                track.getCellAt(new GridLocation(7 , 3)),
                track.getCellAt(new GridLocation(8 , 3)),
                track.getCellAt(new GridLocation(9 , 3)),
                track.getCellAt(new GridLocation(10, 3)),
                track.getCellAt(new GridLocation(11, 3)),

                track.getCellAt(new GridLocation(11, 4)),
                track.getCellAt(new GridLocation(12, 3)),

                track.getCellAt(new GridLocation(12, 4)),
                track.getCellAt(new GridLocation(13, 4)),
                track.getCellAt(new GridLocation(14, 4)),
                track.getCellAt(new GridLocation(15, 4)));
    }

    private List<Cell<GridLocation>> vertical_bisection_SW() {
        return Arrays.asList(
                track.getCellAt(new GridLocation(1, 4)),
                track.getCellAt(new GridLocation(2, 4)),
                track.getCellAt(new GridLocation(3, 4)),
                track.getCellAt(new GridLocation(4, 4)),

                track.getCellAt(new GridLocation(4, 3)),
                track.getCellAt(new GridLocation(5, 4)),

                track.getCellAt(new GridLocation(5 , 3)),
                track.getCellAt(new GridLocation(6 , 3)),
                track.getCellAt(new GridLocation(7 , 3)),
                track.getCellAt(new GridLocation(8 , 3)),
                track.getCellAt(new GridLocation(9 , 3)),
                track.getCellAt(new GridLocation(10, 3)),
                track.getCellAt(new GridLocation(11, 3)),

                track.getCellAt(new GridLocation(11, 2)),
                track.getCellAt(new GridLocation(12, 3)),

                track.getCellAt(new GridLocation(12, 2)),
                track.getCellAt(new GridLocation(13, 2)),
                track.getCellAt(new GridLocation(14, 2)),
                track.getCellAt(new GridLocation(15, 2)));
    }


    /*_____________________________________________________________________________________________*/

    @Test
    public void testSelection_horizontal_bisection_NE() {
        setUp(new GridLocation(4, 1), new GridLocation(2,15));
        List<Cell<GridLocation>> expectedList = horizontal_bisection_NE();
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void testSelection_horizontal_bisection_SW() {
        setUp(new GridLocation(2,15), new GridLocation(4, 1));
        List<Cell<GridLocation>> expectedList = horizontal_bisection_NE();
        Collections.reverse(expectedList);
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void testSelection_horizontal_bisection_NW() {
        setUp(new GridLocation(4,15), new GridLocation(2, 1));
        List<Cell<GridLocation>> expectedList = horizontal_bisection_NW();
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void testSelection_horizontal_bisection_SE() {
        setUp(new GridLocation(2, 1), new GridLocation(4,15));
        List<Cell<GridLocation>> expectedList = horizontal_bisection_NW();
        Collections.reverse(expectedList);
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    private List<Cell<GridLocation>> horizontal_bisection_NW() {
        return Arrays.asList(
                track.getCellAt(new GridLocation(4, 15)),
                track.getCellAt(new GridLocation(4, 14)),
                track.getCellAt(new GridLocation(4, 13)),
                track.getCellAt(new GridLocation(4, 12)),

                track.getCellAt(new GridLocation(4, 11)),
                track.getCellAt(new GridLocation(3, 12)),

                track.getCellAt(new GridLocation(3, 11)),
                track.getCellAt(new GridLocation(3, 10)),
                track.getCellAt(new GridLocation(3, 9 )),
                track.getCellAt(new GridLocation(3, 8 )),
                track.getCellAt(new GridLocation(3, 7 )),
                track.getCellAt(new GridLocation(3, 6)),
                track.getCellAt(new GridLocation(3, 5)),

                track.getCellAt(new GridLocation(3, 4)),
                track.getCellAt(new GridLocation(2, 5)),

                track.getCellAt(new GridLocation(2, 4)),
                track.getCellAt(new GridLocation(2, 3)),
                track.getCellAt(new GridLocation(2, 2)),
                track.getCellAt(new GridLocation(2, 1)));
    }

    private List<Cell<GridLocation>> horizontal_bisection_NE() {
        return Arrays.asList(
                track.getCellAt(new GridLocation(4, 1)),
                track.getCellAt(new GridLocation(4, 2)),
                track.getCellAt(new GridLocation(4, 3)),
                track.getCellAt(new GridLocation(4, 4)),

                track.getCellAt(new GridLocation(4, 5)),
                track.getCellAt(new GridLocation(3, 4)),

                track.getCellAt(new GridLocation(3, 5 )),
                track.getCellAt(new GridLocation(3, 6 )),
                track.getCellAt(new GridLocation(3, 7 )),
                track.getCellAt(new GridLocation(3, 8 )),
                track.getCellAt(new GridLocation(3, 9 )),
                track.getCellAt(new GridLocation(3, 10)),
                track.getCellAt(new GridLocation(3, 11)),

                track.getCellAt(new GridLocation(3, 12)),
                track.getCellAt(new GridLocation(2, 11)),

                track.getCellAt(new GridLocation(2, 12)),
                track.getCellAt(new GridLocation(2, 13)),
                track.getCellAt(new GridLocation(2, 14)),
                track.getCellAt(new GridLocation(2, 15)));
    }

    /*_____________________________________________________________________________________________*/

    @Test
    public void shouldSelectInBisection_SE() {
        setUp(new GridLocation(3, 2), new GridLocation(5, 4));
        List<Cell<GridLocation>> expectedList = bisection_SE();
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();
        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void shouldSelectInBisection_NW() {
        setUp(new GridLocation(5, 4), new GridLocation(3, 2));
        List<Cell<GridLocation>> expectedList = bisection_SE();
        Collections.reverse(expectedList);
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();

        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void shouldSelectInBisection_SW() {
        setUp(new GridLocation(3, 4), new GridLocation(5, 2));
        List<Cell<GridLocation>> expectedList = bisection_SW();
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();
        Assert.assertEquals(expectedList, testingList);
    }

    @Test
    public void shouldSelectInBisection_NE() {
        setUp(new GridLocation(5, 2), new GridLocation(3, 4));
        List<Cell<GridLocation>> expectedList = bisection_SW();
        Collections.reverse(expectedList);
        List<Cell<GridLocation>> testingList = vector.getTraversedCells();
        Assert.assertEquals(expectedList, testingList);
    }

    private List<Cell<GridLocation>> bisection_SW() {
        return Arrays.asList(
                track.getCellAt(new GridLocation(3, 4)),

                /*skipping [4,2] and [3, 3] because [3, 3] OUTSIDE and [4, 2] is INSIDE*/

                track.getCellAt(new GridLocation(4, 3)),

                track.getCellAt(new GridLocation(4, 2)),
                track.getCellAt(new GridLocation(5, 3)),

                track.getCellAt(new GridLocation(5, 2)));
    }

    private List<Cell<GridLocation>> bisection_SE() {
        return Arrays.asList(
                track.getCellAt(new GridLocation(3, 2)),

                /*skipping [4,2] and [3, 3] because [3, 3] OUTSIDE and [4, 2] is INSIDE*/

                track.getCellAt(new GridLocation(4, 3)),

                track.getCellAt(new GridLocation(4, 4)),
                track.getCellAt(new GridLocation(5, 3)),

                track.getCellAt(new GridLocation(5, 4)));
    }

    private void setUp(GridLocation start, GridLocation end) {
        Cell<GridLocation> startCell = new GridCell<>(start, null, this.track);
        Cell<GridLocation> endCell = new GridCell<>(end, null, this.track);
        vector = new Vector(startCell, endCell, this.track);
    }
}