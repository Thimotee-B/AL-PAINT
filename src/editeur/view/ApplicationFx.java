package editeur.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ApplicationFx extends AbstractApplication {
    
    private Scene scene;
    private BorderPane borderpane;
    
    public ApplicationFx() {
        super("JAVAFX");
    }
    
    
    @Override
    public void initFactoryElements() {
        super.initFactoryElements();
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

        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Al-Paint");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

   
    
    
    
}

