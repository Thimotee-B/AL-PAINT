package editeur;

import java.util.ArrayList;
import java.util.List;

public class Polygon extends Component {

	
	private int numberOfSides;
	private float sideSize;
	
	public Polygon(int x, int y, int numberOfSides, float sideSize){
		super(x, y, new Point(x,y), new Point(x,y));
		this.numberOfSides = numberOfSides;
		this.sideSize = sideSize;
	}
	
	public void setNumberOfSides(int numberOfSides){
		this.numberOfSides = numberOfSides;
	}
	
	public void setSideSize(float sideSize) {
		this.sideSize = sideSize;
	}

	@Override
	public void move(int dx, int dy) {
		translate(dx,dy);
	}

	@Override
	public void rescale(double factor) {
		this.sideSize*=factor;
	}

	@Override
	public Memento createMemento() {
	}

	@Override
	public void restoreMemento(Memento memento) {
	}


	@Override
	public Component clone() {
		return super.clone();
	}


}
