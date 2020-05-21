package editeur.model.menu.EditMenu;

/**
 * The interface EditMenuBuilder represents the steps for building a Editmenu.
 * Some steps can not be done.
 * Once all the step you need are done you can build the result.
 * Can have multiple Representations.
 */
public interface EditMenuBuilder {

    /**
     * Build dialog.
     */
    void buildDialog();

    /**
     * Build color picker.
     */
    void buildColorPicker();

    /**
     * Build position picker.
     */
    void buildPositionPicker();

    /**
     * Build rotation picker.
     */
    void buildRotationPicker();

    /**
     * Build scale picker.
     */
    void buildScalePicker();

    /**
     * Build round picker.
     */
    void buildRoundPicker();

    /**
     * Build dialog content.
     */
    void buildDialogContent();

    /**
     * Build dialog buttons.
     */
    void buildDialogButtons();

    /**
     * Build result.
     */
    void buildResult();

}
