package editeur.controller;


import editeur.model.geometry.base.Point;


public interface IControllerInput {

    void MouseClickEvent(boolean fromToolbar , int clickSide, Point old, Point to);

    void MouseClickEventAddTool(boolean fromToolbar ,int clickSide,Point old, Point to);

    void MouseDraggedEvent(boolean fromToolbar ,int clickSide,Point old, Point to);

    void MouseTrashEvent(boolean fromToolbar, int clickSide, Point old, Point to);

    boolean ShowDraggedShape(boolean fromToolbar, Point old, Point to);

}
