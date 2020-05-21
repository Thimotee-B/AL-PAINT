package editeur.model.geometry.memento;

import editeur.model.geometry.base.Point;

/**
 * The type MementoShape contributes to save a Shape.
 * Store the current state of shape in this memento.
 */
public abstract class MementoShape implements Memento{

	private Point position, rotationCenter, translationCenter;
	private double rotation;
	private int r, g, b;
	private double alpha;
	private int width, height;

	/**
	 * Instantiates a new Memento shape.
	 */
	public MementoShape() {}

	/**
	 * Set the memento in parameter with next parameters: information about shapes.
	 *
	 * @param memento           the memento
	 * @param position          the position
	 * @param rotationCenter    the rotation center
	 * @param translationCenter the translation center
	 * @param rotation          the rotation
	 * @param r                 the r value
	 * @param g                 the g value
	 * @param b                 the b value
	 * @param alpha             the alpha value
	 * @param width             the width
	 * @param height            the height
	 */
	public void set(MementoShape memento, Point position, Point rotationCenter, Point translationCenter,
						double rotation, int r, int g, int b, double alpha, int width, int height) {
		
		memento.position = position;
		memento.rotation = rotation;
		memento.rotationCenter    = rotationCenter;
		memento.translationCenter = translationCenter;
		memento.r     = r;
		memento.g     = g;
		memento.b     = b;
		memento.alpha = alpha;
		memento.width = width;
		memento.height= height;
	}

	/**
	 * Gets position.
	 *
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Gets rotation center.
	 *
	 * @return the rotation center
	 */
	public Point getRotationCenter() {
		return rotationCenter;
	}

	/**
	 * Gets translation center.
	 *
	 * @return the translation center
	 */
	public Point getTranslationCenter() {
		return translationCenter;
	}

	/**
	 * Gets rotation.
	 *
	 * @return the rotation
	 */
	public double getRotation() {
		return rotation;
	}

	/**
	 * Gets r.
	 *
	 * @return the r
	 */
	public int getR() {
		return r;
	}

	/**
	 * Gets g.
	 *
	 * @return the g
	 */
	public int getG() {
		return g;
	}

	/**
	 * Gets b.
	 *
	 * @return the b
	 */
	public int getB() {
		return b;
	}

	/**
	 * Gets alpha.
	 *
	 * @return the alpha
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * Gets width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets width.
	 *
	 * @param width the width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Sets height.
	 *
	 * @param height the height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}
