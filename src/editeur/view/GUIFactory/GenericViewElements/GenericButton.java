package editeur.view.GUIFactory.GenericViewElements;

/**
 * The type GenericButton.
 */
public class GenericButton implements IGeneric {
    private final Object button;
    private int width  = 20;
    private int height = 40;

    /**
     * Instantiates a new Generic button.
     *
     * @param button the button
     */
    public GenericButton(Object button) {
        this.button = button;
    }

    /**
     * Get object.
     *
     * @return the object
     */
    @Override
    public Object get() {
        return button;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
