package editeur.model.draw;
import java.util.HashMap;

import editeur.model.geometry.base.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class JavaFxDrawBridge implements DrawBridge {
    private HashMap<Rectangle,javafx.scene.shape.Rectangle> map = 
            new HashMap<Rectangle,javafx.scene.shape.Rectangle>();
    @Override
    public void drawRectangle(Object drawSurface, Rectangle r ) {
        javafx.scene.shape.Rectangle rect = this.map.get(r);
        if(rect == null) 
            rect = new javafx.scene.shape.Rectangle(r.getWidth(),r.getHeight());

        rect.setX(r.getPosition().getX());
        rect.setY(r.getPosition().getY());

        rect.setWidth(r.getWidth());
        rect.setHeight(r.getHeight());
            
        rect.setArcHeight(r.getRoundHeight());
        rect.setArcWidth(r.getRoundWidth());
        
        rect.setTranslateX(r.getPosition().getX());
        rect.setTranslateY(r.getPosition().getY());
        
        Rotate rotation = new Rotate(r.getRotation(), 
                          r.getRotationCenter().getX(),
                          r.getRotationCenter().getY());
        
        rect.getTransforms().add(rotation);

        this.map.put(r, rect);
        this.fillRectangle(drawSurface, r);
        
    }
    
    @Override
    public void fillRectangle(Object drawSurface, Rectangle r) {
        javafx.scene.shape.Rectangle rect = this.map.get(r);
        rect.setFill(Color.rgb(r.getColorB(),r.getColorG(), r.getColorR(), r.getAlpha()));
        this.updateRect(drawSurface,rect);
    }
    
    private void updateRect(Object drawSurface, javafx.scene.shape.Rectangle rect) {
        ((Pane)drawSurface).getChildren().remove(rect);
        ((Pane)drawSurface).getChildren().add(rect);
    }

}
