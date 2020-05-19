package editeur.model.draw;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Rectangle;
import editeur.model.geometry.base.SimplePolygon;



public interface DrawBridge {

    /**
     *
     * @param drawSurface
     * @param selection
     */
    void drawSelection(Object drawSurface, Rectangle selection);

    /**
     *
     * @param toDraw
     * @param r
     */
    void drawRectangle(Object toDraw, Rectangle r);

    /**
     *
     * @param drawSurface
     * @param r
     */
    void fillRectangle(Object drawSurface, Rectangle r);

    /**
     *
     * @param toDraw
     * @param p
     */
    void drawPolygon(Object toDraw, SimplePolygon p);

    /**
     *
     * @param drawSurface
     * @param p
     */
    void fillPolygon(Object drawSurface,SimplePolygon p);

    /**
     *
     * @param whiteboard
     * @param toolbar
     * @param s
     */
    void clearView(Object whiteboard, Object toolbar, IShape s);

    /**
     *
     * @param whiteboard
     * @param toolbar
     */
    void FullClearView(Object whiteboard, Object toolbar);

}
