package it.unicam.cs.pa2021.jVectorRacetrack.model.player;

import java.util.Set;

public interface PlayerUpdateListener<L> {

    void firePlayerStatusChanged(PlayerStatus oldStatus, PlayerStatus newStatus);

    void fireAllNextPossiblePositions(Set<L> positions);

    void fireChosenNextPosition(L position);

}
