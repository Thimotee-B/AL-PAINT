package editeur.view.GUIFactory.GenericViewElements;

/**
 * The type GenericTopBar.
 */
public class GenericTopBar implements IGeneric {
    private final Object topBar;
    private final int width  = 100;
    private final int height = 100;

    /**
     * Instantiates a new Generic top bar.
     *
     * @param topBar the top bar
     */
    public GenericTopBar(Object topBar) {
        this.topBar = topBar;
    }

    /**
     * Get object.
     *
     * @return the object
     */
    @Override
    public Object get() {
        return topBar;
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
     * Gets height.
     *
     * @return the height
     */
    @Override
    public int getHeight() {
        return height;
    }
}
