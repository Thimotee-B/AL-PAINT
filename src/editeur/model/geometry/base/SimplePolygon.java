package editeur.model.geometry.base;

import editeur.model.draw.DrawBridge;
import editeur.model.geometry.Shape;
import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.MementoSimplePolygon;

import java.util.Vector;

public class SimplePolygon extends Shape {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 7570243485311308601L;
	private int numberOfSides;
    private float sideSize;
    private double[] polygonPts;
    
    public SimplePolygon(int x, int y, int numberOfSides, float sideSize){
        super(x, y, new Point(x,y), new Point(x,y), (int) (sideSize * 2), (int) (sideSize *2));
        
        this.numberOfSides = numberOfSides;
        this.sideSize 	   = sideSize;
        this.polygonPts    = ProcessPolygonPoints();
    }

    public int getNumberOfSides() {
        return numberOfSides;
    }

    public float getSideSize() {
        return sideSize;
    }

    public double[] getPolygonPoints() {
        return polygonPts;
    }

    public double[] ProcessPolygonPoints(){
        double pts[] = new double[numberOfSides * 2];
        double area  = 2 * Math.sin(Math.toRadians(180.0/numberOfSides));
        double radius = sideSize / area;
        double a = this.getRotation();

        for (int i = 0; i < 2 * numberOfSides; i++)
            pts[i] = radius;

        for (int i = 0; i < numberOfSides ; i++) {
            pts[2*i]   *= Math.cos(Math.toRadians(a));
            pts[2*i]   += radius;
            pts[2*i+1] *= Math.sin(Math.toRadians(a));
            pts[2*i+1] += radius;
            a += 360.0/numberOfSides;
        }
        return pts;

    }

    public void setNumberOfSides(int numberOfSides){
        this.numberOfSides = numberOfSides;
    }
    
    public void setSideSize(float sideSize) {
        this.sideSize = sideSize;
    }
    
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
    }

    @Override
    public boolean isInside(Point p) {
        double pts[] = polygonPts;
        Point pos = getPosition();
        int x = pos.getX();
        int y = pos.getY();

        int px = p.getX();
        int py = p.getY();
        boolean check = false;
        int i ,j;
        for (i  = 0, j = pts.length - 2;  i < pts.length; j = i,  i+=2)
        {

            int newX  =(int) pts[i] + x;
            int newY  =(int) pts[i+1] + y;
            int newX2 =(int) pts[j] + x;
            int newY2 =(int) pts[j+1] + y;

            if ((newY >= py) != (newY2 >= py))
                if( px <= (newX2 - newX) * (py - newY ) / (newY2 - newY) + newX) {
                    check = !check;
                }
            }
        return check;
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx,dy);
    }

    @Override
    public void scale(double factor) {
        this.sideSize *= factor;
        setHeight((int) sideSize *2);
        setWidth ((int) sideSize *2);
        this.polygonPts = ProcessPolygonPoints();
    }

    @Override
    public Memento save() {
        MementoSimplePolygon m = new MementoSimplePolygon(numberOfSides, sideSize);
        super.save(m);
        return m;
    }

    @Override
    public void restore(Memento memento) {
    	MementoSimplePolygon msp = (MementoSimplePolygon) memento;
        this.numberOfSides  = msp.getNumberOfSides();
        this.sideSize       = msp.getSideSize();
        this.polygonPts     = ProcessPolygonPoints();
    	super.restore(msp);

    }


    @Override
    public Shape clone() {
        return super.clone();
    }

	@Override
	public void draw(DrawBridge db, Object drawSurface) {
		db.drawPolygon(drawSurface, this);
		
	}


}
