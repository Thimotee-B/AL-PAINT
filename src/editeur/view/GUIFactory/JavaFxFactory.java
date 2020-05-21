package editeur.view.GUIFactory;

import java.util.HashMap;

import editeur.controller.Mediator;

import editeur.view.GUIFactory.GenericViewElements.GenericButton;
import editeur.view.GUIFactory.GenericViewElements.GenericToolBar;
import editeur.view.GUIFactory.GenericViewElements.GenericTopBar;
import editeur.view.GUIFactory.GenericViewElements.GenericWhiteBoard;
import javafx.event.ActionEvent;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


/**
 * Implements the GuiFactory methods with javafx Style.
 */
public class JavaFxFactory implements GUIFactory {
    private StackPane whiteBoardJavaFx;
    private StackPane toolbarJavaFx;
    private ToolBar topbarJavaFx;
    private GenericTopBar top;
    private GenericToolBar tool;
    private GenericWhiteBoard wboard;
    private final HashMap<String,Button> mapButton = new HashMap();

    /**
     * Create GenericWhiteBoard who contains javafx stackPane.
     *
     * @return the generic white board
     */
    @Override
    public GenericWhiteBoard createWhiteBoard() {
        whiteBoardJavaFx = new StackPane();
        wboard = new GenericWhiteBoard(whiteBoardJavaFx);
        setWhiteBoard();
        return wboard;
    }

    /**
     * Create GenericToolbar who contains javafx StackPane.
     *
     * @return the generic tool bar
     */
    @Override
    public GenericToolBar createToolBar() {
        toolbarJavaFx = new StackPane();
        tool = new GenericToolBar(toolbarJavaFx);
        setToolBar();
        return tool;
    }


    /**
     * Create GenericTopBar who contains javafx Toolbar.
     *
     * @return the generic top bar
     */
    @Override
    public GenericTopBar createTopBar() {
        topbarJavaFx = new ToolBar();
        top = new GenericTopBar(topbarJavaFx);
        return top;
    }

    /**
     * Sets tool bar.
     */
    private void setToolBar() {
        toolbarJavaFx.setStyle("-fx-border-color: black;");
        toolbarJavaFx.setMaxWidth(tool.getWidth());
        toolbarJavaFx.setMinWidth(tool.getWidth());
        toolbarJavaFx.setAlignment(Pos.TOP_LEFT);

    }

    /**
     * Sets white board.
     */
    private void setWhiteBoard() {
        whiteBoardJavaFx.setStyle("-fx-border-color: black;");
        whiteBoardJavaFx.setPrefSize(wboard.getWidth(), wboard.getHeight());
        whiteBoardJavaFx.resize(wboard.getWidth(), wboard.getHeight());
        whiteBoardJavaFx.setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Create GenericButton who contains javafxButton.
     *
     * @param name the name
     * @return the generic button
     */
    @Override
    public GenericButton createButton(String name) {
        Button button   = new Button(name);
        ImageView image = new ImageView(getClass().getResource("/"+ name + ".png").toString());

        image.setFitWidth(20);
        image.setPreserveRatio(true);

        if (name.compareTo("trash") == 0)
            button.setStyle("-fx-background-color: transparent;");

        button.setGraphic(image);
        button.setOnAction(
                event -> {
                if (name.compareTo("save")==0) {
                    Mediator.getInstance().save(null);
                    event.consume();
                }
                if (name.compareTo("load")==0) {
                    Mediator.getInstance().load(null, false);
                    event.consume();
                }
                if (name.compareTo("undo")==0)
                    handle_undo(event);
                if (name.compareTo("redo")==0)
                    handle_redo(event);
                if (name.compareTo("trash")==0)
                    handle_trash(event);
            }
        );
        mapButton.put(name, button);
        return new GenericButton(button);
    }


    /**
     * Handle trash.
     *
     * @param event the event
     */
    private void handle_trash(ActionEvent event) {
        event.consume();
    }

    /**
     * Handle undo.
     *
     * @param event the event
     */
    private void handle_undo(ActionEvent event) {
        Mediator.getInstance().undo();
        event.consume();
    }

    /**
     * Handle redo.
     *
     * @param event the event
     */
    private void handle_redo(ActionEvent event) {
        Mediator.getInstance().redo();
        event.consume();
    }
    
}
