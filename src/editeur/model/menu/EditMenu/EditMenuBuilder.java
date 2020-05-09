package editeur.model.menu.EditMenu;

import editeur.model.geometry.IShape;

public interface EditMenuBuilder {

    void buildDialog();

    void buildColorPicker(IShape toEdit);

    void buildDialogContent();

    void buildDialogButtons();

    void buildResult();

}
