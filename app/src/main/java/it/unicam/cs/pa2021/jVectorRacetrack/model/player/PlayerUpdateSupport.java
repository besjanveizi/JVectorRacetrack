package it.unicam.cs.pa2021.jVectorRacetrack.model.player;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class PlayerUpdateSupport<L> {

    private final List<PlayerUpdateListener<L>> listeners;

    public PlayerUpdateSupport() {
        listeners = new LinkedList<>();
    }

    public synchronized void addListener(PlayerUpdateListener<L> listener) {
        if (listener == null) return;
        this.listeners.add(listener);
    }

    public synchronized void removeListener(PlayerUpdateListener<L> listener) {
        if (listener == null) return;
        this.listeners.add(listener);
    }

    public synchronized void firePlayerStatusChanged(PlayerStatus oldStatus, PlayerStatus newStatus) {
        listeners.forEach(l -> l.firePlayerStatusChanged(oldStatus, newStatus));
    }

    public synchronized void fireAllNextPossiblePositions(Set<L> locations) {
        listeners.forEach(l -> l.fireAllNextPossiblePositions(locations));
    }

    public synchronized void fireChosenNextPosition(L position) {
        listeners.forEach(l -> l.fireChosenNextPosition(position));
    }
}
