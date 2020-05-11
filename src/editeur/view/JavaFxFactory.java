package editeur.view;


import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;

import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;
import editeur.controller.Mediator;
import editeur.model.geometry.Composite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import javax.print.attribute.standard.Media;


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
        if (name.compareTo("trash") == 0)
            button.setStyle("-fx-background-color: transparent;");
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
        fileChooser.setTitle("Save your art!");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("paint auber", "*.auber")
        );
        fileChooser.setInitialFileName("paint-ksos");

        File savedFile = fileChooser.showSaveDialog(null);
        if (savedFile == null) {
            event.consume();
            return;
        }

        ObjectOutputStream ostream;
        try {
            ostream = new ObjectOutputStream(new FileOutputStream(savedFile.getAbsolutePath()));
            ostream.writeObject(tool.getShapeVector());
            ostream.writeChar('~');
            ostream.writeObject(wboard.getShapeVector());
            ostream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
//        System.out.println("ta grand mère" + savedFile.getName());

        event.consume();
    }

    private void handle_load(ActionEvent event) {
        Mediator.getInstance().clearView();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load your art!");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("paint auber", "*.auber")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile == null) {
            event.consume();
            return;
        }

        ObjectInputStream istream;
        try {
            istream = new ObjectInputStream(new FileInputStream(selectedFile.getAbsolutePath()));
            // on lit la toolbar
            try {
                tool.setShapeVector((Composite) istream.readObject());
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }

            // on console le ~
            istream.readChar();

            // on lit la whiteboard
            try {
                wboard.setShapeVector((Composite) istream.readObject()); // on lit la whiteboard
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //refresh
        Mediator.getInstance().Notify();

        event.consume();
    }
    
    private void handle_trash(ActionEvent event) {
        //Mediator.getInstance().clearView();
        event.consume();
    }
    
    private void handle_undo(ActionEvent event) {
        Mediator.getInstance().undo();
        //TODO: Penser � desactiver les boutons si action plus possible undo,redo
        event.consume();
    }
    
    private void handle_redo(ActionEvent event) {
        Mediator.getInstance().redo();
        //TODO: Penser � desactiver les boutons si action plus possible undo,redo
        event.consume();
    }
    
}
