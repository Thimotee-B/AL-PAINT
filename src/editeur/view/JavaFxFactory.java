package editeur.view;


import java.io.File;
import java.util.HashMap;

import editeur.controller.Mediator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;



public class JavaFxFactory implements GUIFactory {
    private StackPane whiteBoardJavaFx;
    private StackPane toolbarJavaFx;
    private ToolBar topbarJavaFx;
    
    private GenericTopBar  top;
    private GenericToolBar tool;
    private GenericWhiteBoard wboard;
    private HashMap<String,Button> mapButton = new HashMap();
    
    @Override
    public GenericWhiteBoard createWhiteBoard() {
        whiteBoardJavaFx = new StackPane();
        wboard = new GenericWhiteBoard(whiteBoardJavaFx);
        setWhiteBoard();
        return wboard;
    }

    @Override
    public GenericToolBar createToolBar() {
        toolbarJavaFx = new StackPane();
        tool = new GenericToolBar(toolbarJavaFx);
        setToolBar();
        return tool;
    }
    

    @Override
    public GenericTopBar createTopBar() {
        topbarJavaFx = new ToolBar();
        top = new GenericTopBar(topbarJavaFx);
        return top;
    }
    
    private void setToolBar() {
        toolbarJavaFx.setStyle("-fx-border-color: black;");
        toolbarJavaFx.setMaxWidth(tool.getWidth());
        toolbarJavaFx.setMinWidth(tool.getWidth());
        toolbarJavaFx.setAlignment(Pos.TOP_LEFT);
    }
       
    private void setWhiteBoard() {
        whiteBoardJavaFx.setStyle("-fx-border-color: black;");
        whiteBoardJavaFx.setPrefSize(wboard.getWidth(), wboard.getHeight());
        whiteBoardJavaFx.resize(wboard.getWidth(), wboard.getHeight());
        whiteBoardJavaFx.setAlignment(Pos.TOP_LEFT);
    }

    @Override
    public GenericButton createButton(String name) {
        Button button = new Button(name);
        ImageView image = new ImageView(getClass().getResource("/"+ name + ".png").toString());
        image.setFitWidth(20);
        image.setPreserveRatio(true);
        button.setGraphic(image);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (name.compareTo("save")==0)
                    handle_save(event);
                if (name.compareTo("load")==0)
                    handle_load(event);
                if (name.compareTo("undo")==0)
                    handle_undo(event);
                if (name.compareTo("redo")==0)
                    handle_redo(event);
                if (name.compareTo("trash")==0)
                    handle_trash(event);
            }
        });
        mapButton.put(name, button);
        return new GenericButton(button);
    }
    
    
    private void handle_save(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Please choose your saved file :");
        fileChooser.setInitialFileName("default.ser");
        File savedFile = fileChooser.showSaveDialog(null);
        if (savedFile != null) {
            //UNTRUC.save(savedFile.getAbsolutePath());
        }
        event.consume();
    }
    private void handle_load(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Please choose a file to load :");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            //UNTRUC.load(selectedFile.getAbsolutePath());
        }
        event.consume();
    }
    
    private void handle_trash(ActionEvent event) {
        //MediatorJavaFx.getInstance().erase();
        //TODO: Penser à desactiver les boutons si action plus possible undo,redo
        event.consume();
    }
    
    private void handle_undo(ActionEvent event) {
        Mediator.getInstance().undo();
        //TODO: Penser à desactiver les boutons si action plus possible undo,redo
        event.consume();
    }
    
    private void handle_redo(ActionEvent event) {
        Mediator.getInstance().redo();
        //TODO: Penser à desactiver les boutons si action plus possible undo,redo
        event.consume();
    }
    
}
