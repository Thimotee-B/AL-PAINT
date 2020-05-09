package editeur.model.menu;

import editeur.controller.Mediator;
import editeur.model.geometry.IShape;
import editeur.view.GenericWhiteBoard;

import java.util.Vector;

public class Menu implements IMenu{
    Vector<IShape> selectedShapes;
    IShape         clickedShape;
    GenericWhiteBoard whiteBoard;

    public Menu(Vector<IShape> selectedShapes, IShape clickedShape, GenericWhiteBoard whiteBoard){
        this.selectedShapes = selectedShapes;
        this.whiteBoard =  whiteBoard;
        this.clickedShape = clickedShape;

    }

    @Override
    public void showMenu(MenuBuilder menu,int x ,int y) {
        menu.buildMainMenu(selectedShapes, clickedShape, whiteBoard);
        menu.buildGroupItem();
        menu.buildUngroupItem();
        menu.buildEditItem();
        menu.buildResult(x, y);

    }

    @Override
    public void unshowMenu() {
        return;
    }
}
