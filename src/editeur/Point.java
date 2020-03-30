package editeur;

import java.io.Serializable;

public class Point implements Cloneable, Serializable{
	private int x; 
	private int y;
	
	
	public Point(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	
	public void move(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public void translate(int dx, int dy) {
		this.x+=dx;
		this.y+=dy;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	public boolean equals(Point p) {
		return (this.x==p.getX() && this.y==p.getY());
	}
	
	public Point clone() {
		Point clone = null;
		try {
			clone = (Point) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}
}
