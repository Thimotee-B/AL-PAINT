package editeur.model.geometry.memento;

/**
 * The type MementoSimplePolygon extends MementoShape and contributes on how to save a polygon.
 */
public class MementoSimplePolygon extends MementoShape {
	private final int numberOfSides;
	private final float sideSize;

	/**
	 * Instantiates a new Memento simple polygon.
	 *
	 * @param numberOfSides the number of sides
	 * @param sideSize      the side size
	 */
	public MementoSimplePolygon(int numberOfSides, float sideSize) {
		this.numberOfSides = numberOfSides;
		this.sideSize      = sideSize;

	}

	/**
	 * Gets number of sides.
	 *
	 * @return the number of sides
	 */
	public int getNumberOfSides() {
		return numberOfSides;
	}

	/**
	 * Gets side size.
	 *
	 * @return the side size
	 */
	public float getSideSize() {
		return sideSize;
	}
	
	

}
