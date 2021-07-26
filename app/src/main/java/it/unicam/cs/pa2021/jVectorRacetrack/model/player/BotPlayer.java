package it.unicam.cs.pa2021.jVectorRacetrack.model.player;

import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Cell;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.GridLocation;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Racetrack;

import java.util.Set;

/**
 * Represents a Bot player that follows the game rules to move,
 * but the choice of every move is random between all possible valid end positions.
 */
public class BotPlayer extends DefaultPlayer {

    private static int lastID;
    private final int id;
    /**
     * Creates a new <code>BotPlayer</code>.
     *
     * @param startPosition position the bot player starts the race.
     * @param track         <code>Racetrack</code> where the bot is playing.
     */
    public BotPlayer(Racetrack<GridLocation> track, Cell<GridLocation> startPosition) {
        super(startPosition, track);
        BotPlayer.lastID++;
        this.id = lastID;
    }

    @Override
    public String getName() {
        return "BOT#"+id;
    }

    @Override
    public Cell<GridLocation> choseNextPosition() throws IndexOutOfBoundsException{
        Set<Cell<GridLocation>> allPossibleNextPositions = getAllPossibleNextCells();
        if (allPossibleNextPositions.isEmpty()) {
            throw new IndexOutOfBoundsException("Out of the track");
        } else {
            return allPossibleNextPositions.stream().skip((int) (allPossibleNextPositions.size() * Math.random())).findFirst().get();
        }
    }

}
