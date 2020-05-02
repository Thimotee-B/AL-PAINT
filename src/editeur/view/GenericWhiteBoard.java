package editeur.view;

import java.util.Vector;

import editeur.model.geometry.IShape;

public class GenericWhiteBoard {
    private Object whiteBoard;
    private Vector<IShape> shapeVector;
    
    private final int width  = 1000;
    private final int height = 900;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GenericWhiteBoard(Object whiteBoard) {
        this.whiteBoard  = whiteBoard;
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
        return whiteBoard;
    }
    
    
    
    
}
