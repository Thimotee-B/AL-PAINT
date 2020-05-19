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
 *
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

    @Override
    public void start(){
        toolBar.setDrawBridge(drawbridge);
        whiteBoard.setDrawBridge(drawbridge);

        this.mediator.Attach(toolBar);
        this.mediator.Attach(whiteBoard);
        this.mediator.start();
    }
    @Override
    public void end(){
        this.save(Mediator.DEFAULT_SAVE_NAME);
        System.out.println("AL-Paint is closing, thanks for your support");
        System.out.println("Your work in progress will be saved, don't worry");
        System.out.println("You can find in the repository of this project : autosave.auber");
    }

    @Override
    public abstract boolean save(String name);

    @Override
    public abstract boolean load(String name, boolean onlyToolbar);

    // Getters & Setters
    public GenericToolBar getToolBar() {
        return toolBar;
    }

    public void setToolBar(GenericToolBar toolBar) {
        this.toolBar = toolBar;
    }

    public GenericTopBar getTopBar() {
        return topBar;
    }

    public void setTopBar(GenericTopBar topBar) {
        this.topBar = topBar;
    }

    public GenericWhiteBoard getWhiteBoard() {
        return whiteBoard;
    }

    public void setWhiteBoard(GenericWhiteBoard whiteBoard) {
        this.whiteBoard = whiteBoard;
    }


    public GenericButton getSaveButton() {
        return saveButton;
    }

    /**
     * @param saveButton
     */
    public void setSaveButton(GenericButton saveButton) {
        this.saveButton = saveButton;
    }

    public GenericButton getUndoButton() {
        return undoButton;
    }

    public void setUndoButton(GenericButton undoButton) {
        this.undoButton = undoButton;
    }

    public GenericButton getRedoButton() {
        return redoButton;
    }

    public void setRedoButton(GenericButton redoButton) {
        this.redoButton = redoButton;
    }

    public GenericButton getTrashButton() {
        return trashButton;
    }

    public void setTrashButton(GenericButton trashButton) {
        this.trashButton = trashButton;
    }

    public GenericTopBar getTrashBar() {
        return trashBar;
    }

    public void setTrashBar(GenericTopBar trashBar) {
        this.trashBar = trashBar;
    }

    public DrawBridge getDrawBridge() {
        return drawbridge;
    }

    public void setDrawBridge(DrawBridge drawbridge) {
        this.drawbridge = drawbridge;
    }

    public GenericButton getLoadButton() {
        return loadButton;
    }

    public void setLoadButton(GenericButton loadButton) {
        this.loadButton = loadButton;
    }
    
    

    
    
}
