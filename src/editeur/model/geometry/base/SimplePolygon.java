package editeur.model.geometry.base;

import editeur.model.draw.DrawBridge;
import editeur.model.geometry.Shape;
import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.MementoSimplePolygon;

import java.util.Vector;

/**
 * The type SimplePolygon.
 */
public class SimplePolygon extends Shape {

    private static final long serialVersionUID = 7570243485311308601L;
    private int numberOfSides;
    private float sideSize;
    private double[] polygonPts;

    /**
     * Instantiates a new Simple polygon.
     *
     * @param x             the x
     * @param y             the y
     * @param numberOfSides the number of sides
     * @param sideSize      the side size
     */
    public SimplePolygon(int x, int y, int numberOfSides, float sideSize){
        super(x, y, new Point(x,y), new Point(x,y), (int) (sideSize * 2), (int) (sideSize *2));
        this.numberOfSides = numberOfSides;
        this.sideSize 	   = sideSize;
        this.polygonPts    = ProcessPolygonPoints();
        this.setRotationCenter(new Point(x + getRadius(),y +  getRadius()));

    }

    /**
     * Move the polygon at dx, dy position.
     *
     * @param dx the dx
     * @param dy the dy
     */
    @Override
    public void move(int dx, int dy) {
        super.move(dx,dy);
        this.setRotationCenter(new Point(dx + getRadius(),dy +  getRadius()));
    }

    /**
     * Scale the polygon of factor times.
     * Reprocess the polygon points since they have changed.
     * @param factor the factor
     */
    @Override
    public void scale(double factor) {
        this.sideSize *= factor;
        setHeight((int) sideSize *2);
        setWidth ((int) sideSize *2);
        this.polygonPts = ProcessPolygonPoints();
    }

    /**
     * Save the current state in memento.
     *
     * @return the memento
     */
    @Override
    public Memento save() {
        MementoSimplePolygon m = new MementoSimplePolygon(numberOfSides, sideSize);
        super.save(m);
        return m;
    }

    /**
     * Restore the saved state from memento.
     *
     * @param memento the memento
     */
    @Override
    public void restore(Memento memento) {
    	MementoSimplePolygon msp = (MementoSimplePolygon) memento;
        this.numberOfSides  = msp.getNumberOfSides();
        this.sideSize       = msp.getSideSize();
        this.polygonPts     = ProcessPolygonPoints();
    	super.restore(msp);

    }


    /**
     * Clone shape.
     *
     * @return the shape
     */
    @Override
    public Shape clone() {
        return super.clone();
    }

    /**
     * Draw the polygon on drawsurface.
     *
     * @param db          the db
     * @param drawSurface the draw surface
     */
    @Override
	public void draw(DrawBridge db, Object drawSurface) {
		db.drawPolygon(drawSurface, this);
		
	}

    /**
     * Tell if point is inside the polygon.
     *
     * @param p the p
     * @return the boolean
     */
    @Override
    public boolean isInside(Point p) {
        double[] pts = polygonPts;
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

    /**
     * Calculate rotate centroid.
     */
    public void calculateRotateCentroid(){

        int x = getPosition().getX();
        int y = getPosition().getY();
        double fx = this.polygonPts[0] + x;
        double fy = this.polygonPts[1] + y;
        double area = 0;
        double x1 = 0, x2 = 0;
        double y1 = 0, y2 = 0;
        double newX = 0, newY = 0;
        double f;
        for (int i = 0, j = polygonPts.length - 2; i < polygonPts.length ; j = i,  i+=2) {
            x1 = this.polygonPts[i] + x;
            y1 = this.polygonPts[i + 1] + y;
            x2 = this.polygonPts[j] + x;
            y2 = this.polygonPts[j + 1] + y;
            f = (x1 - fx) * (y2 - fy) - (x2 - fx) * (y1 - fy);
            area += f;
            newX += (x1 + x2 - 2 * fx) * f;
            newY += (y1 + y2 - 2 * fy) * f;
        }
        f = area * 3;

        setRotationCenter( new Point((int) (newX / f + fx), (int)(newY / f + fy)));
    }

    /**
     * Process polygon points, we do not add the current x,y position.
     *
     * @return the double [ ]
     */
    public double[] ProcessPolygonPoints(){
        double[] pts = new double[numberOfSides * 2];
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

    /**
     * Get polygon points double [ ].
     *
     * @return the double [ ]
     */
    public double[] getPolygonPoints() {
        return polygonPts;
    }

    /**
     * Get radius int.
     *
     * @return the int
     */
    public int getRadius(){
        double area  = 2 * Math.sin(Math.toRadians(180.0/numberOfSides));
        double radius = sideSize / area;

        return (int) radius;
    }

    /**
     * Set number of sides.
     *
     * @param numberOfSides the number of sides
     */
    public void setNumberOfSides(int numberOfSides){
        this.numberOfSides = numberOfSides;
    }

    /**
     * Sets side size.
     *
     * @param sideSize the side size
     */
    public void setSideSize(float sideSize) {
        this.sideSize = sideSize;
    }

    /**
     * Sets alpha.
     *
     * @param alpha the alpha
     */
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
    }




}
