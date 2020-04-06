package editeur.model.geometry;



import editeur.model.geometry.base.Point;
import editeur.model.geometry.memento.Memento;

public abstract class Shape implements IShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6832490922368244161L;
	private Point position;
	private double rotation;
	private Point rotationCenter;
	private Point translationCenter;
	private int r, g, b, alpha;


	public Shape(int x, int y, Point rotationCenter, Point translationCenter) {
		this.position		   = new Point(x, y);
		this.translationCenter = translationCenter;
		this.rotationCenter	   = rotationCenter;
		
		this.rotation = 0.0;
		
		this.r     = 0;
		this.g     = 0;
		this.b     = 0;
		this.alpha = 255;
		
	}
	
	@Override
	public void changeColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
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
		this.position 		   = new Point(position.getX() + dx, position.getY() + dy);
		this.rotationCenter    = new Point(this.rotationCenter.getX() + dx, this.rotationCenter.getY() + dy);
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
	public void rotate(double rotation) {
		this.rotation = rotation;
	}
	
	@Override
	public Point getRotationCenter() {
		return rotationCenter;
	}

	@Override
	public int getColorR() {
		return this.r;
	}
	
	@Override
	public int getColorG() {
		return this.g;
	}
	
	@Override
	public int getColorB() {
		return this.b;
	}
	
	public void setAlpha(int alpha) {
		this.alpha = 255;
	}


	@Override
	public Shape clone() {
		Shape clone = null;
		try {
			clone = (Shape) super.clone();
		} catch (CloneNotSupportedException e) {
			//catch to do
		}
		return clone;
	}

	public void save(Memento memento) {
	}
	
	public void restore(Memento memento){
	}
}
