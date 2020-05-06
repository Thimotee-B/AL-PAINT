package editeur.model.draw;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Rectangle;
import editeur.model.geometry.base.SimplePolygon;

import java.awt.*;

public interface DrawBridge {
	//Draw poylgon,rectangles,lignes etc 
    
    void drawRectangle(Object toDraw, Rectangle r);
    
    void fillRectangle(Object drawSurface, Rectangle r);

    void drawPolygon(Object toDraw, SimplePolygon p);

    void fillPolygon(Object drawSurface,SimplePolygon p);

    void clearView(Object whiteboard, Object toolbar, IShape s);

    void FullClearView(Object whiteboard, Object toolbar);

}
