package editeur.view.GUIFactory.GenericViewElements;

import editeur.model.draw.DrawBridge;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;
import editeur.view.GraphicalObjectObserver;

public class GenericToolBar implements GraphicalObjectObserver, IGeneric {
    private final int width  = 150;
    private final int height = 900;
    private final int toolMaxSize = 75;

    private final Object toolBar;
    private Composite composite;
    private DrawBridge drawbridge;


    public GenericToolBar(Object toolBar) {
        this.toolBar = toolBar;
        this.composite = new Composite(0,0,0,0);
    }

    @Override
    public void update() {
        if(composite.getComponents().size() > 0)
            composite.draw(this.drawbridge, this.get());
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

    public Composite getComposite(){
        return composite;
    }
    public void setComposite(Composite composite) {
        this.composite = composite;
    }

    public IShape getShape(Point p) {
        for (IShape s : composite.getComponents())
            if (s.isInside(p))
                return s;
        return null;
    }
    @Override
    public Object get() {
        return toolBar;
    }
    @Override
    public int getWidth() {
        return width;
    }
    @Override
    public int getHeight() {
        return height;
    }

    public Point localPoint(double boundX, double boundY, double x, double y) {
        int newX = (int)(x - boundX);
        int newY = (int)(y - boundY);
        return new Point(newX, newY);
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


}
