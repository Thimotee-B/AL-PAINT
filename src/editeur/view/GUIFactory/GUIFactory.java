package editeur.view.GUIFactory;

import editeur.view.GUIFactory.GenericViewElements.GenericButton;
import editeur.view.GUIFactory.GenericViewElements.GenericToolBar;
import editeur.view.GUIFactory.GenericViewElements.GenericTopBar;
import editeur.view.GUIFactory.GenericViewElements.GenericWhiteBoard;

/**
 * The interface GUIFactory regroups all the creation method we need for the GUI objects.
 */
public interface GUIFactory {

    /**
     * Create a GenericWhiteBoard.
     *
     * @return the generic white board
     */
    GenericWhiteBoard createWhiteBoard();

    /**
     * Create A GenericToolBar.
     *
     * @return the generic tool bar
     */
    GenericToolBar createToolBar();

    /**
     * Create A GenericTopBar.
     *
     * @return the generic top bar
     */
    GenericTopBar createTopBar();

    /**
     * Create A GenericButton.
     *
     * @param name the name
     * @return the generic button
     */
    GenericButton createButton(String name);
    
}
