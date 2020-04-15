package editeur.model.geometry.memento;

import editeur.model.geometry.base.Point;

public abstract class MementoShape implements Memento{
	
	private Point position, rotationCenter, translationCenter;
	private double rotation;
	private int r, g, b;
	private double alpha;
	
	public MementoShape() {}
	
	public void set(MementoShape memento, Point position, Point rotationCenter, Point translationCenter,
						double rotation, int r, int g, int b, double alpha) {
		
		memento.position = position;
		memento.rotation = rotation;
		memento.rotationCenter    = rotationCenter;
		memento.translationCenter = translationCenter;
		memento.r     = r;
		memento.g     = g;
		memento.b     = b;
		memento.alpha = alpha;
	}

	public Point getPosition() {
		return position;
	}

	public Point getRotationCenter() {
		return rotationCenter;
	}

	public Point getTranslationCenter() {
		return translationCenter;
	}

	public double getRotation() {
		return rotation;
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public double getAlpha() {
		return alpha;
	}
	
	
	

}
