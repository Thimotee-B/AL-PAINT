package editeur.model.draw;

/**
 * The interface Drawable indicates who is able to be draw.
 */
public interface Drawable {

    /**
     * Draw the object who implements Drawable on DrawSurface.
     *
     * @param drawbridge  the drawbridge
     * @param DrawSurface the draw surface
     */
    void draw(DrawBridge drawbridge, Object DrawSurface);
	
}
