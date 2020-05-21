package editeur.view;

import editeur.controller.Mediator;
import editeur.model.geometry.Composite;
import editeur.model.geometry.base.Point;
import editeur.model.menu.JavaFxMenuBuilder;
import editeur.model.menu.MenuBuilder;

import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

/**
 * The type ApplicationFx extends Abstract Application in order to set javafx components we need, and
 * to create the start point of the javafx native Application.
 */
public class ApplicationFx extends AbstractApplication {
    private BorderPane borderpane;
    private Scene scene;
    private Point old;
    private boolean OldinToolbar, OldinWhiteBoard;

    /**
     * Instantiates a new Application fx.
     */
    public ApplicationFx() {
        super("JAVAFX");
    }

    /**
     * Init factory elements.
     */
    @Override
    public void initFactoryElements() {
        super.initFactoryElements();
    }

    /**
     * Start.
     */
    public void start() {
        super.start();
    }

    /**
     * Init.
     *
     * @throws Exception the exception
     */
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

        topbar.getItems().addAll(new Separator(),
                (Button)this.getSaveButton().get(), (Button)this.getLoadButton().get() ,
                new Separator(),
                (Button)this.getUndoButton().get(),(Button) this.getRedoButton().get(),
                new Separator());
        this.addEvents();
        this.start();
        
        
        
    }

    /**
     * Start.
     *
     * @param primaryStage the primary stage
     * @throws Exception the exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Al-Paint");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    /**
     * Stop.
     */
    @Override
    public void stop(){
        super.end();
    }

    /**
     * Save boolean.
     *
     * @param name the name
     * @return the boolean
     */
    @Override
    public boolean save(String name) {
        File savedFile = null;
        if(name == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save your art!");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("paint auber", "*.auber")
            );
            fileChooser.setInitialFileName("paint-ksos");

            savedFile = fileChooser.showSaveDialog(null);
        }
        else
            savedFile = new File(name);

        if (savedFile == null) {
            return false;
        }

        ObjectOutputStream ostream;
        try {
            ostream = new ObjectOutputStream(new FileOutputStream(savedFile.getAbsolutePath()));
            ostream.writeObject(getToolBar().getComposite());
            ostream.writeChar('~');
            ostream.writeObject(getWhiteBoard().getComposite());
            ostream.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Load boolean.
     *
     * @param name        the name
     * @param onlyToolbar the only toolbar
     * @return the boolean
     */
    @Override
    public boolean load(String name, boolean onlyToolbar) {
        Mediator.getInstance().clearView();
        File selectedFile = null;
        if (name == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load your art!");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("paint auber", "*.auber")
            );
            selectedFile = fileChooser.showOpenDialog(null);
        }
        else
            selectedFile = new File(name);
        if (selectedFile == null) {
            return false;
        }

        ObjectInputStream istream;
        try {
            istream = new ObjectInputStream(new FileInputStream(selectedFile.getAbsolutePath()));
            // on lit la toolbar
            try {
                getToolBar().setComposite((Composite) istream.readObject());
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
                return false;
            }
            if (!onlyToolbar) {
                // on console le ~
                istream.readChar();

                // on lit la whiteboard
                try {
                    getWhiteBoard().setComposite((Composite) istream.readObject()); // on lit la whiteboard
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

        //refresh
        Mediator.getInstance().Notify();
        return true;


    }


    /**
     * Add events.
     */
    public void addEvents() {

        scene.setOnMouseClicked(
                e ->{
                    if (e.getButton() == MouseButton.SECONDARY){
                        MenuBuilder builder = JavaFxMenuBuilder.getInstance();
                        Point to = this.getWhiteBoardPoint(e);
                        Mediator.getInstance().setClickedShape(to.getX(), to.getY());
                        Mediator.getInstance().callMenu(builder, (int) e.getScreenX(), (int) e.getScreenY());
                    }
                    if (e.getButton() == MouseButton.PRIMARY) {
                        Mediator.getInstance().hideMenu();
                    }

                });




        scene.setOnMouseDragged(
                e ->{
                    if (e.getButton() == MouseButton.PRIMARY) {
                        this.mediator.clearView();
                        this.mediator.Notify();
                        if(old == null) {
                            OldinToolbar = inElement((int) e.getSceneX(), (int) e.getSceneY(), (StackPane) getToolBar().get());
                            OldinWhiteBoard = inElement((int) e.getSceneX(), (int) e.getSceneY(), (StackPane) getWhiteBoard().get());
                        }
                        Point p = null;
                        if(OldinToolbar)
                            p = this.getToolBarPoint(e);
                        else if (OldinWhiteBoard)
                            p = this.getWhiteBoardPoint(e);
                        if(old == null)
                            old = new Point(p);
                        if(!this.mediator.ShowDraggedShape(OldinToolbar, old, p))
                            if(OldinWhiteBoard)
                                this.mediator.ShowSelection(old, p);

                    }

                });
        scene.setOnMouseReleased(
                e ->{
                    this.mediator.clearView();
                    this.mediator.Notify();
                    Point p = new Point((int) e.getSceneX(),(int) e.getSceneY());
                    boolean inToolbar    = inElement( (int) e.getSceneX(), (int) e.getSceneY(),(StackPane) getToolBar().get());
                    boolean inWhiteBoard = inElement( (int) e.getSceneX(), (int) e.getSceneY(),(StackPane) getWhiteBoard().get());
                    boolean inTrash    = inButton( (int) e.getSceneX(), (int) e.getSceneY(),(Button) this.getTrashButton().get());
                    int clickSide = (e.getButton() == MouseButton.PRIMARY) ? Mediator.LEFT : Mediator.RIGHT;

                    if(inToolbar && OldinWhiteBoard)
                        this.mediator.MouseClickEventAddTool(false, clickSide, old, getToolBarPoint(e));
                    if (inWhiteBoard && OldinWhiteBoard)
                        this.mediator.MouseClickEvent(false, clickSide, old, getWhiteBoardPoint(e));
                    if(inToolbar && OldinToolbar)
                        this.mediator.MouseClickEvent(true, clickSide, old, getToolBarPoint(e));
                    if (inWhiteBoard &&  OldinToolbar)
                        this.mediator.MouseDraggedEvent(true, clickSide, old, getWhiteBoardPoint(e));
                    if (inTrash && OldinToolbar)
                        this.mediator.MouseTrashEvent(true, clickSide, old, getToolBarPoint(e));
                    if (inTrash && OldinWhiteBoard)
                        this.mediator.MouseTrashEvent(false, clickSide, old, getToolBarPoint(e));
                    old = null;
                });
    }


    /**
     * Gets tool bar point.
     *
     * @param e the e
     * @return the tool bar point
     */
    private Point getToolBarPoint(MouseEvent e) {
        StackPane toolbar  = (StackPane)this.getToolBar().get();
        Bounds b = toolbar.localToScene(toolbar.getBoundsInLocal());
        return this.getToolBar().localPoint(b.getMinX(), b.getMinY(),e.getSceneX(), e.getSceneY());
    }

    /**
     * Gets white board point.
     *
     * @param e the e
     * @return the white board point
     */
    private Point getWhiteBoardPoint(MouseEvent e) {
        StackPane whiteboard  = (StackPane)this.getWhiteBoard().get();
        Bounds b = whiteboard.localToScene(whiteboard.getBoundsInLocal());
        return this.getWhiteBoard().localPoint(b.getMinX(), b.getMinY(),e.getSceneX(), e.getSceneY());
    }

    /**
     * In element boolean.
     *
     * @param x       the x
     * @param y       the y
     * @param element the element
     * @return the boolean
     */
    private boolean inElement(int x , int y,StackPane element) {
        Bounds b = element.localToScene(element.getBoundsInLocal());
        return ((x > b.getMinX() && x < b.getMinX() + element.getWidth())
                && (y > b.getMinY() && y < b.getMinY() + element.getHeight()));
    }

    /**
     * In button boolean.
     *
     * @param x      the x
     * @param y      the y
     * @param button the button
     * @return the boolean
     */
    private boolean inButton(int x , int y, Button button){
        Bounds b = button.localToScene(button.getBoundsInLocal());
        return ((x > b.getMinX() && x < b.getMinX() + button.getWidth())
                && (y > b.getMinY() && y < b.getMinY() + button.getHeight()));
    }

}

