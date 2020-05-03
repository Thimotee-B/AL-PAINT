package editeur.controller;

import editeur.view.GraphicalObjectObserver;

public interface SubjectObserve {

    void Attach(GraphicalObjectObserver observer);

    void Detach(GraphicalObjectObserver observer);

    void Notify();

}
