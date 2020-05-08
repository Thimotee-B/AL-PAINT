package editeur.view;

import java.time.chrono.IsoChronology;
import java.util.Vector;

import editeur.model.draw.DrawBridge;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;

public class GenericToolBar implements GraphicalObjectObserver {
    private Object toolBar;
    private final int width  = 150; 
    private final int height = 900;
    private final int toolMaxSize = 75;
    private final int alignToolX  = 25;
    private Composite shapeVector;
    private DrawBridge drawbridge;
    private int startShapesIndices = 0;

    public GenericToolBar(Object toolBar) {
        this.toolBar = toolBar;
        this.shapeVector = new Composite(0,0,0,0);
    }



    public int getStartShapesIndices() {
        return startShapesIndices;
    }

    public void setStartShapesIndices(int startShapesIndices) {
        this.startShapesIndices = startShapesIndices;
    }

    public void addShape(IShape shape) {
        shapeVector.add(shape);
    }
    
    public void removeShape(IShape shape) {
        shapeVector.remove(shape);
    }
    
    public IShape getShape(int index) {
        if(index>=0)
            return shapeVector.getComponents().get(index);
        return null;
    }

    public Composite getShapeVector(){
        return shapeVector;
    }
    public void setShapeVector(Composite shapeVector) {
        this.shapeVector = shapeVector;
    }

    public IShape getShape(Point p) {
        for (IShape s : shapeVector.getComponents())
            if (s.isInside(p))
                return s;
        return null;
    }
    
    public Object get() {
        return toolBar;
    }

    public int getWidth() {
        return width;
    }
    
    public Point localPoint(double boundX, double boundY, double x, double y) {
        int newX = (int)(x - boundX);
        int newY = (int)(y - boundY);
        return new Point(newX, newY);
    }
    
    public boolean inToolBar(int x , int y) {
       return width >= x && height >= y;
    }



    public boolean inToolBar(Point p) {
        if (p == null) return false;
        return width >= p.getX() && height >= p.getY();
     }

    public void setDrawBridge (DrawBridge db){
        this.drawbridge = db;
    }

    public int getToolMaxSize(){
        return toolMaxSize;
    }

    @Override
    public void update() {
        if(shapeVector.getComponents().size() > 0)
            shapeVector.draw(this.drawbridge, this.get());
    }

}
