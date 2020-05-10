package editeur.model.menu.EditMenu;

import editeur.model.geometry.IShape;

public interface EditMenuBuilder {

    void buildDialog();

    void buildColorPicker();

    void buildPositionPicker();

    void buildRotationPicker();

    void buildScalePicker();

    void buildRoundPicker();

    void buildDialogContent();

    void buildDialogButtons();

    void buildResult();

}
