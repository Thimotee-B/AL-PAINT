package editeur.view;

import java.util.Vector;

import editeur.model.draw.DrawBridge;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;

public class GenericWhiteBoard implements  GraphicalObjectObserver{
    private Object     whiteBoard;
    private Composite  shapeVector;
    private DrawBridge drawbridge;

    private final int width  = 1000;
    private final int height = 900;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GenericWhiteBoard(Object whiteBoard) {
        this.whiteBoard  = whiteBoard;
        this.shapeVector = new Composite(0,0,0,0);
    }
    public Composite getShapeVector(){
        return shapeVector;
    }
    public void setShapeVector(Composite shapeVector) {
        this.shapeVector = shapeVector;
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

    public IShape getShape(Point p) {
        for (IShape s : shapeVector.getComponents())
            if (s.isInside(p))
                return s;
        return null;
    }

    public Point localPoint(double boundX, double boundY, double x, double y) {
        int newX = (int)(x - boundX);
        int newY = (int)(y - boundY);
        return new Point(newX, newY);
    }
    public boolean inWhiteBoard(int x , int y) {
        return width >= x && height >= y;
    }
    public boolean inWhiteBoard(Point point) {
        if (point == null) return false;
        return width >= point.getX() && height >= point.getY();
    }

    public Object get() {
        return whiteBoard;
    }

    public void setDrawBridge (DrawBridge db){
        this.drawbridge = db;
    }


    @Override
    public void update() {
        if(shapeVector.getComponents().size() > 0)
            shapeVector.draw(this.drawbridge, this.get());
    }
}
