package editeur.view;

import editeur.model.draw.DrawBuilder;
import editeur.model.draw.JavaFxDrawBuilder;
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
    private DrawBuilder drawbuilder = new JavaFxDrawBuilder();
    public ApplicationFx() {
        super("JAVAFX");
    }
    
    
    @Override
    public void initFactoryElements() {
        super.initFactoryElements();
    }

    public void start(DrawBuilder builder) {
        super.start(builder);
    }
    
    @Override
    public void init() throws Exception {
        StackPane    toolbar  = (StackPane)   this.toolBar.get();
        StackPane whiteboard  = (StackPane)   this.whiteBoard.get();
        ToolBar      topbar   = (ToolBar)     this.topBar.get();
        
        this.borderpane       = new BorderPane();
        this.scene            = new Scene(borderpane);
        borderpane.setLeft(toolbar);
        borderpane.setCenter(whiteboard);
        borderpane.setTop(topbar);
        borderpane.setBottom((Button)this.trashButton.get());
        //toolbar.setAlignment((Button)this.trashButton.get(), Pos.BOTTOM_CENTER);
        //toolbar.getChildren().add((Button)this.trashButton.get());
        topbar.getItems().addAll(new Separator(),
                (Button)this.saveButton.get(), (Button)this.loadButton.get() ,
                new Separator(),
                (Button)this.undoButton.get(),(Button) this.redoButton.get(),
                new Separator());
        this.start(drawbuilder);
        
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Al-Paint");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

   
    
    
    
}

