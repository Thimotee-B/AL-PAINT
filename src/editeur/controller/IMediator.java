package editeur.controller;

import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;
import editeur.model.menu.MenuBuilder;

public interface IMediator extends SubjectObserve {
    int LEFT  = 0;
    int RIGHT = 1;
    
    void start();

	void group(IShape s);

	void roundBorders(IShape r, int roundWidth, int roundHeight);
	
	void unGroup(IShape s, Composite group);
	
	void reColor(IShape s, int r, int g, int b);

	void clearView();

	void move(IShape shape, int dx, int dy);
	
	void ReScale(IShape shape, double factor);
	
	void add(IShape shapes, IShape toAdd);

	void delete(IShape shapes, IShape toDelete);
	
	void rotate(IShape shape, double factor);
	
	void undo();
	
	void redo();

	boolean save(String name);
	boolean load(String name, boolean onlyToolbar);

	void callMenu(MenuBuilder builder, int x, int y);
	void hideMenu();

	//User Controll Events
	void MouseClickEvent(boolean fromToolbar ,int clickSide,Point old, Point to);

	void MouseDraggedEvent(boolean fromToolbar ,int clickSide,Point old, Point to);

	void MouseClickEventAddTool(boolean fromToolbar ,int clickSide,Point old, Point to);

	void MouseTrashEvent(boolean fromToolbar, int clickSide, Point old, Point to);

	boolean ShowDraggedShape(boolean fromToolbar, Point old, Point to);

	void ShowSelection(Point p1, Point p2);


}
