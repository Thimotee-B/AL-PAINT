package editeur.model.geometry;

import java.io.Serializable;

import editeur.model.draw.Drawable;
import editeur.model.geometry.base.Point;
import editeur.model.geometry.memento.Originator;

public interface IShape extends Cloneable, Originator, Serializable, Drawable {
	
	public int getColorR();

	public int getColorG();

	public int getColorB();

	public void changeColor(int r, int g, int b);
	
	Point getPosition();

	public int getHeight();

	public int getWidth();

	public void setWidth(int width);

	public void setHeight(int height);

	public void move(int dx, int dy);

	public void setPosition(int x, int y);
	
	public void scale(double factor);

	public void setAlpha(double alpha);

	public double getAlpha();

	double getRotation();

	void rotate(double rotation);
	
	Point getTranslationCenter();

	Point getRotationCenter();

	public void setRotationCenter(Point p);

	public Shape clone();

	public boolean isInside(Point p);

}

