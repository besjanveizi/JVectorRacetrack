package it.unicam.cs.pa2021.jVectorRacetrack.controller;

/**
 * Represents the game engine for the Vector Racetrack game.
 */
public interface GameEngine {

    /**
     * Get all players involved with the race.
     */
    void loadPlayers();

    /**
     * Starts a new match of the Vector Racetrack Game.
     */
    void newMatch();

    /**
     * Returns game status.
     * @return game status.
     * @see GameStatus
     */
    GameStatus status();
}
