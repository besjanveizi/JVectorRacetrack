package it.unicam.cs.pa2021.jVectorRacetrack.model.track;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class RacetrackTest {

    Racetrack<GridLocation> track;
    private final int width = 5;
    private final int height = 5;


    @Before
    public void setUp() {
        Zone[][] matrix = new Zone[width][height];
        for (int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                    matrix[r][c] = Zone.INSIDE;
            }
        }
        matrix[3][3] = Zone.OUTSIDE;
        matrix[4][3] = Zone.OUTSIDE;
        matrix[0][0] = Zone.START;
        matrix[4][4] = Zone.FINISH;
        matrix[0][2] = Zone.CHECKPOINT1;
        matrix[3][0] = Zone.CHECKPOINT2;
        matrix[1][4] = Zone.CHECKPOINT3;
        matrix[4][2] = Zone.CHECKPOINT4;
        track = new GridRacetrack(width, height, matrix);
    }

    @Test
    public void shouldThrowException_LocationNotValid() {
        IndexOutOfBoundsException exception = Assert.assertThrows(IndexOutOfBoundsException.class,
                () -> track.getCellAt(new GridLocation(width+1, height+1)));
        Assert.assertEquals("Location not valid", exception.getMessage());
    }

    @Test
    public void shouldBe_OUTSIDE() {
        Assert.assertEquals(Zone.OUTSIDE, track.getZoneAt(new GridLocation(3,3)));
    }

    @Test
    public void shouldBe_START() {
        Assert.assertEquals(Zone.START, track.getZoneAt(new GridLocation(0,0)));
    }

    @Test
    public void shouldBe_CHECKPOINT1() {
        Assert.assertEquals(Zone.CHECKPOINT1, track.getZoneAt(new GridLocation(0,2)));
    }

    @Test
    public void shouldBe_CHECKPOINT2() {
        Assert.assertEquals(Zone.CHECKPOINT2, track.getZoneAt(new GridLocation(3,0)));
    }

    @Test
    public void shouldBe_CHECKPOINT3() {
        Assert.assertEquals(Zone.CHECKPOINT3, track.getZoneAt(new GridLocation(1,4)));
    }

    @Test
    public void shouldBe_CHECKPOINT4() {
        Assert.assertEquals(Zone.CHECKPOINT4, track.getZoneAt(new GridLocation(4,2)));
    }

    @Test
    public void shouldBe_FINISH() {
        Assert.assertEquals(Zone.FINISH, track.getZoneAt(new GridLocation(4,4)));
    }

    @Test
    public void shouldBeValid() {
        Assert.assertTrue(track.isValid());
    }

    @Test
    public void shouldNotBeCircular() {
        Assert.assertFalse(track.isCircular());
    }

    @Test
    public void shouldHaveCorrectTargets() {
        Deque<Zone> targetStack = new LinkedList<>();
        targetStack.offerFirst(Zone.FINISH);
        targetStack.offerFirst(Zone.CHECKPOINT4);
        targetStack.offerFirst(Zone.CHECKPOINT3);
        targetStack.offerFirst(Zone.CHECKPOINT2);
        targetStack.offerFirst(Zone.CHECKPOINT1);
        Assert.assertEquals(targetStack, track.getTargetStack());
    }

    @Test
    public void shouldHave2CellsWith_OUTSIDE() {
        Set<Cell<GridLocation>> outsideCells = new HashSet<>(
                Set.of(track.getCellAt(new GridLocation(3, 3)),
                       track.getCellAt(new GridLocation(4, 3))));
        Assert.assertEquals(outsideCells, track.getCellsWith((z) -> z == Zone.OUTSIDE));
    }
}