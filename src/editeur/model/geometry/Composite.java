package editeur.model.geometry;



import java.util.Vector;

import editeur.model.geometry.base.Point;
import editeur.model.geometry.memento.Memento;


public class Composite extends Shape {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7118837371818614670L;
	
	private Vector<IShape> components = new Vector<IShape>(); 
	private int height;
	private int width;

	public Composite(int x, int y, int height, int width) {
		super(x,
			  y,
			  new Point(x + width/2, y + height/2),
			  new Point(x + width/2, y + height/2)
			  );
		
		this.height = height;
		this.width  = width;
	}
	
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	

	@Override
	public void scale(double factor) {
		this.height = (int) (this.height * factor);
		this.width  = (int) (this.width  * factor);
		for(IShape c : components) c.scale(factor);
	}
	
	@Override
	public void setAlpha(int alpha) {
		super.setAlpha(alpha);
		for(IShape c : components) c.setAlpha(alpha);	
	}
	
	@Override
	public void changeColor(int r, int g, int b) {
		super.changeColor(r, g, b);
		for(IShape c : components) c.changeColor(r, g, b);
	}
	
	@Override
	public void move(int dx, int dy) {
		for(IShape c : components) c.move(dx,  dy);
		translate(dx, dy);
	}
	
	public void add(IShape component) {
		this.components.add(component);
		
	}
	
	public Vector<IShape> getComponents(){
		return this.components;
	}

	
	public void remove(IShape component) {
		this.components.remove(component);
		
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
		return null;
	}


}
