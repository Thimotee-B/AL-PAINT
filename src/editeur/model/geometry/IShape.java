package editeur.model.geometry;

import java.io.Serializable;

import editeur.model.draw.Drawable;
import editeur.model.geometry.base.Point;
import editeur.model.geometry.memento.Originator;

public interface IShape extends Cloneable, Originator, Serializable, Drawable {
	
	int getColorR();

	int getColorG();

	int getColorB();

	void changeColor(int r, int g, int b);
	
	Point getPosition();

	int getHeight();

	int getWidth();

	void setWidth(int width);

	void setHeight(int height);

	void move(int dx, int dy);

	void setPosition(int x, int y);
	
	void scale(double factor);

	void setAlpha(double alpha);

	double getAlpha();

	double getRotation();

	void rotate(double rotation);
	
	Point getTranslationCenter();

	Point getRotationCenter();

	void setRotationCenter(Point p);

	Shape clone();

	boolean isInside(Point p);

}

