package editeur.controller;

import editeur.model.geometry.IShape;

public interface IMediator {
	
    void start();
    
	void group();
	
	void unGroup();
	
	void reColor();
	
	void ReScale(IShape shape, double factor);
	
	void rotate();
	
	void undo();
	
	void redo();
}
