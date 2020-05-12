package editeur.model.geometry;



import editeur.model.geometry.base.Point;
import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.MementoShape;

public abstract class Shape implements IShape {

    /**
     * 
     */
    private static final long serialVersionUID = -6832490922368244161L;
    private Point position;
    private double rotation;
    private Point rotationCenter;
    private Point translationCenter;
    private int r, g, b;
    private double alpha;
    private int width, height;


    public Shape(int x, int y, Point rotationCenter, Point translationCenter, int width, int height) {
        this.position          = new Point(x, y);
        this.translationCenter = translationCenter;
        this.rotationCenter    = rotationCenter;
        
        this.rotation = 0.0;
        
        this.r     = 0;
        this.g     = 0;
        this.b     = 0;
        this.alpha = 1;
        this.width = width;
        this.height = height;
        
    }
    
    @Override
    public void changeColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    @Override
    public void move(int x, int y) {
        setPosition(x, y);

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public abstract void scale(double factor); //abstact because it has to be done for each component individually (a vérif)
    
    @Override
    public Point getPosition() {
        return position;
    }
    
    @Override
    public void setPosition(int x, int y) {
        this.position = new Point(x,y);
    }
    
    public void translate(int dx, int dy) {
        this.position          = new Point(position.getX() + dx, position.getY() + dy);
        this.rotationCenter    = new Point(this.rotationCenter.getX() + dx, this.rotationCenter.getY() + dy);
        this.translationCenter = new Point(this.translationCenter.getX() + dx, this.translationCenter.getY() + dy);
    }
    
    @Override
    public Point getTranslationCenter() {
        return this.translationCenter;
    }

    @Override
    public void setRotationCenter(Point p){
        this.rotationCenter = p;
    }
    
    @Override
    public double getRotation() {
        return rotation;
    }
    
    
    @Override
    public void rotate(double rotation) {
        this.rotation = rotation;
    }
    
    @Override
    public Point getRotationCenter() {
        return rotationCenter;
    }

    @Override
    public int getColorR() {
        return this.r;
    }
    
    @Override
    public int getColorG() {
        return this.g;
    }
    
    @Override
    public int getColorB() {
        return this.b;
    }
    
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }


    @Override
    public abstract boolean isInside(Point p);

    public double getAlpha() {
        return alpha;
    }

    @Override
    public Shape clone() {
        Shape clone = null;
        try {
            clone = (Shape) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            //catch to do
        }
        return null;
    }

    public void save(MementoShape memento) {
    	memento.set(memento, position, rotationCenter, translationCenter, rotation, r, g, b, alpha, width, height);

    }
    
    public void restore(MementoShape memento){
        this.position          = new Point(memento.getPosition().getX(), memento.getPosition().getY());
        this.translationCenter = new Point(memento.getTranslationCenter().getX(), memento.getTranslationCenter().getY());
        this.rotationCenter    = new Point(memento.getRotationCenter().getX(), memento.getRotationCenter().getY());
        
        this.rotation = memento.getRotation();
        
        this.r     = memento.getR();
        this.g     = memento.getG();
        this.b     = memento.getB();
        this.alpha = memento.getAlpha();

        this.height= memento.getHeight();
        this.width = memento.getWidth();

    }
}
