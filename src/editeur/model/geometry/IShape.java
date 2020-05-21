package editeur.model.geometry;

import java.io.Serializable;

import editeur.model.draw.Drawable;
import editeur.model.geometry.base.Point;
import editeur.model.geometry.memento.Originator;

/**
 * The interface IShape is cloneable and represent the abstraction of a shape.
 * Can be saved and restore with Memento Pattern (Originator).
 * Is Drawable .
 */
public interface IShape extends Cloneable, Originator, Serializable, Drawable {

    /**
     * Gets color r.
     *
     * @return the color r
     */
    int getColorR();

    /**
     * Gets color g.
     *
     * @return the color g
     */
    int getColorG();

    /**
     * Gets color b.
     *
     * @return the color b
     */
    int getColorB();

    /**
     * Change color.
     *
     * @param r the r
     * @param g the g
     * @param b the b
     */
    void changeColor(int r, int g, int b);

    /**
     * Gets position.
     *
     * @return the position
     */
    Point getPosition();

    /**
     * Gets height.
     *
     * @return the height
     */
    int getHeight();

    /**
     * Gets width.
     *
     * @return the width
     */
    int getWidth();

    /**
     * Sets width.
     *
     * @param width the width
     */
    void setWidth(int width);

    /**
     * Sets height.
     *
     * @param height the height
     */
    void setHeight(int height);

    /**
     * Move to dx,dy position.
     *
     * @param dx the dx
     * @param dy the dy
     */
    void move(int dx, int dy);

    /**
     * Sets position.
     *
     * @param x the x
     * @param y the y
     */
    void setPosition(int x, int y);

    /**
     * Scale.
     *
     * @param factor the factor
     */
    void scale(double factor);

    /**
     * Sets alpha.
     *
     * @param alpha the alpha
     */
    void setAlpha(double alpha);

    /**
     * Gets alpha.
     *
     * @return the alpha
     */
    double getAlpha();

    /**
     * Gets rotation.
     *
     * @return the rotation
     */
    double getRotation();

    /**
     * Rotate.
     *
     * @param rotation the rotation
     */
    void rotate(double rotation);

    /**
     * Gets translation center.
     *
     * @return the translation center
     */
    Point getTranslationCenter();

    /**
     * Gets rotation center.
     *
     * @return the rotation center
     */
    Point getRotationCenter();

    /**
     * Sets rotation center.
     *
     * @param p the p
     */
    void setRotationCenter(Point p);

    /**
     * Clone shape.
     *
     * @return the shape
     */
    Shape clone();

    /**
     * Tell if point is inside this IShape .
     *
     * @param p the p
     * @return the boolean
     */
    boolean isInside(Point p);

}

