package it.unicam.cs.pa2021.jVectorRacetrack.controller;

import java.util.LinkedList;
import java.util.List;

public final class GameUpdateSupport {

    private final List<GameUpdateListener> listeners;

    public GameUpdateSupport() {
        this.listeners = new LinkedList<>();
    }

    public synchronized void addListener(GameUpdateListener listener) {
        if (listener == null) return;
        this.listeners.add(listener);
    }

    public synchronized void removeListener(GameUpdateListener listener) {
        if (listener == null) return;
        this.listeners.remove(listener);
    }

    public synchronized void fireGameStatusChanged(GameStatus oldStatus, GameStatus newStatus) {
        listeners.forEach(l -> l.fireGameStatusChanged(oldStatus, newStatus));
    }
}
