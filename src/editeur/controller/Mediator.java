package editeur.controller;

import java.util.Vector;

import editeur.model.commands.*;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;
import editeur.model.geometry.base.Rectangle;
import editeur.model.geometry.base.SimplePolygon;

import editeur.model.menu.Menu;
import editeur.model.menu.MenuBuilder;
import editeur.view.AbstractApplication;

import editeur.view.GUIFactory.GenericViewElements.GenericToolBar;
import editeur.view.GraphicalObjectObserver;



public class Mediator implements IMediator {

    private static Mediator instance;
    private final AbstractApplication app;
    private final CareTaker           careTaker;
    private final Vector<IShape>     selectedShapes;
    private IShape             clickedShape;
    private int[]              coordinatesSelected;
    private final Vector<GraphicalObjectObserver> observers;
    private Menu                menu;
    private MenuBuilder         menuBuilder;
    public static final String DEFAULT_SAVE_NAME = "autosave.auber";



    public  Mediator(AbstractApplication app) {
        this.app            = app;
        this.careTaker      = new CareTaker();
        this.selectedShapes = new Vector<IShape>();
        this.observers      = new Vector<GraphicalObjectObserver>();
        instance            = this;
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
           app.getWhiteBoard().getShapeVector().remove(delete);
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

    private int[] AddToSelection(Point p1 , Point p2){
        selectedShapes.removeAllElements();
        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());
        for (int x = minX; x <= maxX ; x++)
            for (int y = minY ; y <= maxY ; y++)
                for (IShape s : app.getWhiteBoard().getShapeVector().getComponents())
                    if( s.isInside(new Point(x,y)) && !selectedShapes.contains(s))
                        selectedShapes.add(s);
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = 0;
        maxY = 0;
        int h = 0, w= 0;
        for (IShape s : selectedShapes){
            if (s.getPosition().getX() < minX)
                minX = s.getPosition().getX();
            if (s.getPosition().getX() + s.getWidth() > maxX)
                maxX = s.getPosition().getX() + s.getWidth();

            if (s.getPosition().getY() < minY)
                minY = s.getPosition().getY();
            if (s.getPosition().getY() + s.getHeight() > maxY) {
                maxY = s.getPosition().getY() + s.getHeight();
            }

        }

        int [] tab = { minX , minY , maxX -minX , maxY - minY};

        return tab;
    }

    public void ShowSelection(Point p1, Point p2){
        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());
        Rectangle r = new Rectangle(minX, minY, maxX - minX, maxY - minY);
        this.app.getDrawBridge().drawSelection(this.app.getWhiteBoard().get(), r);
    }

    private Point computeNewPos(IShape s, Point p1, Point p2) {
        Point oldPos = s.getPosition();
        int stepX = p1.getX() - oldPos.getX();
        int stepY = p1.getY() - oldPos.getY();
        return new Point(p2.getX()-stepX, p2.getY()-stepY);
    }

    private void limit(IShape s, Point p, boolean toolbar, boolean addTool){
        int width  = (toolbar) ? this.app.getToolBar().getWidth() : this.app.getWhiteBoard().getWidth();
        int height = (toolbar) ? this.app.getToolBar().getHeight() : this.app.getWhiteBoard().getHeight();
        if (p.getX() + s.getWidth() >= width)
            p.setX(width - s.getWidth());
        if ( !addTool && p.getX()<= 0)
            p.setX(0);
        if (p.getY() + s.getHeight() >= height)
            p.setY(height - s.getHeight());
        if (p.getY()<= 0)
            p.setY(0);

        if (addTool && p.getX() < - this.app.getToolBar().getWidth())
            p.setX(- this.app.getToolBar().getWidth());
    }

    @Override
    public void MouseClickEvent(boolean fromToolbar ,int clickSide,Point old, Point to) {
        if (clickSide == LEFT && old != null){
            IShape s;
            if(fromToolbar) {
                s = this.app.getToolBar().getShape(old);
                this.clickedShape = s;
                if (s != null) {
                    Point p = computeNewPos(s, old, to);
                    limit(s,p,true,false);
                    this.move(s, p.getX(), p.getY());
                }
            }

            else {
                s = this.app.getWhiteBoard().getShape(old);
                this.clickedShape = s;
                if (s != null){
                    Point p = computeNewPos(s, old, to);
                    limit(s,p,false,false);
                    this.move(s, p.getX(), p.getY());
                }
                else {
                    this.coordinatesSelected =this.AddToSelection(old, to);
                }
            }

        }
        
    }

    private void scaleTool(IShape tool, GenericToolBar toolBar){
            if (tool.getWidth() > toolBar.getWidth() && toolBar.getWidth() > 0
                    || tool.getWidth() >= toolBar.getToolMaxSize() ){

                double factor = (double)toolBar.getToolMaxSize() / (double) tool.getWidth();
                tool.scale(factor);
                this.Notify();

            }


    }


    @Override
    public void MouseClickEventAddTool(boolean fromToolbar ,int clickSide,Point old, Point to) {
        if (clickSide == LEFT && old != null){
            IShape s;
            if(!fromToolbar) {
                s = this.app.getWhiteBoard().getShape(old);
                if (s != null){
                    IShape tool  = s.clone();
                    scaleTool(tool, this.app.getToolBar());
                    Point  p     = computeNewPos(tool, old, to);
                    limit(tool,p,true,false);
                    int alignX = p.getX();
                    int alignY = p.getY();

                    tool.move(alignX, alignY);
                    boolean mustAlign = false;
                    if( tool instanceof  Composite){
                        Composite c = (Composite) tool;
                        for (IShape myShape : c.getComponents())
                            if (!app.getToolBar().inToolBar(myShape.getPosition()))
                                mustAlign = true;
                        int minX      = c.minComponents()[0];
                        int minWidth  = c.minComponents()[1];
                        int minY      = c.minComponents()[2];
                        int minHeight = c.minComponents()[3];
                        if (minX < alignX)
                            alignX = alignX - minX + minWidth/2;
                        if (minY < alignY)
                            alignY = minY - minHeight;
                    }
                    if (mustAlign) {
                        Point align = new Point(alignX,alignY);
                        limit(tool,align,false,false);
                        tool.move(align.getX(), align.getY());
                    }
                    this.Notify();
                    if(app.getToolBar().inToolBar(to)) {
                        this.add(app.getToolBar().getShapeVector(), tool);
                    }
                }
            }

        }
    }

    @Override
    public void MouseDraggedEvent(boolean fromToolbar ,int clickSide,Point old, Point to) {
        if (clickSide == LEFT && old != null){
            IShape s;
            if(fromToolbar) {
                s = this.app.getToolBar().getShape(old);
                if (s != null){
                    Point  p     = computeNewPos(s, old, to);
                    IShape tool  = s.clone();
                    limit(tool,p,false,false);
                    tool.move(p.getX(), p.getY());
                    this.clearView();
                    this.Notify();
                    if(app.getWhiteBoard().inWhiteBoard(p))
                        this.add(app.getWhiteBoard().getShapeVector(), tool);
                }
            }

        }
    }

    @Override
    public void MouseTrashEvent(boolean fromToolbar, int clickSide, Point old, Point to) {
        if (clickSide == LEFT && old != null) {
            IShape s;
            if (fromToolbar) {
                s = this.app.getToolBar().getShape(old);
                if (s != null)
                    this.delete(this.app.getToolBar().getShapeVector(), s);
            } else {
                s = this.app.getWhiteBoard().getShape(old);
                if (s != null)
                    this.delete(this.app.getWhiteBoard().getShapeVector(), s);
            }

        }
    }

    @Override
    public boolean ShowDraggedShape(boolean fromToolbar, Point old, Point to){
        IShape s, clone;
        if (fromToolbar) {
            s = this.app.getToolBar().getShape(old);
            if (s == null) return false;

            clone = s.clone();
            Point p = computeNewPos(s, old, to);

            if (p.getX() < 0) p.setX(0);
            if (p.getY() < 0) p.setY(0);
            if (p.getY() + clone.getHeight() > this.app.getToolBar().getHeight() + this.app.getTrashButton().getHeight()) {
                clone.scale( (float)this.app.getTrashButton().getHeight() / (float) clone.getHeight() );
                p.setY(this.app.getToolBar().getHeight() + this.app.getTrashButton().getHeight() - clone.getHeight());
            }

            clone.move(p.getX(), p.getY());
            clone.draw(this.app.getDrawBridge(),this.app.getToolBar().get());
        }
        else {
            s = this.app.getWhiteBoard().getShape(old);
            if (s == null) return false;

            clone = s.clone();
            this.undoShapeAdd(s);
            Point p = computeNewPos(s, old, to);
            limit(clone,p,false,true);

            clone.move(p.getX(), p.getY());
            clone.draw(this.app.getDrawBridge(),this.app.getWhiteBoard().get());
        }
        return true;

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



}
