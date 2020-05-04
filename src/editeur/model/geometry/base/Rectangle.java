package editeur.model.geometry.base;

import editeur.model.draw.DrawBridge;
import editeur.model.geometry.Shape;
import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.MementoRectangle;
import editeur.model.geometry.memento.MementoShape;

public class Rectangle extends Shape{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6361385559811152501L;
	private int length, width, roundHeight, roundWidth;
    
    
    public Rectangle(int x, int y, int width, int length){
        super(x,
              y,
              new Point(x + width/2, y + length/2),
              new Point(x + width/2, y + length/2)
            );
        
        this.length = length;
        this.width  = width;
    }
    

    public void RoundBorders(){
        this.roundHeight = 0;
        this.roundWidth  = 0;
    }
    
    
    
    public int getRoundHeight() {
        return roundHeight;
    }


    public void setRoundHeight(int roundHeight) {
        this.roundHeight = roundHeight;
    }


    public int getRoundWidth() {
        return roundWidth;
    }


    public void setRoundWidth(int roundWidth) {
        this.roundWidth = roundWidth;
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
        super.move(newx, newy);
    }

    @Override
    public void scale(double factor){
        this.length = (int) (this.length * factor);
        this.width  = (int) (this.width  * factor);
        return;
    }


    @Override
    public Memento save(){
        MementoRectangle m = new MementoRectangle(length, width, roundHeight, roundWidth);
        super.save(m);
        return m;
    }

    @Override
    public void restore(Memento memento){
    	this.restoreShape((MementoShape) memento);
    }
    
    private void restoreShape(MementoShape mementoShape) {
    	super.restore(mementoShape);
    	MementoRectangle m = (MementoRectangle) mementoShape;
    	
    	this.length      = m.getLength();
    	this.width       = m.getWidth();
    	this.roundHeight = m.getRoundHeight();
    	this.roundWidth  = m.getRoundWidth();
    }
    
    @Override
    public Shape clone(){
        Shape clone = super.clone();
        return clone;
    }

    @Override
    public boolean isInside(Point p){
        Point p2 = this.getPosition();
        if (p.getX() >= p2.getX() && p.getX() <= p2.getX() + getWidth())
            return (p.getY() >= p2.getY() && p.getY() <= p2.getY() + getHeight());
        return false;
    }

	@Override
	public void draw(DrawBridge db, Object drawSurface) {
        db.drawRectangle(drawSurface, this);
	}



}
