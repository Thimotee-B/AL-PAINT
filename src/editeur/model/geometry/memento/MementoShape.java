package editeur.model.geometry.memento;

import editeur.model.geometry.base.Point;

public abstract class MementoShape implements Memento{
	
	private Point position, rotationCenter, translationCenter;
	private double rotation;
	private int r, g ,b;

}
