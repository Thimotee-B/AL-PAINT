package editeur.view;

import editeur.controller.Mediator;
import editeur.model.geometry.base.Point;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ApplicationFx extends AbstractApplication {
    private BorderPane borderpane;
    private Scene scene;
    private Point old;
    public ApplicationFx() {
        super("JAVAFX");
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
        this.addEvents();
        this.start();
        
        
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Al-Paint");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    
    private Point getToolBarPoint(MouseEvent e) {
        StackPane toolbar  = (StackPane)this.getToolBar().get();
        Bounds b = toolbar.localToScene(toolbar.getBoundsInLocal());
        return this.getToolBar().localPoint(b.getMinX(), b.getMinY(),e.getSceneX(), e.getSceneY());
    }
    private Point getWhiteBoardPoint(MouseEvent e) {
        StackPane whiteboard  = (StackPane)this.getWhiteBoard().get();
        Bounds b = whiteboard.localToScene(whiteboard.getBoundsInLocal());
        return this.getWhiteBoard().localPoint(b.getMinX(), b.getMinY(),e.getSceneX(), e.getSceneY());
    }

    private boolean inElement(int x , int y,StackPane element) {
        Bounds b = element.localToScene(element.getBoundsInLocal());
        if ((x > b.getMinX() && x < b.getMinX() + element.getWidth())
                && (y > b.getMinY() && y < b.getMinY() + element.getHeight())) {
            return true;
        }
        else {
            return false;
        }
    }




    public void addEvents() {
        
        
        scene.setOnMouseDragged(
                e ->{
                   if (e.getButton() == MouseButton.PRIMARY) {
                       boolean inToolbar    = inElement( (int) e.getSceneX(), (int) e.getSceneY(),(StackPane) getToolBar().get());
                       boolean inWhiteBoard = inElement( (int) e.getSceneX(), (int) e.getSceneY(),(StackPane) getWhiteBoard().get());
                       Point p = null;
                       if(inToolbar) 
                           p = this.getToolBarPoint(e);
                       else if (inWhiteBoard)
                           p = this.getWhiteBoardPoint(e);
                       if(old == null)
                           old = new Point(p);
                       //shadow? ...

                   }
                   
                });
        scene.setOnMouseReleased(
                e ->{
                   Point p = new Point((int) e.getSceneX(),(int) e.getSceneY());
                   boolean inToolbar    = inElement( (int) e.getSceneX(), (int) e.getSceneY(),(StackPane) getToolBar().get());
                   boolean inWhiteBoard = inElement( (int) e.getSceneX(), (int) e.getSceneY(),(StackPane) getWhiteBoard().get());
                   int clickSide = (e.getButton() == MouseButton.PRIMARY) ? Mediator.LEFT : Mediator.RIGHT;

                   if(inToolbar && getWhiteBoard().inWhiteBoard(old))
                        this.mediator.MouseClickEventAddTool(false, clickSide, old, getToolBarPoint(e));
                   if (inWhiteBoard && getWhiteBoard().inWhiteBoard(old))
                      this.mediator.MouseClickEvent(false, clickSide, old, getWhiteBoardPoint(e));
                   if(inToolbar && getToolBar().inToolBar(old))
                        this.mediator.MouseClickEvent(true, clickSide, old, getToolBarPoint(e));
                   if (inWhiteBoard &&  getToolBar().inToolBar(old))
                        this.mediator.MouseDraggedEvent(true, clickSide, old, getWhiteBoardPoint(e));
                    old = null;
                });
    }
   
    
    
    
}

