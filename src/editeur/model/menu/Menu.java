package editeur.model.menu;

import editeur.controller.Mediator;
import editeur.model.geometry.IShape;
import editeur.view.GenericWhiteBoard;
import javafx.scene.control.ContextMenu;

import java.util.Vector;

public class Menu implements IMenu{
    Vector<IShape> selectedShapes;
    IShape         clickedShape;
    GenericWhiteBoard whiteBoard;
    ContextMenu         contextMenu;

    public Menu(Vector<IShape> selectedShapes, IShape clickedShape, GenericWhiteBoard whiteBoard){
        this.selectedShapes = selectedShapes;
        this.whiteBoard =  whiteBoard;
        this.clickedShape = clickedShape;
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
