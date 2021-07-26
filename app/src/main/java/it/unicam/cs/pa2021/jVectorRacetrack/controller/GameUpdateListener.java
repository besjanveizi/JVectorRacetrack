package it.unicam.cs.pa2021.jVectorRacetrack.controller;

public interface GameUpdateListener {

    void fireGameStatusChanged(GameStatus oldStatus, GameStatus newStatus);

}
