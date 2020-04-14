package editeur.view;

public class GenericToolBar {
    private Object toolBar;
    private final int width  = 150; 
    
    public GenericToolBar(Object toolBar) {
        this.toolBar = toolBar;
    }

    public Object get() {
        return toolBar;
    }

    public int getWidth() {
        return width;
    }


    
}
