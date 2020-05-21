package editeur.model.menu;

import editeur.model.geometry.IShape;
import editeur.view.GUIFactory.GenericViewElements.GenericWhiteBoard;
import java.util.Vector;

/**
 * The type Menu represents menu invoked when right click press.
 * His construction is delegated to builder.
 */
public class Menu implements IMenu{
    Vector<IShape>    selectedShapes;
    IShape            clickedShape;
    GenericWhiteBoard whiteBoard;


    /**
     * Instantiates a new Menu.
     *
     * @param selectedShapes the selected shapes
     * @param clickedShape   the clicked shape
     * @param whiteBoard     the white board
     */
    public Menu(Vector<IShape> selectedShapes, IShape clickedShape, GenericWhiteBoard whiteBoard){
        this.selectedShapes = selectedShapes;
        this.whiteBoard     =  whiteBoard;
        this.clickedShape   = clickedShape;
    }

    /**
     * Show menu once Build with builder.
     * Can have multiple representation.
     *
     * @param menu the builder
     * @param x       the x
     * @param y       the y
     */
    @Override
    public void showMenu(MenuBuilder menu,int x ,int y) {
        menu.buildResult(this.selectedShapes, this.clickedShape, this.whiteBoard, x, y);
    }

    /**
     * Unshow menu previously build.
     *
     * @param menu the builder
     */
    @Override
    public void unshowMenu(MenuBuilder menu) {
        menu.debuild();
    }
}
