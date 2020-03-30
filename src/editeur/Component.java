package editeur;

import java.util.List;

public abstract class Component implements IComponent {

	private Point position;
	private double rotation;
	private Point rotationCenter;
	private Point translationCenter;
	private int colorR;
	private int colorG;
	private int colorB;


	public Component(int x, int y, Point rotationCenter, Point translationCenter) {
		position=new Point(x, y);
		this.rotationCenter = rotationCenter;
		this.translationCenter = translationCenter;
		colorR = 0;
		colorG = 0;
		colorB = 0;
		rotation = 0.0;
	}
	
	@Override
	public void changeColor(int r, int g, int b) {
		this.colorR=r;
		this.colorG=g;
		this.colorB=b;
		return;
	}
	
	@Override
	public void move(int x, int y) {
		setPosition(x, y);
	}
	
	@Override
	public abstract void scale(double factor); //abstact because it has to be done for each component individually (a vérif)
	
	@Override
	public Point getPosition() {
		return position;
	}
	
	@Override
	public void setPosition(int x, int y) {
		this.position = new Point(x,y);
	}
	
	public void translate(int dx, int dy) {
		this.position = new Point(position.getX() + dx, position.getY() + dy);
		this.rotationCenter = new Point(this.rotationCenter.getX() + dx, this.rotationCenter.getY() + dy);
		this.translationCenter = new Point(this.translationCenter.getX() + dx, this.translationCenter.getY() + dy);
	}
	
	@Override
	public Point getTranslationCenter() {
		return this.translationCenter;
	}
	 
	
	@Override
	public double getRotation() {
		return rotation;
	}
	
	
	@Override
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	@Override
	public Point getRotationCenter() {
		return rotationCenter;
	}

	@Override
	public int getColorR() {
		return this.colorR;
	}
	
	@Override
	public int getColorG() {
		return this.colorG;
	}
	
	@Override
	public int getColorB() {
		return this.colorB;
	}
	


	@Override
	public Component clone() {
		Component clone = null;
		try {
			clone = (Component) super.clone();
		} catch (CloneNotSupportedException e) {
			//catch to do
		}
		return clone;
	}

	public void createMemento(Memento memento) {
	}
	
	public void restoreMemento(Memento memento){
	}
}
