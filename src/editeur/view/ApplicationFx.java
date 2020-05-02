package editeur.view;

import editeur.model.draw.DrawBridge;
import editeur.model.draw.JavaFxDrawBridge;
import editeur.model.geometry.base.Rectangle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ApplicationFx extends AbstractApplication {
    
    private Scene scene;
    private BorderPane borderpane;
    public ApplicationFx() {
        super("JAVAFX");
        this.setDrawBridge(new JavaFxDrawBridge());
    }
    
    
    @Override
    public void initFactoryElements() {
        super.initFactoryElements();
    }

    public void start() {
        super.start();
    }
    
    @Override
    public void init() throws Exception {
        StackPane    toolbar  = (StackPane)   this.getToolBar().get();
        StackPane whiteboard  = (StackPane)   this.getWhiteBoard().get();
        ToolBar      topbar   = (ToolBar)     this.getTopBar().get();
        
        this.borderpane       = new BorderPane();
        this.scene            = new Scene(borderpane);
        borderpane.setLeft(toolbar);
        borderpane.setCenter(whiteboard);
        borderpane.setTop(topbar);
        borderpane.setBottom((Button)this.getTrashButton().get());
        //toolbar.setAlignment((Button)this.trashButton.get(), Pos.BOTTOM_CENTER);
        //toolbar.getChildren().add((Button)this.trashButton.get());
        topbar.getItems().addAll(new Separator(),
                (Button)this.getSaveButton().get(), (Button)this.getLoadButton().get() ,
                new Separator(),
                (Button)this.getUndoButton().get(),(Button) this.getRedoButton().get(),
                new Separator());
        this.start();
        
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Al-Paint");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

   
    
    
    
}

