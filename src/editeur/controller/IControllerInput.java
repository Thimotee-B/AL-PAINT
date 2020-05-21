package editeur.controller;


import editeur.model.geometry.base.Point;


/**
 * The interface IControllerInput.
 * The role of this interface is to manage the different mouse or keyboard specifics control on this app.
 * Like moving a shape with the mouse, adding it to the toolbar ...
 * Works with the Mediator.
 */
public interface IControllerInput {

    /**
     * Mouse click event: manage the current mouseEvent.
     * Move the shape pointed by old point to to position.
     * Else select the shapes contains in a rectangle from old to to position.
     *
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    void MouseClickEvent(boolean fromToolbar , int clickSide, Point old, Point to);

    /**
     * Mouse click event for adding a tool ( a shape in the toolbar.)
     * Simply clone and add the cloned shape at old position if exists and add it to the to position.
     * Does nothing if from toolbar
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    void MouseClickEventAddTool(boolean fromToolbar ,int clickSide,Point old, Point to);

    /**
     * Mouse dragged event: Simply drag a shape from toolbar at old position and clone it to to position
     * in whiteboard.
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    void MouseDraggedEvent(boolean fromToolbar ,int clickSide,Point old, Point to);

    /**
     * Mouse trash event : Handle the deletion of shape at old position put in trash
     *
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    void MouseTrashEvent(boolean fromToolbar, int clickSide, Point old, Point to);

    /**
     * Show dragged shape boolean : Draw the current dragged shape, disclaimer, a temporary clone is moved until we
     * reach destination.
     *
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param old         the old position
     * @param to          the to position
     * @return the boolean : false if we dragged nothing.
     */
    boolean ShowDraggedShape(boolean fromToolbar, Point old, Point to);
}
