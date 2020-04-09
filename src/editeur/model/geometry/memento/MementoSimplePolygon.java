package editeur.model.geometry.memento;

public class MementoSimplePolygon extends MementoShape {
	private int numberOfSides;
	private float sideSize;
	
	public MementoSimplePolygon(int numberOfSides, float sideSize) {
		this.numberOfSides = numberOfSides;
		this.sideSize      = sideSize;
	}

	public int getNumberOfSides() {
		return numberOfSides;
	}

	public float getSideSize() {
		return sideSize;
	}
	
	

}
