package editeur.model.menu.EditMenu;

import editeur.controller.Mediator;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Rectangle;
import editeur.model.geometry.base.SimplePolygon;
import editeur.view.GenericWhiteBoard;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import javax.print.attribute.standard.Media;


public class EditMenuBuilderJavaFx implements EditMenuBuilder {

    GenericWhiteBoard whiteBoard;
    IShape toEdit;

    ColorPicker chooser = null;
    Dialog<ButtonType> dialog;

    TextField x      = null;
    TextField y      = null;
    TextField rotate = null;
    TextField scale  = null;
    TextField roundW = null;
    TextField roundH = null;
    ButtonType apply, cancel, ok;

    int undoCount = 0;

    int r = -1;
    int g = -1;
    int b = -1;

    int posX = -1;
    int posY = -1;

    double rotateValue = -1;
    double scaleValue  = -1;
    int    roundwidth  = -1;
    int    roundheight = -1;


    public EditMenuBuilderJavaFx(GenericWhiteBoard whiteBoard, IShape toEdit){
        this.whiteBoard = whiteBoard;
        this.toEdit     = toEdit;
    }


    @Override
    public void buildDialog() {
        dialog = new Dialog<>();
        dialog.setTitle("Edition Menu");
        dialog.setResizable(true);
        dialog.setHeaderText("Please choose your modification on this shape and apply");

    }

    @Override
    public void buildColorPicker() {
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
    public void buildPositionPicker() {
        x = new TextField();
        y = new TextField();

        x.setMinSize(140, 20);
        x.setMaxSize(140, 20);
        y.setMinSize(140, 20);
        y.setMaxSize(140, 20);

    }

    @Override
    public void buildRotationPicker() {
        rotate = new TextField();
        rotate.setMinSize(140, 20);
        rotate.setMaxSize(140, 20);
    }

    @Override
    public void buildScalePicker() {
        scale = new TextField();
        scale.setMinSize(140, 20);
        scale.setMaxSize(140, 20);
    }

    @Override
    public void buildRoundPicker() {
        roundW = new TextField();
        roundH = new TextField();

        roundW.setMinSize(140, 20);
        roundW.setMaxSize(140, 20);
        roundH.setMinSize(140, 20);
        roundH.setMaxSize(140, 20);
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
        if(rotate != null){
            g.add(new Label("Rotate "), 1, 4);
            g.add(rotate, 2, 4);
        }
        if(scale != null){
            g.add(new Label("Scale "), 1, 5);
            g.add(scale, 2, 5);
        }
        if(roundW != null && (toEdit instanceof Rectangle || isCompositeFullOfRectangle(toEdit))){
            g.add(new Label("RoundWidth "), 1, 6);
            g.add(roundW, 2, 6);
        }
        if(roundH != null && (toEdit instanceof Rectangle || isCompositeFullOfRectangle(toEdit))){
            g.add(new Label("RoundHeight "), 1, 7);
            g.add(roundH, 2, 7);
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

    public void buildResult() {

        dialog.showAndWait().ifPresent(response -> {
            if (response == ok) {
                applyOrOk();
                System.out.println("you did awesome :)");
            }
            if (response == apply){
                applyOrOk();
                buildResult();
            }
            if (response == cancel){
                for (int i = 0; i < undoCount ; i++)
                    Mediator.getInstance().undo();
            }
        });


    }

    private void applyOrOk(){
        if (r != -1 || g != -1 || b != -1) {
            Mediator.getInstance().reColor(toEdit, r, g, b);
            undoCount++;
        }
        modifyPositionIfNeeded();
        modifyRotateIfNeeded();
        modifyScaleIfNeeded();
        modifyRoundIfNeeded();
        Mediator.getInstance().clearView();
        Mediator.getInstance().Notify();
    }

    private void modifyRotateIfNeeded(){
        try {
            rotateValue = (rotate.getText().compareTo("") == 0) ? -1 : Double.parseDouble(rotate.getText());
        }
        catch (NumberFormatException e) {
            return;
        }
        if (rotateValue != -1){
            Mediator.getInstance().rotate(toEdit, rotateValue);
            undoCount++;
        }
    }

    private void modifyRoundIfNeeded(){
        try {
            roundwidth = (roundW.getText().compareTo("") == 0) ? -1 : Integer.parseInt(roundW.getText());
        }
        catch (NumberFormatException e) {
            return;
        }
        try {
            roundheight = (roundH.getText().compareTo("") == 0) ? -1 : Integer.parseInt(roundH.getText());
        }
        catch (NumberFormatException e) {
            return;
        }
        if (toEdit instanceof Rectangle || isCompositeFullOfRectangle(toEdit)) {
            if (roundheight == -1)
                roundheight = roundwidth;
            if (roundwidth == -1)
                roundwidth = roundheight;
            if (roundwidth != -1 && roundheight != -1) {
                Mediator.getInstance().roundBorders(toEdit, roundwidth, roundheight);
                undoCount++;
            }
        }

    }

    private void modifyScaleIfNeeded(){
        try {
            scaleValue = (scale.getText().compareTo("") == 0) ? -1 : Double.parseDouble(scale.getText());
            }
        catch (NumberFormatException e) {
            return;
        }
        if (scaleValue != -1){
            while (toEdit.getPosition().getX() +toEdit.getWidth() * scaleValue > whiteBoard.getWidth()
                   || toEdit.getPosition().getY() +toEdit.getHeight() * scaleValue > whiteBoard.getHeight())
                scaleValue--;


            Mediator.getInstance().ReScale(toEdit, scaleValue);
            undoCount++;
        }
    }

    private void modifyPositionIfNeeded(){
        undoCount++;
        boolean modifyX = true, modifyY = true;
        try {
            if (x.getText().compareTo("") == 0) modifyX = false;
            else posX = Integer.parseInt(x.getText());
        }
        catch (NumberFormatException e) {
            return;
        }
        try{
            if (y.getText().compareTo("") == 0) modifyY = false;
            else posY = Integer.parseInt(y.getText());
        }
        catch (NumberFormatException e) {
            return;
        }


        if (posX != -1 && posX < 0) posX = 0;
        if (posY != -1 && posY < 0) posY = 0;

        checkLimits();



        if (modifyX && modifyY && posX != -1 && posY != -1)
            Mediator.getInstance().move(toEdit, posX, posY);
        else if (modifyX && posX != -1 && posY == -1)
            Mediator.getInstance().move(toEdit, posX, toEdit.getPosition().getY());
        else if (modifyY && posX == -1 && posY != -1)
            Mediator.getInstance().move(toEdit,toEdit.getPosition().getX(), posY);
        else
          undoCount--;
    }

    private void checkLimits(){

        if (toEdit instanceof  Composite){
            Composite c = (Composite) toEdit;
            if(posX != -1 && posX < c.getWidth())  posX = c.getWidth();
            if(posY != -1 && posY < c.getHeight()) posY = c.getHeight();
        }

        if (posX >= whiteBoard.getWidth()){
                posX = (whiteBoard.getWidth() - toEdit.getWidth());
        }

        if (posY >= whiteBoard.getHeight()){
             posY = (whiteBoard.getHeight() - toEdit.getHeight());
        }
    }

    private boolean isCompositeFullOfRectangle(IShape toEdit){
        if(toEdit instanceof  Composite){
            Composite c = (Composite) toEdit;
            for (IShape s : c.getComponents())
                if (!(s instanceof  Rectangle))
                    return false;
            return true;
        }
        return false;
    }
}


