package editeur.model.geometry.memento;

/**
 * The type MementoRectangle extends MementoShape and contributes to save a rectangle.
 */
public class MementoRectangle extends MementoShape {
	private final int roundHeight;
	private final int roundWidth;

	/**
	 * Instantiates a new Memento rectangle.
	 *
	 * @param roundHeight the round height
	 * @param roundWidth  the round width
	 */
	public MementoRectangle(int roundHeight, int roundWidth) {
		this.roundHeight  = roundHeight;
		this.roundWidth   = roundWidth;
	}


	/**
	 * Gets round height.
	 *
	 * @return the round height
	 */
	public int getRoundHeight() {
		return roundHeight;
	}

	/**
	 * Gets round width.
	 *
	 * @return the round width
	 */
	public int getRoundWidth() {
		return roundWidth;
	}
	
	
	
}
