package editeur.model.draw;
import editeur.model.geometry.base.Rectangle;
public interface DrawBuilder {
	//Draw poylgon,rectangles,lignes etc 
    
    void drawRectangle(Object todraw, Rectangle r);
    
    void fillRectangle(Object drawSurface, Rectangle r);
}
