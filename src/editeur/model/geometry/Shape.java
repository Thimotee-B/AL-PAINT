package editeur.model.geometry;



import editeur.model.geometry.base.Point;
import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.MementoShape;

import java.util.Objects;

/**
 * The abstract class shape defined all the basics operation of a shape.
 */
public abstract class Shape implements IShape {

    private static final long serialVersionUID = -6832490922368244161L;
    private Point position;
    private double rotation;
    private Point rotationCenter;
    private Point translationCenter;
    private int r, g, b;
    private double alpha;
    private int width, height;


    /**
     * Instantiates a new Shape.
     *
     * @param x                 the x
     * @param y                 the y
     * @param rotationCenter    the rotation center
     * @param translationCenter the translation center
     * @param width             the width
     * @param height            the height
     */
    public Shape(int x, int y, Point rotationCenter, Point translationCenter, int width, int height) {
        this.position          = new Point(x, y);
        this.translationCenter = translationCenter;
        this.rotationCenter    = rotationCenter;
        
        this.rotation = 0.0;
        
        this.r     = 0;
        this.g     = 0;
        this.b     = 0;
        this.alpha = 1;
        this.width = width;
        this.height = height;
        
    }

    /**
     * Change color.
     *
     * @param r the r
     * @param g the g
     * @param b the b
     */
    @Override
    public void changeColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Move the shape to x,y
     *
     * @param x the x
     * @param y the y
     */
    @Override
    public void move(int x, int y) {
        setPosition(x, y);

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
     * Sets width.
     *
     * @param width the width
     */
    @Override
    public void setWidth(int width) {
        this.width = width;
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
     * Sets height.
     *
     * @param height the height
     */
    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Scale.
     *
     * @param factor the factor
     */
    @Override
    public abstract void scale(double factor);

    /**
     * Gets position.
     *
     * @return the position
     */
    @Override
    public Point getPosition() {
        return position;
    }

    /**
     * Sets position.
     *
     * @param x the x
     * @param y the y
     */
    @Override
    public void setPosition(int x, int y) {
        this.position = new Point(x,y);
    }

    /**
     * Gets translation center.
     *
     * @return the translation center
     */
    @Override
    public Point getTranslationCenter() {
        return this.translationCenter;
    }

    /**
     * Set rotation center.
     *
     * @param p the p
     */
    @Override
    public void setRotationCenter(Point p){
        this.rotationCenter = p;
    }

    /**
     * Gets rotation.
     *
     * @return the rotation
     */
    @Override
    public double getRotation() {
        return rotation;
    }


    /**
     * Rotate the shape of rotation angle.
     *
     * @param rotation the rotation
     */
    @Override
    public void rotate(double rotation) {
        this.rotation = rotation;
    }

    /**
     * Gets rotation center.
     *
     * @return the rotation center
     */
    @Override
    public Point getRotationCenter() {
        return rotationCenter;
    }

    /**
     * Gets color r.
     *
     * @return the color r
     */
    @Override
    public int getColorR() {
        return this.r;
    }

    /**
     * Gets color g.
     *
     * @return the color g
     */
    @Override
    public int getColorG() {
        return this.g;
    }

    /**
     * Gets color b.
     *
     * @return the color b
     */
    @Override
    public int getColorB() {
        return this.b;
    }

    /**
     * Sets alpha.
     *
     * @param alpha the alpha
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }


    /**
     * Is inside boolean.
     *
     * @param p the p
     * @return the boolean
     */
    @Override
    public abstract boolean isInside(Point p);

    /**
     * Gets alpha.
     *
     * @return the alpha
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Clone shape.
     *
     * @return the shape
     */
    @Override
    public Shape clone() {
        Shape clone = null;
        try {
            clone = (Shape) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            //catch to do
        }
        return null;
    }

    /**
     * Save the current state of this shape in a memento.
     *
     * @param memento the memento
     */
    public void save(MementoShape memento) {
    	memento.set(memento, position, rotationCenter, translationCenter, rotation, r, g, b, alpha, width, height);

    }

    /**
     * Restore the previous saved state of this shape in a memento.
     *
     * @param memento the memento
     */
    public void restore(MementoShape memento){
        this.position          = new Point(memento.getPosition().getX(), memento.getPosition().getY());
        this.translationCenter = new Point(memento.getTranslationCenter().getX(), memento.getTranslationCenter().getY());
        this.rotationCenter    = new Point(memento.getRotationCenter().getX(), memento.getRotationCenter().getY());
        
        this.rotation = memento.getRotation();
        
        this.r     = memento.getR();
        this.g     = memento.getG();
        this.b     = memento.getB();
        this.alpha = memento.getAlpha();

        this.height= memento.getHeight();
        this.width = memento.getWidth();

    }

    /**
     * Translate.
     *
     * @param dx the dx
     * @param dy the dy
     */
    public void translate(int dx, int dy) {
        this.position          = new Point(position.getX() + dx, position.getY() + dy);
        this.rotationCenter    = new Point(this.rotationCenter.getX() + dx, this.rotationCenter.getY() + dy);
        this.translationCenter = new Point(this.translationCenter.getX() + dx, this.translationCenter.getY() + dy);
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
        return Double.compare(shape.rotation, rotation) == 0 &&
                r == shape.r &&
                g == shape.g &&
                b == shape.b &&
                Double.compare(shape.alpha, alpha) == 0 &&
                width == shape.width &&
                height == shape.height &&
                position.equals(shape.position) &&
                rotationCenter.equals(shape.rotationCenter) &&
                translationCenter.equals(shape.translationCenter);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(position, rotation, rotationCenter, translationCenter, r, g, b, alpha, width, height);
    }
}
