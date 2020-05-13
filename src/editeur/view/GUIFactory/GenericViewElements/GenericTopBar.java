package editeur.view.GUIFactory.GenericViewElements;

public class GenericTopBar {
    private final Object topBar;
    
    private final int width = 100;
    
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
