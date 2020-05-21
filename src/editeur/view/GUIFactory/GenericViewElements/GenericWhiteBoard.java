package editeur.view.GUIFactory.GenericViewElements;


import editeur.model.draw.DrawBridge;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;
import editeur.view.GraphicalObjectObserver;

/**
 * The type GenericWhiteBoard.
 */
public class GenericWhiteBoard implements GraphicalObjectObserver, IGeneric {
    private final Object     whiteBoard;
    private Composite composite;
    private DrawBridge drawbridge;
    private final int width  = 1000;
    private final int height = 900;

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
     * Instantiates a new Generic white board.
     *
     * @param whiteBoard the white board
     */
    public GenericWhiteBoard(Object whiteBoard) {
        this.whiteBoard  = whiteBoard;
        this.composite = new Composite(0,0,0,0);
    }

    /**
     * Update and draw the whiteboard shapes.
     */
    @Override
    public void update() {
        if(composite.getComponents().size() > 0)
            composite.draw(this.drawbridge, this.get());
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
     * Add shape on whiteboard composite.
     *
     * @param shape the shape
     */
    public void addShape(IShape shape) {
        composite.add(shape);
    }

    /**
     * Remove shape on whiteboard composite.
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
     * Return point in local space of this object.
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
     * Tell if In whiteboard .
     *
     * @param point the point
     * @return the boolean
     */
    public boolean inWhiteBoard(Point point) {
        if (point == null) return false;
        return width >= point.getX() && height >= point.getY();
    }

    /**
     * Get object.
     *
     * @return the object
     */
    @Override
    public Object get() {
        return whiteBoard;
    }

    /**
     * Set draw bridge.
     *
     * @param db the db
     */
    public void setDrawBridge (DrawBridge db){
        this.drawbridge = db;
    }


}
