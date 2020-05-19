package editeur.controller;

import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;
import editeur.model.menu.MenuBuilder;

public interface IMediator extends SubjectObserve {
    int LEFT  = 0;
    int RIGHT = 1;


    void start();

	/**
	 *
	 * @param s
	 */
	void group(IShape s);

	/**
	 *
	 * @param r
	 * @param roundWidth
	 * @param roundHeight
	 */
	void roundBorders(IShape r, int roundWidth, int roundHeight);

	/**
	 *
	 * @param s
	 * @param group
	 */
	void unGroup(IShape s, Composite group);

	/**
	 *
	 * @param s
	 * @param r
	 * @param g
	 * @param b
	 */
	void reColor(IShape s, int r, int g, int b);

	void clearView();

	/**
	 *
	 * @param shape
	 * @param dx
	 * @param dy
	 */
	void move(IShape shape, int dx, int dy);

	/**
	 *
	 * @param shape
	 * @param factor
	 */
	void ReScale(IShape shape, double factor);

	/**
	 *
	 * @param shapes
	 * @param toAdd
	 */
	void add(IShape shapes, IShape toAdd);

	/**
	 *
	 * @param shapes
	 * @param toDelete
	 */
	void delete(IShape shapes, IShape toDelete);

	/**
	 *
	 * @param shape
	 * @param factor
	 */
	void rotate(IShape shape, double factor);
	
	void undo();
	
	void redo();

	/**
	 *
	 * @param name
	 * @return
	 */
	boolean save(String name);

	/**
	 *
	 * @param name
	 * @param onlyToolbar
	 * @return
	 */
	boolean load(String name, boolean onlyToolbar);

	/**
	 *
	 * @param builder
	 * @param x
	 * @param y
	 */
	void callMenu(MenuBuilder builder, int x, int y);
	void hideMenu();

	//User Controll Events

	/**
	 *
	 * @param fromToolbar
	 * @param clickSide
	 * @param old
	 * @param to
	 */
	void MouseClickEvent(boolean fromToolbar ,int clickSide,Point old, Point to);

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
	void MouseClickEventAddTool(boolean fromToolbar ,int clickSide,Point old, Point to);

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

	/**
	 *
	 * @param p1
	 * @param p2
	 */
	void ShowSelection(Point p1, Point p2);


}
