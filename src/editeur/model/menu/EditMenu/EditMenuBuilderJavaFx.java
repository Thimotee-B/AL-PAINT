package editeur.model.menu.EditMenu;

import editeur.controller.Mediator;
import editeur.model.geometry.IShape;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;



public class EditMenuBuilderJavaFx implements EditMenuBuilder {
    ColorPicker chooser;
    Dialog<ButtonType> dialog;

    @Override
    public void buildDialog() {
         dialog = new Dialog<>();
         dialog.setTitle("Edition Menu");
         dialog.setResizable(true);
         dialog.setHeaderText("Please choose your modification on this shape and apply");

    }

    @Override
    public void buildColorPicker(IShape toEdit) {
        chooser = new ColorPicker(
                Color.rgb(toEdit.getColorR(),
                          toEdit.getColorG(),
                          toEdit.getColorB(),
                          toEdit.getAlpha()));
        chooser.setOnAction(
                e -> {
                    Color choice = chooser.getValue();
                    Mediator.getInstance().reColor(toEdit,
                               (int) (choice.getRed() * 255),
                               (int) (choice.getGreen() * 255),
                               (int) (choice.getBlue() * 255));

                }
        );
    }

    @Override
    public void buildDialogContent() {
        GridPane g = new GridPane();
        g.add(chooser, 1 , 1);
        dialog.getDialogPane().setContent(g);
    }

    @Override
    public void buildDialogButtons() {
        ButtonType apply, cancel, ok;
        apply  = new ButtonType("Apply",  ButtonBar.ButtonData.APPLY);
        cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ok     = new ButtonType("Ok",     ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(apply, ok , cancel);

    }

    public void buildResult(){
        dialog.showAndWait();
    }

}
