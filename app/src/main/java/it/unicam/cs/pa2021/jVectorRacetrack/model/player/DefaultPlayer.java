package it.unicam.cs.pa2021.jVectorRacetrack.model.player;

import it.unicam.cs.pa2021.jVectorRacetrack.model.rules.DefaultRule;
import it.unicam.cs.pa2021.jVectorRacetrack.model.rules.Rule;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.*;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Vector;

import java.util.*;

/**
 * Represents a default <code>Player</code> in the game.
 */
public abstract class DefaultPlayer implements Player<GridLocation>{

    private final Racetrack<GridLocation> track;
    private final List<Movement<GridLocation>> executedMovements;
    private Cell<GridLocation> currentPosition;
    private final Rule<GridLocation> defaultRule;
    private final Deque<Zone> targetStack;
    private PlayerStatus status;
    private int nCrashes;
    private int moves;

    /**
     * Creates a new <code>DefaultPlayer</code>.
     * @param startPosition position the player starts the race.
     * @param track <code>Racetrack</code> where the <code>Player</code> is playing.
     */
    public DefaultPlayer(Cell<GridLocation> startPosition, Racetrack<GridLocation> track) {
        this.track = track;
        targetStack = track.getTargetStack();
        this.executedMovements = new ArrayList<>();
        this.currentPosition = startPosition;
        defaultRule = new DefaultRule<>();

        status = PlayerStatus.PLAYING;
        nCrashes = 0;
        moves = 0;
    }

    public abstract String getName();

    public abstract Cell<GridLocation> choseNextPosition() throws NoSuchElementException;

    @Override
    public final List<Movement<GridLocation>> getExecutedMovements() {
        return executedMovements;
    }

    /**
     * Returns all the possible next cells that the <code>Player</code> can chose to move to in a turn.
     * @return a set of all the possible next cells to move to.
     */
    protected final Set<Cell<GridLocation>> getAllPossibleNextCells() {
        if (status == PlayerStatus.CRASHED || executedMovements.isEmpty()) {
            return track.getNeighbourCells(currentPosition.getLocation());
        }
        else {
            Set<Cell<GridLocation>> allPossibleNextCells;
            try {
                Cell<GridLocation> next = getLastMovement().getNextEndingCell();
                allPossibleNextCells = getLastMovement().apply(defaultRule);
                if (next != null) allPossibleNextCells.add(next);
            } catch (IndexOutOfBoundsException e) {
                setCrashed();
                moves++;
                return Collections.emptySet();
            }
            return allPossibleNextCells;
        }
    }

    @Override
    public final void move(Cell<GridLocation> chosenLocation) {
        if (status == PlayerStatus.CRASHED) return;
        Movement<GridLocation> movement = new Vector(currentPosition, chosenLocation, track);
        checkTraversedCells(movement.getTraversedCells());
        if (isCrashed()) {
            moves++;
            return;
        }
        doMove(chosenLocation, movement);
        moves++;
    }

    private void checkTraversedCells(List<Cell<GridLocation>> traversedCells) {
        checkIfCrashed(traversedCells);
        if (!isCrashed()) {
            checkIfNextTargetReached(traversedCells);
            checkIfFinished();
        }
    }

    @Override
    public final boolean isLegal(Cell<GridLocation> chosenCell) {
        if (!getAllPossibleNextCells().contains(chosenCell)) return false;
        else if (status == PlayerStatus.CRASHED) status = PlayerStatus.PLAYING;
        return true;
    }

    private void doMove(Cell<GridLocation> newLoc, Movement<GridLocation> movement) {
        executedMovements.add(movement);
        this.currentPosition = newLoc;
    }

    @Override
    public final PlayerStatus getStatus() {
        return status;
    }

    @Override
    public final Cell<GridLocation> getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public final int getCrashes() {
        return nCrashes;
    }

    @Override
    public final boolean hasFinished() {
        return status == PlayerStatus.FINISHED;
    }

    @Override
    public final boolean isCrashed() {
        return status == PlayerStatus.CRASHED;
    }

    @Override
    public final int getMoves() {
        return moves;
    }

    /**
     * Returns the current target <code>Zone</code> the player needs to reach.
     * @return current target <code>Zone</code>.
     */
    protected final Zone getNextTarget() {
        return this.targetStack.getFirst();
    }

    private Movement<GridLocation> getLastMovement() {
        return executedMovements.get(executedMovements.size() - 1);
    }

    private void checkIfNextTargetReached(List<Cell<GridLocation>> traversedCells) {
        while (!targetStack.isEmpty()) {
            if (traversedCells.stream().anyMatch((c) -> c.getZone() == getNextTarget())) {
                this.targetStack.pop();
            }
            else break;
        }
    }

    private void checkIfCrashed(List<Cell<GridLocation>> traversedCells) {
        if (traversedCells.stream().anyMatch(( c -> c.getZone() == Zone.OUTSIDE))){
            setCrashed();
        }
    }

    private void setCrashed() {
        status = PlayerStatus.CRASHED;
        nCrashes++;
    }

    private void checkIfFinished() {
        if (targetStack.size() == 0) {
            status = PlayerStatus.FINISHED;
        }
    }

}
