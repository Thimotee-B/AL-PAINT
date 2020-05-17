package editeur.view;

import editeur.model.draw.DrawBridge;
import editeur.view.GUIFactory.GenericViewElements.GenericButton;
import editeur.view.GUIFactory.GenericViewElements.GenericToolBar;
import editeur.view.GUIFactory.GenericViewElements.GenericTopBar;
import editeur.view.GUIFactory.GenericViewElements.GenericWhiteBoard;

public interface IApplication {

    void initFactoryElements();

    void start();

    void end();

    boolean save(String name);

    boolean load(String name, boolean onlyToolbar);

    //
    GenericToolBar getToolBar();

    void setToolBar(GenericToolBar toolBar);

    GenericTopBar getTopBar();

    void setTopBar(GenericTopBar topBar);

    GenericWhiteBoard getWhiteBoard();

    void setWhiteBoard(GenericWhiteBoard whiteBoard);

    GenericButton getSaveButton();

    void setSaveButton(GenericButton saveButton);

    GenericButton getUndoButton();

    void setUndoButton(GenericButton undoButton);

    GenericButton getRedoButton();

    void setRedoButton(GenericButton redoButton);

    GenericButton getTrashButton();

    void setTrashButton(GenericButton trashButton);

    GenericTopBar getTrashBar();

    void setTrashBar(GenericTopBar trashBar);

    DrawBridge getDrawBridge();

    void setDrawBridge(DrawBridge drawbridge);

    GenericButton getLoadButton();

    void setLoadButton(GenericButton loadButton);
}
