package editeur.view;

import editeur.model.draw.DrawBridge;
import editeur.view.GUIFactory.GenericViewElements.GenericButton;
import editeur.view.GUIFactory.GenericViewElements.GenericToolBar;
import editeur.view.GUIFactory.GenericViewElements.GenericTopBar;
import editeur.view.GUIFactory.GenericViewElements.GenericWhiteBoard;

/**
 * The interface IApplication represents the view elements and start,end,save,load behaviours.
 */
public interface IApplication {

    /**
     * Init factory elements.
     */
    void initFactoryElements();

    /**
     * Start.
     */
    void start();

    /**
     * End.
     */
    void end();

    /**
     * Save boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean save(String name);

    /**
     * Load boolean.
     *
     * @param name        the name
     * @param onlyToolbar the only toolbar
     * @return the boolean
     */
    boolean load(String name, boolean onlyToolbar);

    /**
     * Gets tool bar.
     *
     * @return the tool bar
     */
//
    GenericToolBar getToolBar();

    /**
     * Sets tool bar.
     *
     * @param toolBar the tool bar
     */
    void setToolBar(GenericToolBar toolBar);

    /**
     * Gets top bar.
     *
     * @return the top bar
     */
    GenericTopBar getTopBar();

    /**
     * Sets top bar.
     *
     * @param topBar the top bar
     */
    void setTopBar(GenericTopBar topBar);

    /**
     * Gets white board.
     *
     * @return the white board
     */
    GenericWhiteBoard getWhiteBoard();

    /**
     * Sets white board.
     *
     * @param whiteBoard the white board
     */
    void setWhiteBoard(GenericWhiteBoard whiteBoard);

    /**
     * Gets save button.
     *
     * @return the save button
     */
    GenericButton getSaveButton();

    /**
     * Sets save button.
     *
     * @param saveButton the save button
     */
    void setSaveButton(GenericButton saveButton);

    /**
     * Gets undo button.
     *
     * @return the undo button
     */
    GenericButton getUndoButton();

    /**
     * Sets undo button.
     *
     * @param undoButton the undo button
     */
    void setUndoButton(GenericButton undoButton);

    /**
     * Gets redo button.
     *
     * @return the redo button
     */
    GenericButton getRedoButton();

    /**
     * Sets redo button.
     *
     * @param redoButton the redo button
     */
    void setRedoButton(GenericButton redoButton);

    /**
     * Gets trash button.
     *
     * @return the trash button
     */
    GenericButton getTrashButton();

    /**
     * Sets trash button.
     *
     * @param trashButton the trash button
     */
    void setTrashButton(GenericButton trashButton);

    /**
     * Gets trash bar.
     *
     * @return the trash bar
     */
    GenericTopBar getTrashBar();

    /**
     * Sets trash bar.
     *
     * @param trashBar the trash bar
     */
    void setTrashBar(GenericTopBar trashBar);

    /**
     * Gets draw bridge.
     *
     * @return the draw bridge
     */
    DrawBridge getDrawBridge();

    /**
     * Sets draw bridge.
     *
     * @param drawbridge the drawbridge
     */
    void setDrawBridge(DrawBridge drawbridge);

    /**
     * Gets load button.
     *
     * @return the load button
     */
    GenericButton getLoadButton();

    /**
     * Sets load button.
     *
     * @param loadButton the load button
     */
    void setLoadButton(GenericButton loadButton);
}
