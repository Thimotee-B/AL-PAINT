package editeur.model.menu;

/**
 * The interface IMenu.
 */
public interface IMenu {

    /**
     * Show menu once Build with builder.
     * Can have multiple representation.
     *
     * @param builder the builder
     * @param x       the x
     * @param y       the y
     */
    void showMenu(MenuBuilder builder, int x, int y);

    /**
     * Unshow menu previously build.
     *
     * @param builder the builder
     */
    void unshowMenu(MenuBuilder builder);

}
