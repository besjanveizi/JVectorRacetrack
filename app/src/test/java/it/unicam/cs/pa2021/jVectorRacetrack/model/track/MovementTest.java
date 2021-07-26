package it.unicam.cs.pa2021.jVectorRacetrack.model.track;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MovementTest {

    Cell<GridLocation> start;
    Cell<GridLocation> end;
    Movement<GridLocation> vector;
    private Racetrack<GridLocation> track;

    @Before
    public void setUp() {
        Zone[][] zones = new Zone[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                zones[i][j] = Zone.INSIDE;
            }
        }
        track = new GridRacetrack(20, 20, zones);
    }

    @Test
    public void shouldBe_SOUTH() {
        setUp(new GridLocation(3, 7), new GridLocation(7, 7));
        Assert.assertEquals(Direction.SOUTH, vector.getDirection());
    }

    @Test
    public void shouldBe_NORTH() {
        setUp(new GridLocation(7, 7), new GridLocation(3, 7));
        Assert.assertEquals(Direction.NORTH, vector.getDirection());
    }

    @Test
    public void shouldBe_SOUTH_WEST() {
        setUp(new GridLocation(2, 7), new GridLocation(5, 2));
        Assert.assertEquals(Direction.SOUTH_WEST, vector.getDirection());
    }

    @Test
    public void shouldBe_NORTH_WEST() {
        setUp(new GridLocation(6, 7), new GridLocation(3, 2));
        Assert.assertEquals(Direction.NORTH_WEST, vector.getDirection());
    }

    @Test
    public void shouldBe_SOUTH_EAST() {
        setUp(new GridLocation(3, 2), new GridLocation(6, 7));
        Assert.assertEquals(Direction.SOUTH_EAST, vector.getDirection());
    }

    @Test
    public void shouldBe_NORTH_EAST() {
        setUp(new GridLocation(5, 2), new GridLocation(2, 7));
        Assert.assertEquals(Direction.NORTH_EAST, vector.getDirection());
    }

    @Test
    public void shouldBe_WEST() {
        setUp(new GridLocation(6, 8), new GridLocation(6, 2));
        Assert.assertEquals(Direction.WEST, vector.getDirection());
    }

    @Test
    public void shouldBe_EAST() {
        setUp(new GridLocation(3, 2), new GridLocation(3, 8));
        Assert.assertEquals(Direction.EAST, vector.getDirection());
    }

    @Test
    public void shouldGetCorrectNextEndingCell() {
        setUp(new GridLocation(0, 1), new GridLocation(2, 8));
        Assert.assertEquals(new GridLocation(4, 15), vector.getNextEndingCell().getLocation());

    }

    protected void setUp(GridLocation start, GridLocation end) {
        this.start = track.getCellAt(start);
        this.end = track.getCellAt(end);
        vector = new Vector(this.start, this.end, track);
    }
}