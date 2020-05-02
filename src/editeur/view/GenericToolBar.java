package editeur.view;

import java.util.Vector;

import editeur.model.geometry.IShape;

public class GenericToolBar {
    private Object toolBar;
    private final int width  = 150; 
    private Vector<IShape> shapeVector;
    
    public GenericToolBar(Object toolBar) {
        this.toolBar = toolBar;
        this.shapeVector = new Vector<IShape>();
    }
    
    public void addShape(IShape shape) {
        shapeVector.add(shape);
    }
    
    public void removeShape(IShape shape) {
        shapeVector.remove(shape);
    }
    
    public IShape getShape(int index) {
        if(index>=0)
            return shapeVector.get(index);
        return null;
    }
    
    public Object get() {
        return toolBar;
    }

    public int getWidth() {
        return width;
    }
    


    
}
