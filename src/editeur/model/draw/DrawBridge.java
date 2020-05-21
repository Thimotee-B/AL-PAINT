package editeur.model.draw;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Rectangle;
import editeur.model.geometry.base.SimplePolygon;


/**
 * The interface DrawBridge defines the draw operations available.
 * AbstractApplication Delegates the drawing to this bridge.
 */
public interface DrawBridge {

    /**
     * Draw the current selection on drawSurface.
     *
     * @param drawSurface the draw surface
     * @param selection   the selection
     */
    void drawSelection(Object drawSurface, Rectangle selection);

    /**
     * Simply Draw a rectangle on the drawSurface if possible.
     *
     * @param drawSurface the draw surface.
     * @param r      the r
     *
     */
    void drawRectangle(Object drawSurface, Rectangle r);

    /**
     * Fill the rectangle on drawSurface.
     *
     * @param drawSurface the draw surface
     * @param r           the r
     */
    void fillRectangle(Object drawSurface, Rectangle r);

    /**
     * Simpy Draw a polygon on the draw surface if possible.
     *
     * @param drawSurface the draw surface
     * @param p      the p
     */
    void drawPolygon(Object drawSurface, SimplePolygon p);

    /**
     * Fill polygon on drawSurface.
     *
     * @param drawSurface the draw surface
     * @param p           the p
     */
    void fillPolygon(Object drawSurface,SimplePolygon p);

    /**
     * Clear view of the IShape object.
     *
     * @param whiteboard the whiteboard
     * @param toolbar    the toolbar
     * @param s          the s
     */
    void clearView(Object whiteboard, Object toolbar, IShape s);

    /**
     * Full clear view.
     *
     * @param whiteboard the whiteboard
     * @param toolbar    the toolbar
     */
    void FullClearView(Object whiteboard, Object toolbar);

}
