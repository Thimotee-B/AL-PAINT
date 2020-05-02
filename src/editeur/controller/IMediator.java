package editeur.controller;

import editeur.model.geometry.IShape;

public interface IMediator {
	
    void start();
    
    
	void group();
	
	void unGroup();
	
	void reColor();
	

	void move(IShape shape, int dx, int dy);
	
	void ReScale(IShape shape, double factor);
	
	void add(IShape shapes, IShape toAdd);

	void delete(IShape shapes, IShape toDelete);
	
	void rotate(IShape shape, double factor);
	
	void undo();
	
	void redo();
}
