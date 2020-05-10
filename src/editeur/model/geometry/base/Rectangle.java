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
	private int roundHeight, roundWidth;
    
    
    public Rectangle(int x, int y, int width, int length){
        super(x,
              y,
              new Point((x + x+width)/2, (y + y+length)/2),
              new Point((x + x+width)/2, (y + y+length)/2),
              width,
              length
            );

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

    
    @Override
    public void move(int newx, int newy){
        super.move(newx, newy);
        super.setRotationCenter(new Point((newx + newx + getWidth())/2 , (newy + newy + getHeight()) /2));
    }

    @Override
    public void scale(double factor){
        this.setHeight((int) (this.getHeight() * factor));
        this.setWidth ((int) (this.getWidth()  * factor));
    }


    @Override
    public Memento save(){
        MementoRectangle m = new MementoRectangle(roundHeight, roundWidth);
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
