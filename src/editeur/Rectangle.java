package editeur;


public class Rectangle extends Component{

	private int length;
	private int width;
	
	
	public Rectangle(int x, int y, int width, int length){
		super(x, y, new Point(x + width/2, y + length/2), new Point(x + width/2, y + length/2));
		this.length = length;
		this.width = width;
	}
	
	//J'ai pas trop compris le truc des arrondis
	public void RoundBorders(){
	}
	
	public void setHeight(int length){
		this.length = length;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.length;
	}
	
	@Override
	public void move(int newx, int newy){
		translate(newx, newy);
	}

	@Override
	public void rescale(double factor){
		this.length = (int) this.length * factor;
		this.width = (int) this.width * factor;
		return;
	}


	@Override
	public Memento createMemento(){
		MementoRectangle mem = new MementoRectangle();
;		return mem;
	}

	@Override
	public void restoreMemento(Memento memento){
	}
	
	@Override
	public Component clone(){
		return super.clone();
	}



}
