package editeur.view.GUIFactory.GenericViewElements;

public class GenericButton {
    private final Object button;

    private int width  = 20;
    private int height = 40;

    public GenericButton(Object button) {
        this.button = button;
    }

    public Object get() {
        return button;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
