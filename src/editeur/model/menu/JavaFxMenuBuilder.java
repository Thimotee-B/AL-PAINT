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

/**
 * The type JavaFxMenuBuilder.
 */
public class JavaFxMenuBuilder implements MenuBuilder {
    static MenuBuilder     instance;
    ContextMenu    MainMenu;
    EditMenuBuilder builder;
    GenericWhiteBoard  whiteboard;
    Vector<IShape> selectedShapes;
    IShape         clickedShape;
    static private boolean displayed = false, builded = false;

    /**
     * Instantiates a new Java fx menu builder.
     */
    private JavaFxMenuBuilder() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    static public MenuBuilder getInstance() {
        if (instance == null)
            instance = new JavaFxMenuBuilder();
        return instance;
    }

    /**
     * Build main menu.
     *
     * @param selectedShapes the selected shapes
     * @param clickedShape   the clicked shape
     * @param whiteboard     the whiteboard
     */
    @Override
    public void buildMainMenu(Vector<IShape> selectedShapes,IShape clickedShape, GenericWhiteBoard whiteboard) {
        this.MainMenu            = new ContextMenu();
        this.selectedShapes       = selectedShapes;
        this.whiteboard = whiteboard;
        this.clickedShape = clickedShape;
    }


    /**
     * Build group item.
     */
    @Override
    public void buildGroupItem() {
        MenuItem group = new MenuItem("Group");
        group.setOnAction(
                e -> {
                        if(selectedShapes.size() > 1)
                            Mediator.getInstance().group(whiteboard.getComposite());
                        this.debuild();
                    e.consume();
                }
        );
        this.MainMenu.getItems().add(group);
    }

    /**
     * Build ungroup item.
     */
    @Override
    public void buildUngroupItem() {
        MenuItem ungroup = new MenuItem("Ungroup");
        ungroup.setOnAction(
                e -> {
                    if (clickedShape instanceof Composite) {
                        Composite c = (Composite) clickedShape;
                        Mediator.getInstance().unGroup(whiteboard.getComposite(), c);
                    }
                    this.debuild();
                    e.consume();
                }
        );
        this.MainMenu.getItems().add(ungroup);
    }


    /**
     * Build edit item.
     */
    @Override
    public void buildEditItem() {
        if (clickedShape == null) return;

        MenuItem edit           = new MenuItem("Edit");
        builder = new EditMenuBuilderJavaFx(whiteboard, clickedShape);
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

    /**
     * Build result menu.
     *
     * @param selectedShapes the selected shapes
     * @param clickedShape   the clicked shape
     * @param whiteBoard     the white board
     * @param x              the x
     * @param y              the y
     */
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

    /**
     * Debuild.
     */
    @Override
    public void debuild() {
        MainMenu.hide();
        displayed   = false;
        builded     = false;

    }
}
