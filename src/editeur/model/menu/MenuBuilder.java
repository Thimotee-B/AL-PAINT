package editeur.model.menu;

import editeur.model.geometry.IShape;
import editeur.view.GUIFactory.GenericViewElements.GenericWhiteBoard;
import java.util.Vector;

/**
 * The interface MenuBuilder defines which steps we need to build a menu.
 * Can be implemented in multiples representations.
 */
public interface MenuBuilder {

    /**
     * Build main menu and indicate which shapes are selected or clicked.
     *
     * @param selectedShapes the selected shapes
     * @param clickedShape   the clicked shape
     * @param whiteboard     the whiteboard
     */
    void buildMainMenu(Vector<IShape> selectedShapes, IShape clickedShape, GenericWhiteBoard whiteboard);

    /**
     * Build group item.
     */
    void buildGroupItem();

    /**
     * Build ungroup item.
     */
    void buildUngroupItem();

    /**
     * Build edit item.
     */
    void buildEditItem();

    /**
     * Build result menu.
     *
     * @param selectedShapes the selected shapes
     * @param clickedShape   the clicked shape
     * @param whiteBoard     the white board
     * @param x              the x
     * @param y              the y
     */
    void buildResult(Vector<IShape> selectedShapes, IShape clickedShape, GenericWhiteBoard whiteBoard, int x, int y);

    /**
     * Debuild.
     */
    void debuild();
}
