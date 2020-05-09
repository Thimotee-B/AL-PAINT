package editeur.model.menu;

import editeur.controller.Mediator;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.menu.EditMenu.EditMenuBuilder;
import editeur.model.menu.EditMenu.EditMenuBuilderJavaFx;
import editeur.view.ApplicationFx;
import editeur.view.GenericWhiteBoard;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import javax.print.attribute.standard.Media;
import java.util.Optional;
import java.util.Stack;
import java.util.Vector;

public class JavaFxMenuBuilder implements MenuBuilder {

    ContextMenu    MainMenu;
    GenericWhiteBoard  whiteboard;
    Vector<IShape> selectedShapes;
    IShape         clickedShape;

    @Override
    public void buildMainMenu(Vector<IShape> selectedShapes,IShape clickedShape, GenericWhiteBoard whiteboard) {
        this.MainMenu            = new ContextMenu();
        this.selectedShapes       = selectedShapes;
        this.whiteboard = whiteboard;
        this.clickedShape = clickedShape;
    }


    @Override
    public void buildGroupItem() {
        MenuItem group = new MenuItem("Group");
        group.setOnAction(
                e -> {
                        if(selectedShapes.size() > 1)
                            Mediator.getInstance().group(whiteboard.getShapeVector());
                    e.consume();
                }
        );
        this.MainMenu.getItems().add(group);
    }

    @Override
    public void buildUngroupItem() {
        MenuItem ungroup = new MenuItem("Ungroup");
        ungroup.setOnAction(
                e -> {
                    if (clickedShape instanceof Composite) {
                        Composite c = (Composite) clickedShape;
                        Mediator.getInstance().unGroup(whiteboard.getShapeVector(), c);
                    }
                    e.consume();
                }
        );
        this.MainMenu.getItems().add(ungroup);
    }




    @Override
    public void buildEditItem() {
        MenuItem edit           = new MenuItem("Edit");
        EditMenuBuilder builder = new EditMenuBuilderJavaFx();
        edit.setOnAction(
                e -> {
                        builder.buildDialog();
                        builder.buildColorPicker(clickedShape);
                        builder.buildPositionPicker(clickedShape);
                        builder.buildDialogContent();
                        builder.buildDialogButtons();
                        builder.buildResult(clickedShape);
                        e.consume();
                    }
        );
        this.MainMenu.getItems().add(edit);


    }

    @Override
    public void buildResult(int x, int y) {
        StackPane w = (StackPane) whiteboard.get();
        MainMenu.show(w, x, y);
    }
}
