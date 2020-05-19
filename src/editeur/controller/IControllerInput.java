package editeur.controller;


import editeur.model.geometry.base.Point;


public interface IControllerInput {

    /**
     *
     * @param fromToolbar
     * @param clickSide
     * @param old
     * @param to
     */
    void MouseClickEvent(boolean fromToolbar , int clickSide, Point old, Point to);

    /**
     *
     * @param fromToolbar
     * @param clickSide
     * @param old
     * @param to
     */
    void MouseClickEventAddTool(boolean fromToolbar ,int clickSide,Point old, Point to);

    /**
     *
     * @param fromToolbar
     * @param clickSide
     * @param old
     * @param to
     */
    void MouseDraggedEvent(boolean fromToolbar ,int clickSide,Point old, Point to);

    /**
     *
     * @param fromToolbar
     * @param clickSide
     * @param old
     * @param to
     */
    void MouseTrashEvent(boolean fromToolbar, int clickSide, Point old, Point to);

    /**
     *
     * @param fromToolbar
     * @param old
     * @param to
     * @return
     */
    boolean ShowDraggedShape(boolean fromToolbar, Point old, Point to);
}
