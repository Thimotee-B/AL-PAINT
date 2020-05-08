package editeur.controller;

import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;
import editeur.view.GraphicalObjectObserver;

public interface IMediator extends SubjectObserve {
    public static final int LEFT  = 0; 
    public static final int RIGHT = 1;
    
    void start();

	void group(IShape s, int [] coordinates);
	
	void unGroup();
	
	void reColor();

	void clearView();

	void move(IShape shape, int dx, int dy);
	
	void ReScale(IShape shape, double factor);
	
	void add(IShape shapes, IShape toAdd);

	void delete(IShape shapes, IShape toDelete);
	
	void rotate(IShape shape, double factor);
	
	void undo();
	
	void redo();
	
	void MouseClickEvent(boolean fromToolbar ,int clickSide,Point old, Point to);
	void MouseDraggedEvent(boolean fromToolbar ,int clickSide,Point old, Point to);
	void MouseClickEventAddTool(boolean fromToolbar ,int clickSide,Point old, Point to);

	void ShowDraggedShape(boolean fromToolbar, Point old, Point to);

}
