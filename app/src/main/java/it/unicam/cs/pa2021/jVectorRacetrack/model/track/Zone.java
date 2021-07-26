package it.unicam.cs.pa2021.jVectorRacetrack.model.track;

/**
 * Represents the possible zones in the track
 */
public enum Zone {
    /**
     * Zone inside the track.
     */
    INSIDE,
    /**
     * Zone outside the track.
     */
    OUTSIDE,
    /**
     * Zone where is possible to chose as starting point.
     */
    START,
    /**
     * Zone that marks a possible finish point.
     */
    FINISH,
    /**
     * Zone that marks a checkpoint that has to be reached first.
     */
    CHECKPOINT1,
    /**
     * Zone that marks a checkpoint that has to be reached second.
     */
    CHECKPOINT2,
    /**
     * Zone that marks a checkpoint that has to be reached third.
     */
    CHECKPOINT3,
    /**
     * Zone that marks a checkpoint that has to be reached last before {@link Zone#FINISH}.
     */
    CHECKPOINT4
}
