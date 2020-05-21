package editeur.controller;

import java.util.Vector;

import editeur.model.commands.*;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;
import editeur.model.geometry.base.Rectangle;
import editeur.model.geometry.base.SimplePolygon;

import editeur.model.menu.IMenu;
import editeur.model.menu.Menu;
import editeur.model.menu.MenuBuilder;
import editeur.view.AbstractApplication;

import editeur.view.GraphicalObjectObserver;
import editeur.view.IApplication;


/**
 * The class Mediator implements IMediator.
 * Handle the complicated communications beetween components as CareTaker, commands, observers, application and some shapes.
 * Is also a singleton, so is accessible anywhere from the project.
 * Simplify a lot the process, all high level operations are accessible from there, and everything is updated.
 */
public class Mediator implements IMediator {

    private static Mediator instance;
    private final IControllerInput controllerInput;
    private final IApplication app;
    private final CareTaker careTaker;
    private Vector<IShape> selectedShapes;
    private IShape clickedShape;
    private int[] coordinatesSelected;
    private final Vector<GraphicalObjectObserver> observers;
    private IMenu menu;
    private MenuBuilder menuBuilder;
    public static final String DEFAULT_SAVE_NAME = "autosave.auber";


    /**
     * Instantiates a new Mediator.
     *
     * @param app the app
     */
    public  Mediator(AbstractApplication app) {
        this.app             = app;
        this.careTaker       = new CareTaker();
        this.selectedShapes  = new Vector<IShape>();
        this.observers       = new Vector<GraphicalObjectObserver>();
        this.controllerInput = new ControllerInput(app);
        instance             = this;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Mediator getInstance() {
        if (instance == null)
            instance = new Mediator(null);
        return instance;
    }

//Override SubjectObservee interface.
    /**
     * Attach the observer to the Observe for further updates.
     *
     * @param observer the observer
     */
    @Override
    public void Attach(GraphicalObjectObserver observer){
        this.observers.add(observer);
    }

    /**
     * Detach the observer of observe list. Will not receive updates from now.
     *
     * @param observer the observer
     */
    @Override
    public void Detach(GraphicalObjectObserver observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all the observers that they need to update.
     */
    @Override
    public void Notify() {
        Vector<GraphicalObjectObserver> clone = (Vector<GraphicalObjectObserver>) observers.clone();
        for (GraphicalObjectObserver observer : clone)
            observer.update();
    }

//Override IMediator interface.
    /**
     * Define the start behavior. Auto load the old toolbar if exists else init it with 3 shapes.
     */
    @Override
    public void start() {
        if (!this.load(DEFAULT_SAVE_NAME, true)) {
            Rectangle r = new Rectangle(20, 50, 100, 100);
            r.changeColor(22, 169, 243);
            SimplePolygon p = new SimplePolygon(20, 300, 6, 40);
            p.changeColor(50, 50, 0);
            SimplePolygon p2 = new SimplePolygon(20, 500, 5, 50);
            p.changeColor(50, 150, 80);
            app.getToolBar().addShape(r);
            app.getToolBar().addShape(p);
            app.getToolBar().addShape(p2);
            this.save(DEFAULT_SAVE_NAME);
        }
        else
            System.out.println("AutoSave Load, Congrats =)");
        this.Notify();
    }

    /**
     * Move the shape shape at dx, dy position.
     * AutoUpdate.
     * @param shape the shape
     * @param dx    the dx
     * @param dy    the dy
     */
    @Override
    public void move(IShape shape, int dx, int dy) {
        Command cmd = new CommandMove(shape, dx, dy);
        this.clearView();
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
    }

    /**
     * Group the selected shapes and add this group shape s.
     * Everything is updated, handle the commands and update the view.
     * @param s the shape
     */
    @Override
	public void group(IShape s) {
		Command cmd = new CommandGroup(s, selectedShapes, coordinatesSelected);
        this.clearView();
		cmd.execute();
		careTaker.add(cmd);
        for (IShape delete : selectedShapes)
           app.getWhiteBoard().getComposite().remove(delete);
		this.Notify();
	}

    /**
     * Round borders of a rectangle else do nothing.
     * Everything is updated.
     * @param r           the rectangle
     * @param roundWidth  the round width wanted
     * @param roundHeight the round height wanted
     */
    @Override
    public void roundBorders(IShape r, int roundWidth, int roundHeight) {
        Command cmd = new CommandRoundBorders(r, roundWidth, roundHeight);
        this.clearView();
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
    }

    /**
     * Cancel the group composite, and add all its shapes to shape s ( his parent).
     * Everything is updated.
     * @param s     the s
     * @param group the group
     */
    @Override
	public void unGroup(IShape s, Composite group) {
        Command cmd = new CommandUngroup(s, group);
        this.clearView();
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
		
	}

    /**
     * ReColor the shape s.
     * AutoUpdate.
     * @param s the shape
     * @param r the red value
     * @param g the green value
     * @param b the blue value
     */
    @Override
	public void reColor(IShape toEdit,int r, int g , int b) {
        Command cmd = new CommandRecolor(toEdit, r, g, b);
        this.clearView();
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
		
	}

    /**
     * Clear view.
     */
    @Override
    public void clearView() {
        this.app.getDrawBridge().FullClearView(app.getWhiteBoard().get(), app.getToolBar().get());
    }

    /**
     * Rescale the shape shape factor times.
     * AutoUpdate.
     * @param shape  the shape
     * @param factor the factor
     */
    @Override
	public void ReScale(IShape shape, double factor) {
		Command cmd = new CommandRescale(shape, factor);
        this.clearView();
		cmd.execute();
		careTaker.add(cmd);
        this.Notify();
	}

    /**
     * Rotate the shape shape of factor angle.
     * AutoUpdate.
     * @param shape  the shape
     * @param factor the factor
     */
    @Override
	public void rotate(IShape shape, double factor) {
	      Command cmd = new CommandRotate(shape, factor);
	      this.clearView();
	      cmd.execute();
	      careTaker.add(cmd);
	      this.Notify();
	}


    /**
     * Simply undo the last action. AutoUpdate.
     */
    @Override
    public void undo() {
        this.clearView();
        careTaker.undo();
        this.Notify();
        
    }

    /**
     * Simply redo the last action. AutoUpdate.
     */
    @Override
    public void redo() {
        this.clearView();
        careTaker.redo();
        this.Notify();
        
    }

    /**
     * Add toAdd shape to shapes.
     * AutoUpdate.
     * @param shapes the shapes
     * @param toAdd  the to add
     */
    @Override
    public void add(IShape shapes, IShape toAdd) {
        Command cmd = new CommandAdd(shapes, toAdd);
        this.clearView();
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
        
    }

    /**
     * Delete toDelete shape from shapes.
     * AutoUpdate.
     * @param shapes   the shapes
     * @param toDelete the to delete
     */
    @Override
    public void delete(IShape shapes, IShape toDelete) {
        Command cmd = new CommandDelete(shapes, toDelete);
        this.clearView();
        cmd.execute();
        careTaker.add(cmd);
        this.undoShapeAdd(toDelete);
        this.Notify();
    }


    /**
     * Mouse click event: manage the current mouseEvent.
     * Move the shape pointed by old point to to position.
     * Else select the shapes contains in a rectangle from old to to position.
     *
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    @Override
    public void MouseClickEvent(boolean fromToolbar ,int clickSide,Point old, Point to) {
       this.controllerInput.MouseClickEvent(fromToolbar, clickSide, old, to);
    }

    /**
     * Mouse click event for adding a tool ( a shape in the toolbar.)
     * Simply clone and add the cloned shape at old position if exists and add it to the to position.
     * Does nothing if from toolbar
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    @Override
    public void MouseClickEventAddTool(boolean fromToolbar ,int clickSide,Point old, Point to) {
        this.controllerInput.MouseClickEventAddTool(fromToolbar, clickSide, old, to);
    }

    /**
     * Mouse dragged event: Simply drag a shape from toolbar at old position and clone it to to position
     * in whiteboard.
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    @Override
    public void MouseDraggedEvent(boolean fromToolbar ,int clickSide,Point old, Point to) {
       this.controllerInput.MouseDraggedEvent(fromToolbar, clickSide, old, to);
    }

    /**
     * Mouse trash event : Handle the deletion of shape at old position put in trash
     *
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    @Override
    public void MouseTrashEvent(boolean fromToolbar, int clickSide, Point old, Point to) {
        this.controllerInput.MouseTrashEvent(fromToolbar, clickSide, old, to);
    }

    /**
     * Show dragged shape boolean : Draw the current dragged shape, disclaimer, a temporary clone is moved until we
     * reach destination.
     *
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param old         the old position
     * @param to          the to position
     * @return the boolean : false if we dragged nothing.
     */
    @Override
    public boolean ShowDraggedShape(boolean fromToolbar, Point old, Point to){
        return this.controllerInput.ShowDraggedShape(fromToolbar, old, to);
    }

    /**
     * Call menu at position x,y
     *
     * @param builder the menu builder who handle of construction.
     * @param x       the x
     * @param y       the y
     */
    @Override
    public void callMenu(MenuBuilder builder, int x, int y) {
        this.menuBuilder = builder;
        this.menu = new Menu(selectedShapes, clickedShape, app.getWhiteBoard());
        this.menu.showMenu(builder, x, y);
    }

    /**
     * Hide the called menu if exists.
     */
    @Override
    public void hideMenu() {
        if (this.menu != null && this.menuBuilder != null)
            this.menu.unshowMenu(this.menuBuilder);
    }

    /**
     * Save the current whiteboard and toolbar.
     *
     * @param name the name file
     * @return the boolean
     */
    @Override
    public boolean save(String name){
        return this.app.save(name);
    }

    /**
     * Load the saved toolbar if only toolbar true else load the saved toolbar & whiteboard.
     *
     * @param name        the name file.
     * @param onlyToolbar the only toolbar
     * @return the boolean
     */
    @Override
    public boolean load(String name, boolean onlyToolbar){
        return this.app.load(name, onlyToolbar);
    }

    /**
     * Set the clicked shape at x,y if exists.
     *
     * @param x the x
     * @param y the y
     */
    public void setClickedShape(int x, int y){
        IShape tmp = this.app.getWhiteBoard().getShape(new Point(x,y));
        if (tmp != null) clickedShape  = tmp;
    }

    /**
     * Show the current selection rectangle from p1 to p2 position.
     *
     * @param p1 the p 1
     * @param p2 the p 2
     */
    @Override
    public void ShowSelection(Point p1, Point p2){
        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());
        Rectangle r = new Rectangle(minX, minY, maxX - minX, maxY - minY);
        this.app.getDrawBridge().drawSelection(this.app.getWhiteBoard().get(), r);
    }


    /**
     * Undo shape only from view.
     *
     * @param shape the shape
     */
    public void undoShapeAdd(IShape shape){
        if (shape instanceof  Composite){
            Composite c = (Composite) shape;
            for (IShape s : c.getComponents())
                undoShapeAdd(s);
        }
        else
            this.app.getDrawBridge().clearView
                    (
                            this.app.getWhiteBoard().get()
                            , this.app.getToolBar().get()
                            , shape
                    );
    }

    /**
     * Sets clicked shape.
     *
     * @param clickedShape the clicked shape
     */

    void setClickedShape(IShape clickedShape) {
        this.clickedShape = clickedShape;
    }

    /**
     * Sets coordinates selected : the coordinates of the selection.
     *
     * @param CoordinatesSelected the coordinates selected
     */
    void setCoordinatesSelected(int [] CoordinatesSelected) {
        this.coordinatesSelected = CoordinatesSelected;
    }

    /**
     * Gets selected shapes.
     *
     * @return the selected shapes
     */
    Vector<IShape> getSelectedShapes() {
        return selectedShapes;
    }
}
