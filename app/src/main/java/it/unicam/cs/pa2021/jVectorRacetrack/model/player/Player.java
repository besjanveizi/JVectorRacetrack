package it.unicam.cs.pa2021.jVectorRacetrack.model.player;

import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Cell;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Movement;

import java.util.List;

/**
 * Represents a player for the Vector Racetrack Game.
 * @param <L> type location used to perform player's movement.
 */
public interface Player<L> {

    /**
     * Returns the next <code>Cell</code> the <code>Player</code> wants to move to.
     * @return the next <code>Cell</code> the <code>Player</code> wants to move to.
     */
    Cell<L> choseNextPosition();

    /**
     * Returns the total number of crashes.
     * @return the number of crashes.
     */
    int getCrashes();

    /**
     * Returns <code>Player</code> current <code>Cell</code> position.
     * @return the current <code>Cell</code> position
     */
    Cell<L> getCurrentPosition();

    /**
     * Returns all the player's so far executed movements.
     * @return a ordered list of the executed movements.
     */
    List<Movement<L>> getExecutedMovements();

    /**
     * Returns total moves the player made.
     * @return total moves made.
     */
    int getMoves();

    /**
     * Returns the <code>Player</code>'s name.
     * @return the player's name.
     */
    String getName();

    /**
     * Returns <code>Player</code> status.
     * @return <code>Player</code> status
     * @see PlayerStatus
     */
    PlayerStatus getStatus();

    /**
     * Checks if the <code>Player</code> has finished the race.
     * @return true if the <code>Player</code> has finished the race, otherwise false.
     */
    boolean hasFinished();

    /**
     * Checks if the <code>Player</code> has crashed.
     * @return true if the <code>Player</code> has crashed, otherwise false.
     */
    boolean isCrashed();

    /**
     * Checks if the <code>Cell</code> the player is attempting to move to next is legal according to the game rules.
     * @param chosenCell <code>Cell</code> the player wants to move to.
     * @return true if the move is legal, otherwise false.
     */
    boolean isLegal(Cell<L> chosenCell);

    /**
     * The player moves to the chosen location
     * @param loc next location the <code>Player</code> wants to move to.
     */
    void move(Cell<L> loc);


    void addPlayerUpdateListener(PlayerUpdateListener<L> listener);
    void removePlayerUpdateListener(PlayerUpdateListener<L> listener);
}
