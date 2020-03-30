package editeur;

import java.io.Serializable;

public interface IComponent extends Cloneable, Originator, Serializable {
	
	public int getColorR();

	public int getColorG();

	public int getColorB();

	public void changeColor(int r, int g, int b);
	
	Point getPosition();

	public void move(int dx, int dy);

	public void setPosition(int x, int y);
	
	public void scale(double factor);

	public void setAlpha(int alpha);

	double getRotation();

	void setRotation(double rotation);
	
	Point getTranslationCenter();

	Point getRotationCenter();

	public Component clone();

}
