package editeur.view.GUIFactory;

import editeur.view.GUIFactory.GenericViewElements.GenericButton;
import editeur.view.GUIFactory.GenericViewElements.GenericToolBar;
import editeur.view.GUIFactory.GenericViewElements.GenericTopBar;
import editeur.view.GUIFactory.GenericViewElements.GenericWhiteBoard;

public interface GUIFactory {
    
    GenericWhiteBoard createWhiteBoard();
    GenericToolBar createToolBar();
    GenericTopBar createTopBar();
    GenericButton createButton(String name);
    
}
