package editeur.view;

import editeur.controller.IMediator;
import editeur.controller.Mediator;
import editeur.model.draw.DrawBridge;
import editeur.view.GUIFactory.*;
import editeur.view.GUIFactory.GenericViewElements.GenericButton;
import editeur.view.GUIFactory.GenericViewElements.GenericToolBar;
import editeur.view.GUIFactory.GenericViewElements.GenericTopBar;
import editeur.view.GUIFactory.GenericViewElements.GenericWhiteBoard;
import javafx.application.Application;

/**
 * The type AbstractApplication regroups all the components we need in the view as toolbar, whiteboard, buttons ...
 */
public abstract class AbstractApplication extends Application implements IApplication {
    private static volatile AbstractApplication instance = null;
    private GUIFactory guiFactory;
    private GenericToolBar toolBar;
    private GenericTopBar topBar;
    private GenericWhiteBoard whiteBoard;
    private GenericButton saveButton, loadButton, undoButton, redoButton, trashButton;
    private   GenericTopBar     trashBar;
    private   DrawBridge        drawbridge;
    protected IMediator         mediator;

    /**
     * Instantiates a new Abstract application.
     *
     * @param Gui the gui
     */
    public AbstractApplication(String Gui) {
        synchronized(AbstractApplication.class) {
            if (instance == null) {
                instance   = this;
                this.guiFactory = ApplicationConfigurator.selectFactory(Gui);
                this.initFactoryElements();
                this.mediator   = new Mediator(instance);
                this.drawbridge = ApplicationConfigurator.selectDrawBridgeImpl(Gui);
            }
                
        }
    }

    /**
     * Init the factory elements with the selected implementation of guifactory.
     */
    @Override
    public void initFactoryElements() {
        this.toolBar     = guiFactory.createToolBar();
        this.topBar      = guiFactory.createTopBar();
        this.whiteBoard  = guiFactory.createWhiteBoard();
        this.saveButton  = guiFactory.createButton("save");
        this.loadButton  = guiFactory.createButton("load");
        this.undoButton  = guiFactory.createButton("undo");
        this.redoButton  = guiFactory.createButton("redo");
        this.trashButton = guiFactory.createButton("trash");
    }

    /**
     * Start the application.
     */
    @Override
    public void start(){
        toolBar.setDrawBridge(drawbridge);
        whiteBoard.setDrawBridge(drawbridge);

        this.mediator.Attach(toolBar);
        this.mediator.Attach(whiteBoard);
        this.mediator.start();
    }

    /**
     * End Behaviour with save.
     */
    @Override
    public void end(){
        this.save(Mediator.DEFAULT_SAVE_NAME);
        System.out.println("AL-Paint is closing, thanks for your support");
        System.out.println("Your work in progress will be saved, don't worry");
        System.out.println("You can find in the repository of this project : autosave.auber");
    }

    /**
     * Save boolean.
     *
     * @param name the name
     * @return the boolean
     */
    @Override
    public abstract boolean save(String name);

    /**
     * Load boolean.
     *
     * @param name        the name
     * @param onlyToolbar the only toolbar
     * @return the boolean
     */
    @Override
    public abstract boolean load(String name, boolean onlyToolbar);

    /**
     * Gets tool bar.
     *
     * @return the tool bar
     */
// Getters & Setters
    public GenericToolBar getToolBar() {
        return toolBar;
    }

    /**
     * Sets tool bar.
     *
     * @param toolBar the tool bar
     */
    public void setToolBar(GenericToolBar toolBar) {
        this.toolBar = toolBar;
    }

    /**
     * Gets top bar.
     *
     * @return the top bar
     */
    public GenericTopBar getTopBar() {
        return topBar;
    }

    /**
     * Sets top bar.
     *
     * @param topBar the top bar
     */
    public void setTopBar(GenericTopBar topBar) {
        this.topBar = topBar;
    }

    /**
     * Gets white board.
     *
     * @return the white board
     */
    public GenericWhiteBoard getWhiteBoard() {
        return whiteBoard;
    }

    /**
     * Sets white board.
     *
     * @param whiteBoard the white board
     */
    public void setWhiteBoard(GenericWhiteBoard whiteBoard) {
        this.whiteBoard = whiteBoard;
    }


    /**
     * Gets save button.
     *
     * @return the save button
     */
    public GenericButton getSaveButton() {
        return saveButton;
    }

    /**
     * Sets save button.
     *
     * @param saveButton the save button
     */
    public void setSaveButton(GenericButton saveButton) {
        this.saveButton = saveButton;
    }

    /**
     * Gets undo button.
     *
     * @return the undo button
     */
    public GenericButton getUndoButton() {
        return undoButton;
    }

    /**
     * Sets undo button.
     *
     * @param undoButton the undo button
     */
    public void setUndoButton(GenericButton undoButton) {
        this.undoButton = undoButton;
    }

    /**
     * Gets redo button.
     *
     * @return the redo button
     */
    public GenericButton getRedoButton() {
        return redoButton;
    }

    /**
     * Sets redo button.
     *
     * @param redoButton the redo button
     */
    public void setRedoButton(GenericButton redoButton) {
        this.redoButton = redoButton;
    }

    /**
     * Gets trash button.
     *
     * @return the trash button
     */
    public GenericButton getTrashButton() {
        return trashButton;
    }

    /**
     * Sets trash button.
     *
     * @param trashButton the trash button
     */
    public void setTrashButton(GenericButton trashButton) {
        this.trashButton = trashButton;
    }

    /**
     * Gets trash bar.
     *
     * @return the trash bar
     */
    public GenericTopBar getTrashBar() {
        return trashBar;
    }

    /**
     * Sets trash bar.
     *
     * @param trashBar the trash bar
     */
    public void setTrashBar(GenericTopBar trashBar) {
        this.trashBar = trashBar;
    }

    /**
     * Gets draw bridge.
     *
     * @return the draw bridge
     */
    public DrawBridge getDrawBridge() {
        return drawbridge;
    }

    /**
     * Sets draw bridge.
     *
     * @param drawbridge the drawbridge
     */
    public void setDrawBridge(DrawBridge drawbridge) {
        this.drawbridge = drawbridge;
    }

    /**
     * Gets load button.
     *
     * @return the load button
     */
    public GenericButton getLoadButton() {
        return loadButton;
    }

    /**
     * Sets load button.
     *
     * @param loadButton the load button
     */
    public void setLoadButton(GenericButton loadButton) {
        this.loadButton = loadButton;
    }
    
    

    
    
}
