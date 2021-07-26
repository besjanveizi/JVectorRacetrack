package it.unicam.cs.pa2021.jVectorRacetrack.model.player;

import it.unicam.cs.pa2021.jVectorRacetrack.model.rules.DefaultRule;
import it.unicam.cs.pa2021.jVectorRacetrack.model.rules.Rule;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.*;

import java.util.*;

/**
 * Represents a default <code>Player</code> in the game.
 */
public abstract class DefaultPlayer<L> implements Player<L>{

    private final Racetrack<L> track;
    private final List<Movement<L>> executedMovements;
    private Cell<L> currentPosition;
    private final Rule<L> defaultRule;
    private final Deque<Zone> targetStack;
    private PlayerStatus status;
    private int nCrashes;
    private int moves;

    private final PlayerUpdateSupport<L> propertyChangeSupport;

    /**
     * Creates a new <code>DefaultPlayer</code>.
     * @param startPosition position the player starts the race.
     * @param track <code>Racetrack</code> where the <code>Player</code> is playing.
     */
    public DefaultPlayer(Cell<L> startPosition, Racetrack<L> track) {
        this.track = track;
        targetStack = track.getTargetStack();
        this.executedMovements = new ArrayList<>();
        this.currentPosition = startPosition;
        defaultRule = new DefaultRule<>();

        status = PlayerStatus.PLAYING;
        nCrashes = 0;
        moves = 0;
        propertyChangeSupport = new PlayerUpdateSupport<>();
    }

    public abstract String getName();

    public abstract Cell<L> choseNextPosition() throws NoSuchElementException;

    @Override
    public final List<Movement<L>> getExecutedMovements() {
        return executedMovements;
    }

    /**
     * Returns all the possible next cells that the <code>Player</code> can chose to move to in a turn.
     * @return a set of all the possible next cells to move to.
     */
    protected final Set<Cell<L>> getAllPossibleNextCells() {
        Set<Cell<L>> allPossibleNextCells = Collections.emptySet();
        if (status == PlayerStatus.CRASHED || executedMovements.isEmpty()) {
            allPossibleNextCells = track.getNeighbourCells(currentPosition.getLocation());
        }
        else {
            try {
                Cell<L> next = getLastMovement().getNextEndingCell();
                allPossibleNextCells = getLastMovement().apply(defaultRule);
                if (next != null) allPossibleNextCells.add(next);
            } catch (IndexOutOfBoundsException e) {
                setCrashed();
                moves++;
            }
        }
        return allPossibleNextCells;
    }

    @Override
    public final void move(Cell<L> chosenLocation) {
        if (status == PlayerStatus.CRASHED) return;
        Movement<L> movement = createMovement(currentPosition, chosenLocation);
        checkTraversedCells(movement.getTraversedCells());
        if (isCrashed()) {
            moves++;
            return;
        }
        doMove(chosenLocation, movement);
        moves++;
    }

    public abstract Movement<L> createMovement(Cell<L> currentPosition, Cell<L> chosenLocation);

    private void checkTraversedCells(List<Cell<L>> traversedCells) {
        checkIfCrashed(traversedCells);
        if (!isCrashed()) {
            checkIfNextTargetReached(traversedCells);
            checkIfFinished();
        }
    }

    @Override
    public final boolean isLegal(Cell<L> chosenCell) {
        if (!getAllPossibleNextCells().contains(chosenCell)) return false;
        else if (status == PlayerStatus.CRASHED) {
            PlayerStatus oldStatus = status;
            status = PlayerStatus.PLAYING;
            this.propertyChangeSupport.firePlayerStatusChanged(oldStatus, status);
        }
        return true;
    }

    private void doMove(Cell<L> newLoc, Movement<L> movement) {
        executedMovements.add(movement);
        this.currentPosition = newLoc;
        // firePlayerMoved
    }

    @Override
    public final PlayerStatus getStatus() {
        return status;
    }

    @Override
    public final Cell<L> getCurrentPosition() {
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

    private Movement<L> getLastMovement() {
        return executedMovements.get(executedMovements.size() - 1);
    }

    private void checkIfNextTargetReached(List<Cell<L>> traversedCells) {
        while (!targetStack.isEmpty()) {
            if (traversedCells.stream().anyMatch((c) -> c.getZone() == getNextTarget())) {
                this.targetStack.pop();
                // firePlayerTargetStackUpdate
            }
            else break;
        }
    }

    private void checkIfCrashed(List<Cell<L>> traversedCells) {
        if (traversedCells.stream().anyMatch(( c -> c.getZone() == Zone.OUTSIDE))){
            PlayerStatus oldStatus = status;
            setCrashed();
            this.propertyChangeSupport.firePlayerStatusChanged(oldStatus, status);
        }
    }

    private void setCrashed() {
        status = PlayerStatus.CRASHED;
        nCrashes++;
    }

    private void checkIfFinished() {
        if (targetStack.size() == 0) {
            PlayerStatus oldStatus = status;
            status = PlayerStatus.FINISHED;
            this.propertyChangeSupport.firePlayerStatusChanged(oldStatus, status);
        }
    }

    @Override
    public synchronized void addPlayerUpdateListener(PlayerUpdateListener<L> listener) {
        this.propertyChangeSupport.addListener(listener);
    }

    @Override
    public synchronized void removePlayerUpdateListener(PlayerUpdateListener<L> listener) {
        this.propertyChangeSupport.removeListener(listener);
    }
}
