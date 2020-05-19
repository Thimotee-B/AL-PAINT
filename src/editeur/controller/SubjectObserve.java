package editeur.controller;

import editeur.view.GraphicalObjectObserver;

public interface SubjectObserve {

    /**
     *
     * @param observer
     */
    void Attach(GraphicalObjectObserver observer);

    /**
     *
     * @param observer
     */
    void Detach(GraphicalObjectObserver observer);

    void Notify();

}
