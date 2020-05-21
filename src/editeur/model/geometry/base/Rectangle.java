package editeur.model.geometry.base;

import editeur.model.draw.DrawBridge;
import editeur.model.geometry.Shape;
import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.MementoRectangle;
import editeur.model.geometry.memento.MementoShape;

/**
 * The type Rectangle.
 */
public class Rectangle extends Shape{

    private static final long serialVersionUID = 6361385559811152501L;
    private int roundHeight, roundWidth;


    /**
     * Instantiates a new Rectangle.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param length the length
     */
    public Rectangle(int x, int y, int width, int length){
        super(x,
              y,
              new Point((x + x+width)/2, (y + y+length)/2),
              new Point((x + x+width)/2, (y + y+length)/2),
              width,
              length
            );

    }

    /**
     * Gets round height.
     *
     * @return the round height
     */
    public int getRoundHeight() {
        return roundHeight;
    }


    /**
     * Sets round height.
     *
     * @param roundHeight the round height
     */
    public void setRoundHeight(int roundHeight) {
        this.roundHeight = roundHeight;
    }


    /**
     * Gets round width.
     *
     * @return the round width
     */
    public int getRoundWidth() {
        return roundWidth;
    }


    /**
     * Sets round width.
     *
     * @param roundWidth the round width
     */
    public void setRoundWidth(int roundWidth) {
        this.roundWidth = roundWidth;
    }


    /**
     * Move to newx, newy position.
     *
     * @param newx the newx
     * @param newy the newy
     */
    @Override
    public void move(int newx, int newy){
        super.move(newx, newy);
        setRotationCenter(new Point((newx + newx + getWidth())/2 , (newy + newy + getHeight()) /2));
    }

    /**
     * Scale the rectangle of factor times.
     *
     * @param factor the factor
     */
    @Override
    public void scale(double factor){
        this.setHeight((int) (this.getHeight() * factor));
        this.setWidth ((int) (this.getWidth()  * factor));
    }


    /**
     * Save the current state in memento.
     *
     * @return the memento
     */
    @Override
    public Memento save(){
        MementoRectangle m = new MementoRectangle(roundHeight, roundWidth);
        super.save(m);
        return m;
    }

    /**
     * Restore the saved state from memento.
     *
     * @param memento the memento
     */
    @Override
    public void restore(Memento memento){
    	this.restoreShape((MementoShape) memento);
    }

    /**
     * Private Helper to restore the saved state from memento.
     *
     * @param mementoShape the memento shape
     */
    private void restoreShape(MementoShape mementoShape) {

    	super.restore(mementoShape);

    	MementoRectangle m = (MementoRectangle) mementoShape;

    	this.roundHeight = m.getRoundHeight();
    	this.roundWidth  = m.getRoundWidth();
    }

    /**
     * Clone shape.
     *
     * @return the shape
     */
    @Override
    public Shape clone(){
        Shape clone = super.clone();
        return clone;
    }

    /**
     * Tell if the point is inside the rectangle.
     *
     * @param p the p
     * @return the boolean
     */
    @Override
    public boolean isInside(Point p){
        Point p2 = this.getPosition();
        if (p.getX() >= p2.getX() && p.getX() <= p2.getX() + getWidth())
            return (p.getY() >= p2.getY() && p.getY() <= p2.getY() + getHeight());
        return false;
    }

    /**
     * Draw the rectangle on draw surface.
     *
     * @param db          the db
     * @param drawSurface the draw surface
     */
    @Override
	public void draw(DrawBridge db, Object drawSurface) {
        db.drawRectangle(drawSurface, this);
	}



}
