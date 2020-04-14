package editeur.view;

public class GenericWhiteBoard {
    private Object whiteBoard;
    
    private final int width  = 1000;
    private final int height = 900;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GenericWhiteBoard(Object whiteBoard) {
        this.whiteBoard = whiteBoard;
    }

    public Object get() {
        return whiteBoard;
    }
    
    
    
    
}
