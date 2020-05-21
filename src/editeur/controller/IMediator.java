package editeur.controller;

import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;
import editeur.model.menu.MenuBuilder;

/**
 * The interface IMediator.
 * Handle the complicated communications beetween components as CareTaker, commands, observers, application and some shapes.
 * Is also a singleton, so is accessible anywhere from the project.
 * Simplify a lot the process, all high level operations are accessible from there, and everything is updated.
 */
public interface IMediator extends SubjectObserve {
    int LEFT  = 0;
    int RIGHT = 1;


    /**
     * Define the start behavior. Auto load the old toolbar if exists else init it with 3 shapes.
     */
    void start();

    /**
     * Group the selected shapes and add this group shape s.
     * Everything is updated, handle the commands and update the view.
     * @param s the shape
     */
    void group(IShape s);

    /**
     * Round borders of a rectangle else do nothing.
     * Everything is updated.
     * @param r           the rectangle
     * @param roundWidth  the round width wanted
     * @param roundHeight the round height wanted
     */
    void roundBorders(IShape r, int roundWidth, int roundHeight);

    /**
     * Cancel the group composite, and add all its shapes to shape s ( his parent).
     * Everything is updated.
     * @param s     the s
     * @param group the group
     */
    void unGroup(IShape s, Composite group);

    /**
     * ReColor the shape s.
     * AutoUpdate.
     * @param s the shape
     * @param r the red value
     * @param g the green value
     * @param b the blue value
     */
    void reColor(IShape s, int r, int g, int b);

    /**
     * Clear view.
     */
    void clearView();

    /**
     * Move the shape shape at dx, dy position.
     * AutoUpdate.
     * @param shape the shape
     * @param dx    the dx
     * @param dy    the dy
     */
    void move(IShape shape, int dx, int dy);

    /**
     * Rescale the shape shape factor times.
     * AutoUpdate.
     * @param shape  the shape
     * @param factor the factor
     */
    void ReScale(IShape shape, double factor);

    /**
     * Add toAdd shape to shapes.
     * AutoUpdate.
     * @param shapes the shapes
     * @param toAdd  the to add
     */
    void add(IShape shapes, IShape toAdd);

    /**
     * Delete toDelete shape from shapes.
     * AutoUpdate.
     * @param shapes   the shapes
     * @param toDelete the to delete
     */
    void delete(IShape shapes, IShape toDelete);

    /**
     * Rotate the shape shape of factor angle.
     * AutoUpdate.
     * @param shape  the shape
     * @param factor the factor
     */
    void rotate(IShape shape, double factor);

    /**
     * Simply undo the last action. AutoUpdate.
     */
    void undo();

    /**
     * Simply redo the last action. AutoUpdate.
     */
    void redo();

    /**
     * Save the current whiteboard and toolbar.
     *
     * @param name the name file
     * @return the boolean
     */
    boolean save(String name);

    /**
     * Load the saved toolbar if only toolbar true else load the saved toolbar & whiteboard.
     *
     * @param name        the name file.
     * @param onlyToolbar the only toolbar
     * @return the boolean
     */
    boolean load(String name, boolean onlyToolbar);

    /**
     * Call menu at position x,y
     *
     * @param builder the menu builder who handle of construction.
     * @param x       the x
     * @param y       the y
     */
    void callMenu(MenuBuilder builder, int x, int y);

    /**
     * Hide the called menu if exists.
     */
    void hideMenu();

	//User Controll Events

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
    void MouseClickEvent(boolean fromToolbar ,int clickSide,Point old, Point to);

    /**
     * Mouse click event for adding a tool ( a shape in the toolbar.)
     * Simply clone and add the cloned shape at old position if exists and add it to the to position.
     * Does nothing if from toolbar
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    void MouseDraggedEvent(boolean fromToolbar ,int clickSide,Point old, Point to);

    /**
     * Mouse dragged event: Simply drag a shape from toolbar at old position and clone it to to position
     * in whiteboard.
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    void MouseClickEventAddTool(boolean fromToolbar ,int clickSide,Point old, Point to);

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

    /**
     * Show the current selection rectangle from p1 to p2 position.
     *
     * @param p1 the p 1
     * @param p2 the p 2
     */
    void ShowSelection(Point p1, Point p2);


}
