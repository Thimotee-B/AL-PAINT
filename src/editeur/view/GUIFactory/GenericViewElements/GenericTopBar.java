package editeur.view.GUIFactory.GenericViewElements;

public class GenericTopBar implements IGeneric {
    private final Object topBar;
    
    private final int width  = 100;
    private final int height = 100;
    
    public GenericTopBar(Object topBar) {
        this.topBar = topBar;
    }
    @Override
    public Object get() {
        return topBar;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
