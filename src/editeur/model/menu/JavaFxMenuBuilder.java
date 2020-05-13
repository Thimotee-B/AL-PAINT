package editeur.model.menu;

import editeur.controller.Mediator;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.menu.EditMenu.EditMenuBuilder;
import editeur.model.menu.EditMenu.EditMenuBuilderJavaFx;
import editeur.view.GUIFactory.GenericViewElements.GenericWhiteBoard;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.util.Vector;

public class JavaFxMenuBuilder implements MenuBuilder {
    static MenuBuilder     instance;
    ContextMenu    MainMenu;
    GenericWhiteBoard  whiteboard;
    Vector<IShape> selectedShapes;
    IShape         clickedShape;
    static private boolean displayed = false, builded = false;

    private JavaFxMenuBuilder() {}

    static public MenuBuilder getInstance() {
        if (instance == null)
            instance = new JavaFxMenuBuilder();
        return instance;
    }

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
                        this.debuild();
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
                    this.debuild();
                    e.consume();
                }
        );
        this.MainMenu.getItems().add(ungroup);
    }




    @Override
    public void buildEditItem() {
        if (clickedShape == null) return;

        MenuItem edit           = new MenuItem("Edit");
        EditMenuBuilder builder = new EditMenuBuilderJavaFx(whiteboard, clickedShape);
        edit.setOnAction(
                e -> {
                        builder.buildDialog();
                        builder.buildColorPicker();
                        builder.buildPositionPicker();
                        builder.buildRotationPicker();
                        builder.buildScalePicker();
                        builder.buildRoundPicker();
                        builder.buildDialogContent();
                        builder.buildDialogButtons();
                        builder.buildResult();
                        this.debuild();
                        e.consume();
                    }
        );
        this.MainMenu.getItems().add(edit);


    }

    @Override
    public void buildResult(Vector<IShape> selectedShapes, IShape clickedShape, GenericWhiteBoard whiteBoard, int x, int y) {
        if (!builded) {
            this.buildMainMenu(selectedShapes, clickedShape, whiteBoard);
            this.buildGroupItem();
            this.buildUngroupItem();
            this.buildEditItem();
            builded = true;
        }
        if (!displayed) {
            StackPane w = (StackPane) whiteboard.get();
            MainMenu.show(w, x, y);
            displayed = true;
        }
    }

    @Override
    public void debuild() {
        MainMenu.hide();
        displayed   = false;
        builded     = false;

    }
}
