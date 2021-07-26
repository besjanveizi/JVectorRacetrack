package it.unicam.cs.pa2021.jVectorRacetrack.model.player;

import it.unicam.cs.pa2021.jVectorRacetrack.model.track.*;

import java.util.Set;

/**
 * Represents a Bot player that follows the game rules to move,
 * but the choice of every move is random between all possible valid end positions.
 */
public class BotPlayer extends DefaultPlayer<GridLocation> {

    private static int lastID;
    private final int id;
    private final Racetrack<GridLocation> track;
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
        this.track = track;
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
            return allPossibleNextPositions.stream()
                    .skip((int) (allPossibleNextPositions.size() * Math.random()))
                    .findFirst().get();
        }
    }

    @Override
    public Movement<GridLocation> createMovement(Cell<GridLocation> currentPosition, Cell<GridLocation> chosenLocation)  {
        return new Vector(currentPosition, chosenLocation, track);
    }

}
