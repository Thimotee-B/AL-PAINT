package editeur.view;

import editeur.model.draw.DrawBuilder;
import editeur.model.draw.JavaFxDrawBuilder;
import editeur.model.geometry.base.Rectangle;
import javafx.application.Application;

public abstract class AbstractApplication extends Application {
    
    private static volatile AbstractApplication instance = null;
    private   GUIFactory        guiFactory;
    protected GenericToolBar    toolBar;
    protected GenericTopBar     topBar;
    protected GenericWhiteBoard whiteBoard;
    protected GenericButton     saveButton, loadButton, undoButton, redoButton, trashButton;
    protected GenericTopBar    trashBar;
    
    public AbstractApplication(String Gui) {
        synchronized(AbstractApplication.class) {
            if (instance == null) {
                instance   = this;
                this.guiFactory = ApplicationConfigurator.selectFactory(Gui);
                this.initFactoryElements();
            }
                
        }
    }
    
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
    
    public void start(DrawBuilder drawbuilder) {
        //Test à enlever
        //TODO: fonction add pour add les shapes dans mediator
        Rectangle r = new Rectangle(20,50,100,100);
        r.changeColor(255, 165, 0);
        r.draw(drawbuilder, toolBar.get());
        //r.changeColor(0, 0, 0);
        //r.draw(drawbuilder, toolBar.get());
    }
    
}
