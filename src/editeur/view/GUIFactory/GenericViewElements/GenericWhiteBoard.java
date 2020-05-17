package editeur.view.GUIFactory.GenericViewElements;


import editeur.model.draw.DrawBridge;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;
import editeur.view.GraphicalObjectObserver;

public class GenericWhiteBoard implements GraphicalObjectObserver, IGeneric {
    private final Object     whiteBoard;
    private Composite composite;
    private DrawBridge drawbridge;

    private final int width  = 1000;
    private final int height = 900;
    @Override
    public int getWidth() {
        return width;
    }
    @Override
    public int getHeight() {
        return height;
    }

    public GenericWhiteBoard(Object whiteBoard) {
        this.whiteBoard  = whiteBoard;
        this.composite = new Composite(0,0,0,0);
    }

    @Override
    public void update() {
        if(composite.getComponents().size() > 0)
            composite.draw(this.drawbridge, this.get());
    }

    public Composite getComposite(){
        return composite;
    }
    public void setComposite(Composite composite) {
        this.composite = composite;
    }
    public void addShape(IShape shape) {
        composite.add(shape);
    }

    public void removeShape(IShape shape) {
        composite.remove(shape);
    }

    public IShape getShape(int index) {
        if(index>=0)
            return composite.getComponents().get(index);
        return null;
    }

    public IShape getShape(Point p) {
        for (IShape s : composite.getComponents())
            if (s.isInside(p))
                return s;
        return null;
    }

    public Point localPoint(double boundX, double boundY, double x, double y) {
        int newX = (int)(x - boundX);
        int newY = (int)(y - boundY);
        return new Point(newX, newY);
    }

    public boolean inWhiteBoard(Point point) {
        if (point == null) return false;
        return width >= point.getX() && height >= point.getY();
    }
    @Override
    public Object get() {
        return whiteBoard;
    }

    public void setDrawBridge (DrawBridge db){
        this.drawbridge = db;
    }


}
