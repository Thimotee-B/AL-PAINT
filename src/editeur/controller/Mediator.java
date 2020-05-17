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


public class Mediator implements IMediator {

    private static Mediator instance;
    private final IControllerInput controllerInput;
    private final IApplication app;
    private final CareTaker           careTaker;
    private Vector<IShape>     selectedShapes;
    private IShape             clickedShape;
    private int[]              coordinatesSelected;
    private final Vector<GraphicalObjectObserver> observers;
    private IMenu menu;
    private MenuBuilder         menuBuilder;
    public static final String DEFAULT_SAVE_NAME = "autosave.auber";



    public  Mediator(AbstractApplication app) {
        this.app             = app;
        this.careTaker       = new CareTaker();
        this.selectedShapes  = new Vector<IShape>();
        this.observers       = new Vector<GraphicalObjectObserver>();
        this.controllerInput = new ControllerInput(app);
        instance             = this;
    }

    public static Mediator getInstance() {
        if (instance == null)
            instance = new Mediator(null);
        return instance;
    }


    //Override SubjectObservee
    @Override
    public void Attach(GraphicalObjectObserver observer){
        this.observers.add(observer);
    }

    @Override
    public void Detach(GraphicalObjectObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void Notify() {
        Vector<GraphicalObjectObserver> clone = (Vector<GraphicalObjectObserver>) observers.clone();
        for (GraphicalObjectObserver observer : clone)
            observer.update();
    }



    //Override IMediator
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
    
    @Override
    public void move(IShape shape, int dx, int dy) {
        Command cmd = new CommandMove(shape, dx, dy);
        this.clearView();
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
    }
    
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

    @Override
    public void roundBorders(IShape r, int roundWidth, int roundHeight) {
        Command cmd = new CommandRoundBorders(r, roundWidth, roundHeight);
        this.clearView();
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
    }

    @Override
	public void unGroup(IShape s, Composite group) {
        Command cmd = new CommandUngroup(s, group);
        this.clearView();
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
		
	}

	@Override
	public void reColor(IShape toEdit,int r, int g , int b) {
        Command cmd = new CommandRecolor(toEdit, r, g, b);
        this.clearView();
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
		
	}

    @Override
    public void clearView() {
        this.app.getDrawBridge().FullClearView(app.getWhiteBoard().get(), app.getToolBar().get());
    }

    @Override
	public void ReScale(IShape shape, double factor) {
		Command cmd = new CommandRescale(shape, factor);
        this.clearView();
		cmd.execute();
		careTaker.add(cmd);
        this.Notify();
	}

	@Override
	public void rotate(IShape shape, double factor) {
	      Command cmd = new CommandRotate(shape, factor);
	      this.clearView();
	      cmd.execute();
	      careTaker.add(cmd);
	      this.Notify();
	}
	

    @Override
    public void undo() {
        this.clearView();
        careTaker.undo();
        this.Notify();
        
    }

    @Override
    public void redo() {
        this.clearView();
        careTaker.redo();
        this.Notify();
        
    }

    @Override
    public void add(IShape shapes, IShape toAdd) {
        Command cmd = new CommandAdd(shapes, toAdd);
        this.clearView();
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
        
    }
    @Override
    public void delete(IShape shapes, IShape toDelete) {
        Command cmd = new CommandDelete(shapes, toDelete);
        this.clearView();
        cmd.execute();
        careTaker.add(cmd);
        this.undoShapeAdd(toDelete);
        this.Notify();
    }


    @Override
    public void MouseClickEvent(boolean fromToolbar ,int clickSide,Point old, Point to) {
       this.controllerInput.MouseClickEvent(fromToolbar, clickSide, old, to);
    }

    @Override
    public void MouseClickEventAddTool(boolean fromToolbar ,int clickSide,Point old, Point to) {
        this.controllerInput.MouseClickEventAddTool(fromToolbar, clickSide, old, to);
    }

    @Override
    public void MouseDraggedEvent(boolean fromToolbar ,int clickSide,Point old, Point to) {
       this.controllerInput.MouseDraggedEvent(fromToolbar, clickSide, old, to);
    }

    @Override
    public void MouseTrashEvent(boolean fromToolbar, int clickSide, Point old, Point to) {
        this.controllerInput.MouseTrashEvent(fromToolbar, clickSide, old, to);
    }

    @Override
    public boolean ShowDraggedShape(boolean fromToolbar, Point old, Point to){
        return this.controllerInput.ShowDraggedShape(fromToolbar, old, to);
    }

    @Override
    public void callMenu(MenuBuilder builder, int x, int y) {
        this.menuBuilder = builder;
        this.menu = new Menu(selectedShapes, clickedShape, app.getWhiteBoard());
        this.menu.showMenu(builder, x, y);
    }

    @Override
    public void hideMenu() {
        if (this.menu != null && this.menuBuilder != null)
            this.menu.unshowMenu(this.menuBuilder);
    }

    @Override
    public boolean save(String name){
        return this.app.save(name);
    }

    @Override
    public boolean load(String name, boolean onlyToolbar){
        return this.app.load(name, onlyToolbar);
    }

    public void setClickedShape(int x, int y){
        IShape tmp = this.app.getWhiteBoard().getShape(new Point(x,y));
        if (tmp != null) clickedShape  = tmp;
    }

    public void ShowSelection(Point p1, Point p2){
        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());
        Rectangle r = new Rectangle(minX, minY, maxX - minX, maxY - minY);
        this.app.getDrawBridge().drawSelection(this.app.getWhiteBoard().get(), r);
    }


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

    //Package visibility
    void setClickedShape(IShape clickedShape) {
        this.clickedShape = clickedShape;
    }

    void setCoordinatesSelected(int [] CoordinatesSelected) {
        this.coordinatesSelected = CoordinatesSelected;
    }

    Vector<IShape> getSelectedShapes() {
        return selectedShapes;
    }
}
