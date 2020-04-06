package editeur.model.geometry.base;

import editeur.model.geometry.Shape;
import editeur.model.geometry.memento.Memento;

public class SimplePolygon extends Shape {

	
	private int numberOfSides;
	private float sideSize;
	
	public SimplePolygon(int x, int y, int numberOfSides, float sideSize){
		super(x, y, new Point(x,y), new Point(x,y));
		
		this.numberOfSides = numberOfSides;
		this.sideSize 	   = sideSize;
	}
	
	public void setNumberOfSides(int numberOfSides){
		this.numberOfSides = numberOfSides;
	}
	
	public void setSideSize(float sideSize) {
		this.sideSize = sideSize;
	}
	
	public void setAlpha(int alpha) {
		super.setAlpha(alpha);
	}

	@Override
	public void move(int dx, int dy) {
		translate(dx,dy);
	}

	@Override
	public void scale(double factor) {
		this.sideSize*=factor;
	}

	@Override
	public Memento save() {
		return null;
	}

	@Override
	public void restore(Memento memento) {
	}


	@Override
	public Shape clone() {
		return super.clone();
	}


}
