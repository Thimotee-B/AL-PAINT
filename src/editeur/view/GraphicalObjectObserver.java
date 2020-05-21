package editeur.view;

/**
 * The interface GraphicalObjectObserver represents the Observer of the Pattern Observer.
 * All class who implements this interface became observers and can update when the observe notify them.
 * They first need to attach to an observe.
 */
public interface GraphicalObjectObserver {

    /**
     * Update the observer.
     */
    void update();

}
