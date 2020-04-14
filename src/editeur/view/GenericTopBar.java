package editeur.view;

public class GenericTopBar {
    private Object topBar;
    
    private int width = 100;
    
    public GenericTopBar(Object topBar) {
        this.topBar = topBar;
    }
    
    public Object get() {
        return topBar;
    }
    
    public int getWidth() {
        return width;
    }
}
