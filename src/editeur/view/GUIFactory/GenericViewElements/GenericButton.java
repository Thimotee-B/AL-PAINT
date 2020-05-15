package editeur.view.GUIFactory.GenericViewElements;

public class GenericButton implements IGeneric {
    private final Object button;

    private int width  = 20;
    private int height = 40;

    public GenericButton(Object button) {
        this.button = button;
    }
    @Override
    public Object get() {
        return button;
    }
    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
