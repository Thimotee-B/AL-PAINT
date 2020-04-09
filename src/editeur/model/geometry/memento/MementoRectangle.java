package editeur.model.geometry.memento;

public class MementoRectangle extends MementoShape {
	private int length, width, roundHeight, roundWidth;
	
	public MementoRectangle(int length, int width, int roundHeight, int roundWidth) {
		this.length       = length;
		this.width        = width;
		this.roundHeight  = roundHeight;
		this.roundWidth   = roundWidth;
	}

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}

	public int getRoundHeight() {
		return roundHeight;
	}

	public int getRoundWidth() {
		return roundWidth;
	}
	
	
	
}
