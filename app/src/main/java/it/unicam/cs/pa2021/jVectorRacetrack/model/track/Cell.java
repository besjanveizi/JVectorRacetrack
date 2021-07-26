package it.unicam.cs.pa2021.jVectorRacetrack.model.track;

/**
 * Represents a generic <code>Cell</code> in the <code>Racetrack</code>.
 * @param <L> Type of location of the <code>Cell</code>.
 */
public interface Cell<L> {

    /**
     * Returns the location of the <code>Cell</code>
     * @return the location of the <code>Cell</code>
     */
    L getLocation();

    /**
     * Returns the <code>Racetrack</code> the <code>Cell</code> is in.
     * @return the <code>Racetrack</code> that contains the <code>Cell</code>.
     */
    Racetrack<L> getTrack();

    /**
     * Returns the <code>Zone</code> where the <code>Cell</code> is in.
     * @return the <code>Zone</code> of the <code>Cell</code>
     */
    Zone getZone();
}
