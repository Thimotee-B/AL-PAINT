package editeur.model.menu;

import editeur.model.geometry.IShape;
import editeur.view.GenericWhiteBoard;


import java.util.Vector;

public interface MenuBuilder {

    void buildMainMenu(Vector<IShape> selectedShapes, IShape clickedShape, GenericWhiteBoard whiteboard);

    void buildGroupItem();

    void buildUngroupItem();

    void buildEditItem();

    void buildResult(Vector<IShape> selectedShapes, IShape clickedShape, GenericWhiteBoard whiteBoard, int x, int y);

    void debuild();
}
