package editeur.model.geometry.memento;

/**
 * The interface Originator is part of MementoPattern.
 * Class who implements Originator can save their state and restore it later
 * For example, IShape implements Originator, so they can be saved.
 */
public interface Originator {

    /**
     * Save the memento of implemented class.
     *
     * @return the memento
     */
    Memento save();

    /**
     * Restore the memento for implemented class.
     *
     * @param memento the memento
     */
    void restore(Memento memento);
}
