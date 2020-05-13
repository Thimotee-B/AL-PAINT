package editeur.model.menu;

import editeur.model.geometry.IShape;
import editeur.view.GUIFactory.GenericViewElements.GenericWhiteBoard;
import java.util.Vector;

public class Menu implements IMenu{
    Vector<IShape>    selectedShapes;
    IShape            clickedShape;
    GenericWhiteBoard whiteBoard;


    public Menu(Vector<IShape> selectedShapes, IShape clickedShape, GenericWhiteBoard whiteBoard){
        this.selectedShapes = selectedShapes;
        this.whiteBoard     =  whiteBoard;
        this.clickedShape   = clickedShape;
    }

    @Override
    public void showMenu(MenuBuilder menu,int x ,int y) {
        menu.buildResult(this.selectedShapes, this.clickedShape, this.whiteBoard, x, y);
    }

    @Override
    public void unshowMenu(MenuBuilder menu) {
        menu.debuild();
    }
}
