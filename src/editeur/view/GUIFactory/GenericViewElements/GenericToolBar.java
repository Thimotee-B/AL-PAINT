package editeur.view.GUIFactory.GenericViewElements;

import editeur.model.draw.DrawBridge;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;
import editeur.view.GraphicalObjectObserver;

/**
 * The type GenericToolBar.
 */
public class GenericToolBar implements GraphicalObjectObserver, IGeneric {
    private final int width  = 150;
    private final int height = 900;
    private final int toolMaxSize = 75;
    private final Object toolBar;
    private Composite composite;
    private DrawBridge drawbridge;


    /**
     * Instantiates a new Generic tool bar.
     *
     * @param toolBar the tool bar
     */
    public GenericToolBar(Object toolBar) {
        this.toolBar = toolBar;
        this.composite = new Composite(0,0,0,0);
    }

    /**
     * Update and draw all the shapes contains in toolbar.
     */
    @Override
    public void update() {
        if(composite.getComponents().size() > 0)
            composite.draw(this.drawbridge, this.get());
    }

    /**
     * Add shape to composite of toolbar.
     *
     * @param shape the shape
     */
    public void addShape(IShape shape) {
        composite.add(shape);
    }

    /**
     * Remove shape of composite of toolbar.
     *
     * @param shape the shape
     */
    public void removeShape(IShape shape) {
        composite.remove(shape);
    }

    /**
     * Gets shape.
     *
     * @param index the index
     * @return the shape
     */
    public IShape getShape(int index) {
        if(index>=0)
            return composite.getComponents().get(index);
        return null;
    }

    /**
     * Get composite composite.
     *
     * @return the composite
     */
    public Composite getComposite(){
        return composite;
    }

    /**
     * Sets composite.
     *
     * @param composite the composite
     */
    public void setComposite(Composite composite) {
        this.composite = composite;
    }

    /**
     * Gets shape.
     *
     * @param p the p
     * @return the shape
     */
    public IShape getShape(Point p) {
        for (IShape s : composite.getComponents())
            if (s.isInside(p))
                return s;
        return null;
    }

    /**
     * Get object.
     *
     * @return the object
     */
    @Override
    public Object get() {
        return toolBar;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Returns the point in the local space of this object.
     *
     * @param boundX the bound x
     * @param boundY the bound y
     * @param x      the x
     * @param y      the y
     * @return the point
     */
    public Point localPoint(double boundX, double boundY, double x, double y) {
        int newX = (int)(x - boundX);
        int newY = (int)(y - boundY);
        return new Point(newX, newY);
    }

    /**
     * Tell if in toolbar.
     *
     * @param p the p
     * @return the boolean
     */
    public boolean inToolBar(Point p) {
        if (p == null) return false;
        return width >= p.getX() && height >= p.getY();
     }

    /**
     * Set draw bridge.
     *
     * @param db the db
     */
    public void setDrawBridge (DrawBridge db){
        this.drawbridge = db;
    }

    /**
     * Get tool max size int.
     *
     * @return the int
     */
    public int getToolMaxSize(){
        return toolMaxSize;
    }


}
