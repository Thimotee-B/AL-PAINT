package editeur.controller;

import java.util.List;
import java.util.Observable;
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

import editeur.view.GenericToolBar;
import editeur.view.GraphicalObjectObserver;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;


public class Mediator implements IMediator {

    
	//On peut en faire un singleton si on veut
    private static Mediator instance;
    private AbstractApplication app;
    private CareTaker           careTaker;
    private Vector<IShape>     selectedShapes;
    private IShape             clickedShape;
    private int[]              coordinatesSelected;
    private Vector<GraphicalObjectObserver> observers;



    public  Mediator(AbstractApplication app) {
        this.app            = app;
        this.careTaker      = new CareTaker();
        this.selectedShapes = new Vector<IShape>();
        this.observers      = new Vector<GraphicalObjectObserver>();
        instance            = this;
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
        Rectangle r = new Rectangle(20,50,100,100);
        r.changeColor(22, 169, 243);
        SimplePolygon p = new SimplePolygon(20,300,6,40);
        p.changeColor(50,50,0);
        SimplePolygon p2 = new SimplePolygon(20,500,5,50);
        p.changeColor(50,150,80);
        app.getToolBar().addShape(r);
        app.getToolBar().addShape(p);
        app.getToolBar().addShape(p2);
        this.app.getToolBar().setStartShapesIndices(3);
        this.Notify();
    }
    
    @Override
    public void move(IShape shape, int dx, int dy) {
        Command cmd = new CommandMove(shape, dx, dy);
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
    }
    
	@Override
	public void group(IShape s) {
		Command cmd = new CommandGroup(s, selectedShapes, coordinatesSelected);
		cmd.execute();
		careTaker.add(cmd);
        for (IShape delete : selectedShapes)
           app.getWhiteBoard().getShapeVector().remove(delete);
		this.Notify();
	}

    @Override
    public void roundBorders(Rectangle r, int roundWidth, int roundHeight) {
        Command cmd = new CommandRoundBorders(r, roundWidth, roundHeight);
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
    }

    @Override
	public void unGroup(IShape s, Composite group) {
        Command cmd = new CommandUngroup(s, group);
        cmd.execute();
        careTaker.add(cmd);
        /*for (IShape delete : selectedShapes)
            app.getWhiteBoard().getShapeVector().remove(delete);*/
        this.Notify();
		
	}

	@Override
	public void reColor(IShape toEdit,int r, int g , int b) {
        Command cmd = new CommandRecolor(toEdit, r, g, b);
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
		cmd.execute();
		careTaker.add(cmd);
        this.Notify();
	}

	@Override
	public void rotate(IShape shape, double factor) {
	      Command cmd = new CommandRotate(shape, factor);
	      cmd.execute();
	      careTaker.add(cmd);
	      this.Notify();
	}
	
	public static Mediator getInstance() {
	    return instance;
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

    @Override
    public void undo() {
        careTaker.undo();
        this.Notify();
        
    }

    @Override
    public void redo() {
        careTaker.redo();
        this.Notify();
        
    }

    @Override
    public void add(IShape shapes, IShape toAdd) {
        Command cmd = new CommandAdd(shapes, toAdd);
        cmd.execute();
        careTaker.add(cmd);
        this.Notify();
        
    }
    @Override
    public void delete(IShape shapes, IShape toDelete) {
        Command cmd = new CommandDelete(shapes, toDelete);
        cmd.execute();
        careTaker.add(cmd);
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
        //maxX = 0;
        //maxY = 0;
        int h = 0, w= 0;
        for (IShape s : selectedShapes){
            if (s.getPosition().getX() < minX)
                minX = s.getPosition().getX();
            //if (s.getPosition().getX() > maxX)
             //   maxX = s.getPosition().getX();

            if (s.getPosition().getY() < minY)
                minY = s.getPosition().getY();
            //if (s.getPosition().getY() > maxY) {
              //  maxY = s.getPosition().getY();
            //}

        }

        int [] tab = { minX , minY , maxX -minX , maxY - minY};
        //Debug//Rectangle r = new Rectangle(minX, minY, maxX - minX, maxY - minY);
        //Debug//this.app.getDrawBridge().drawSelection(this.app.getWhiteBoard().get(), r);
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
        //test x et y sont la pos des clicks
        //selectedShapes.clear();
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
                    /*if(selectedShapes.size() > 0) {
                        this.group(app.getWhiteBoard().getShapeVector());
                        for (IShape delete : selectedShapes) {
                            app.getWhiteBoard().getShapeVector().remove(delete);
                        }*/
                        //this.clearView();
                        //this.Notify();
                        //System.out.println(app.getWhiteBoard().getShapeVector().getComponents().size());
                        //System.out.println(selectedShapes.size());

                   // }

                }
            }

        }
        
    }
    //TODO: A refactor autrement, surement avec une boite de collision dans Ishape,
    //Ou méthode getwidth getheight pour tous, ou adaptator pour polygon whatever
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
                    System.out.println("cc");
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
                    //System.out.println("Align Y : " + alignY + " y : " + minY);
                    }
                    if (mustAlign) {
                        Point align = new Point(alignX,alignY);
                        limit(tool,align,false,false);
                        tool.move(align.getX(), align.getY());
                    }
                    this.Notify();
                    //TODO: méthode intersect pour aligner les formes
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


    public boolean ShowDraggedShape(boolean fromToolbar, Point old, Point to){
        IShape s, clone;
        //Todo: Juste déterminer si whiteboard ou toolbar avec un temp sur le dragged
        if (fromToolbar) {
            s = this.app.getToolBar().getShape(old);
            if (s == null) return false;
            clone = (IShape) s.clone();
            Point p = computeNewPos(s, old, to);
            if (p.getX() < 0) p.setX(0);
            if (p.getY() < 0) p.setY(0);
            if (p.getY() + clone.getHeight() > this.app.getToolBar().getHeight())
                p.setY(this.app.getToolBar().getHeight() - clone.getHeight());
            clone.move(p.getX(), p.getY());
            clone.draw(this.app.getDrawBridge(),this.app.getToolBar().get());
        }
        else {
            s = this.app.getWhiteBoard().getShape(old);
            if (s == null) return false;
            clone = (IShape) s.clone();
            this.undoShapeAdd(s);
            Point p = computeNewPos(s, old, to);
            limit(clone,p,false,true);
            clone.move(p.getX(), p.getY());
            clone.draw(this.app.getDrawBridge(),this.app.getWhiteBoard().get());
        }
        return true;

    }

    public void setClickedShape(int x, int y){
        IShape tmp = this.app.getWhiteBoard().getShape(new Point(x,y));
        if (tmp != null) clickedShape  = tmp;
    }

    @Override
    public void callMenu(MenuBuilder builder, int x, int y) {
        Menu m = new Menu(selectedShapes, clickedShape, app.getWhiteBoard());
        m.showMenu(builder, x, y);

    }

    //Mouse events ? c'est un peu différent de java awt (la first gen javafx)
	//donc je sais pas si on peut hériter en javafx d'une classe mouse listener

}
