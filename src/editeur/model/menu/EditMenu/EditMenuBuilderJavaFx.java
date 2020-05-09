package editeur.model.menu.EditMenu;

import editeur.controller.Mediator;
import editeur.model.geometry.IShape;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;




public class EditMenuBuilderJavaFx implements EditMenuBuilder {
    ColorPicker chooser = null;
    Dialog<ButtonType> dialog;
    TextField x = null, y = null;
    ButtonType apply, cancel, ok;
    int undoCount = 0;

    int r = -1, g = -1, b = -1;
    int posX = -1 , posY = -1;

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

                    r = (int) (choice.getRed() * 255);
                    g = (int) (choice.getGreen() * 255);
                    b = (int) (choice.getBlue() * 255);

                }
        );
    }

    @Override
    public void buildPositionPicker(IShape toEdit) {
        x = new TextField();
        y = new TextField();

        x.setMinSize(140, 20);
        x.setMaxSize(140, 20);
        y.setMinSize(140, 20);
        y.setMaxSize(140, 20);

    }


    @Override
    public void buildDialogContent() {
        GridPane g = new GridPane();
        if (chooser != null) {
            g.add(new Label("Color "), 1, 1);
            g.add(chooser, 2, 1);
        }
        if (x != null) {
            g.add(new Label("    X "), 1, 2);
            g.add(x, 2, 2);
        }
        if (y != null) {
            g.add(new Label("    Y "), 1, 3);
            g.add(y, 2, 3);
        }

        dialog.getDialogPane().setContent(g);
    }

    @Override
    public void buildDialogButtons() {
        apply = new ButtonType("Apply", ButtonBar.ButtonData.APPLY);
        cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(apply, ok, cancel);


    }

    public void buildResult(IShape toEdit) {

        dialog.showAndWait().ifPresent(response -> {
            if (response == ok) {
                System.out.println("cc");
            }
            if (response == apply){
                if (r != -1 || g != -1 || b != -1) {
                    Mediator.getInstance().reColor(toEdit, r, g, b);
                    undoCount++;
                }
                modifyPositionIfNeeded(toEdit);
                buildResult(toEdit);
            }
            if (response == cancel){
                for (int i = 0; i < undoCount ; i++)
                    Mediator.getInstance().undo();
            }


        });


    }

    private void modifyPositionIfNeeded(IShape toEdit){
        undoCount++;
        posX = Integer.parseInt(x.getText());
        posY = Integer.parseInt(y.getText());
        if (posX != -1 && posY != -1)
            Mediator.getInstance().move(toEdit, posX, posY);
        else if (posX != -1 && posY == -1)
            Mediator.getInstance().move(toEdit, posX, toEdit.getPosition().getY());
        else if (posX == -1 && posY != -1)
            Mediator.getInstance().move(toEdit,toEdit.getPosition().getX(), posY);
        else
          undoCount--;
    }
}


