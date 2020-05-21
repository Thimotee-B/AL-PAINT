package editeur.controller;

import editeur.view.GraphicalObjectObserver;

/**
 * The interface SubjectObserve.
 * Observe of the pattern observer, can notify and ask to update to every observers GraphicalObjectObserver attached.
 */
public interface SubjectObserve {

    /**
     * Attach the observer to the Observe for further updates.
     *
     * @param observer the observer
     */
    void Attach(GraphicalObjectObserver observer);

    /**
     * Detach the observer of observe list. Will not receive updates from now.
     *
     * @param observer the observer
     */
    void Detach(GraphicalObjectObserver observer);

    /**
     * Notify all the observers that they need to update.
     */
    void Notify();

}
