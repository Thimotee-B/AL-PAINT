package editeur.model.geometry;



import java.util.HashMap;

import java.util.Vector;

import editeur.model.draw.DrawBridge;
import editeur.model.geometry.base.Point;

import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.MementoComposite;


/**
 * The type Composite is Composite Pattern.
 * This class let us make group and treat as an individual.
 * We can perform Group of IShape, and Composite can group others Composite.
 */
public class Composite extends Shape {
    private static final long serialVersionUID = 7118837371818614670L;

    /**
     * The Components IShape of this composite
     */
    private Vector<IShape> components = new Vector<IShape>();


    /**
     * Instantiates a new Composite.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public Composite(int x, int y, int width, int height) {
        super(x,
              y,
              new Point((x + x + width)/2, (y + y + height)/2),
              new Point((x + x + width)/2, (y + y + height)/2),
              width,
              height
              );

    }


    /**
     * Scale the composite and each of his components.
     *
     * @param factor the factor
     */
    @Override
    public void scale(double factor) {
        this.setHeight((int) (this.getHeight() * factor));
        this.setWidth((int) (this.getWidth()  * factor));

        for(IShape shape : components) {
            shape.scale(factor);
            int x = (int) ((shape.getPosition().getX() - this.getPosition().getX()) * factor);
            int y = (int) ((shape.getPosition().getY() - this.getPosition().getY()) * factor);
            shape.move(this.getPosition().getX() + x, this.getPosition().getY() + y);
        }


    }

    /**
     * Sets alpha for composite and each of his components.
     *
     * @param alpha the alpha
     */
    @Override
    public void setAlpha(double alpha) {
        super.setAlpha(alpha);
        for(IShape shape : components) shape.setAlpha(alpha);	
    }


    /**
     * Tell if Point Is inside the composite .
     *
     * @param p the p
     * @return the boolean
     */
    @Override
    public boolean isInside(Point p) {
        for(IShape shape : components) {
            if(shape.isInside(p))
                return true;
        }
        return false;
    }


    /**
     * Change the current Color of Composite, and so of all his components.
     *
     * @param r the r
     * @param g the g
     * @param b the b
     */
    @Override
    public void changeColor(int r, int g, int b) {
        super.changeColor(r, g, b);
        for(IShape shape : components) shape.changeColor(r, g, b);
    }


    /**
     * Rotate the composite and so of all his components.
     *
     * @param rotation the rotation
     */
    @Override
    public void rotate(double rotation) {
        for (IShape s : components)
            s.rotate(rotation);
    }

    /**
     * Move the composite and all his components.
     *
     * @param dx the dx
     * @param dy the dy
     */
    @Override
    public void move(int dx, int dy) {
        int pasX = 0, pasY = 0;

        int x = getPosition().getX(), y =getPosition().getY();

        for(IShape shape : components){
            int sX = shape.getPosition().getX() , sY = shape.getPosition().getY();
            pasX = dx - x;
            pasY = dy - y;
            shape.move(sX + pasX , sY + pasY );
        }
        super.move(dx, dy);

    }


    /**
     * Clone the composite, each components is also cloned.
     *
     * @return the shape
     */
    @Override
    public Shape clone() {
        Composite clone = (Composite) super.clone();
        Vector<IShape> c = new Vector<IShape>();
        for (IShape s : components){
            c.add(s.clone());
        }
        clone.components = c;
        return clone;
    }

    /**
     * Draw the composite on the draw surface.
     *
     * @param db          the db
     * @param drawSurface the draw surface
     */
    @Override
    public void draw(DrawBridge db, Object drawSurface) {
        for (IShape s : components)
            if( s != null) s.draw(db, drawSurface);

    }

    /**
     * Save the current state of Composite in a Memento .
     *
     * @return the memento
     */
    @Override
    public Memento save() {
        MementoComposite m = new MementoComposite();
        super.save(m);
        
        m.setCompositeMapMemento(new HashMap<IShape,Memento>());
        m.saveCompositeComponents(components);
        m.set(m,this.getPosition(),this.getRotationCenter(),
                this.getTranslationCenter(),this.getRotation(),
                getColorR(),getColorG(),getColorB(),getAlpha(),
                getWidth(),getHeight());

        return m;
    }

    /**
     * Restore the previous saved state from Memento.
     *
     * @param memento the memento
     */
    @Override
    public void restore(Memento memento) {
        this.components.clear();
        
        MementoComposite m = (MementoComposite) memento;
        super.restore(m);
        this.restoreComponent(m);


        
    }

    /**
     * Restore previous saved state of Components with their corresponding Memento.
     *
     * @param memento the memento
     */
    public void restoreComponent(MementoComposite memento) {
        for (IShape shape : memento.getCompositeMapMemento().keySet()){
            this.remove(shape);
            shape.restore(memento.getCompositeMapMemento().get(shape));
            this.add(shape);

        }
    }

    /**
     * Add a new component to composite.
     *
     * @param component the component
     */
    public void add(IShape component) {
        this.components.add(component);

    }

    /**
     * Minimal values (x,y,width,height) of Composite's components .
     *
     * @return the int [ ]
     */
    public int [] minComponents(){
        int min    = Integer.MAX_VALUE;
        int width  = Integer.MAX_VALUE;
        int min2   = Integer.MAX_VALUE;
        int height = Integer.MAX_VALUE;
        for (IShape s : components){
            if (s instanceof Composite) {
                if (min > ((Composite) s).minComponents()[0]) {
                    min = ((Composite) s).minComponents()[0];
                    width = ((Composite) s).minComponents()[1];
                }
                if (min2 > ((Composite) s).minComponents()[2]) {
                    min2 = ((Composite) s).minComponents()[2];
                    height = ((Composite) s).minComponents()[3];
                }
            }
            if (s.getPosition().getX() < min) {
                min = s.getPosition().getX();
                width = s.getWidth();
            }

            if (s.getPosition().getY() < min2) {
                min2 = s.getPosition().getY();
                height = s.getHeight();
            }

        }
        return new int[]{min, width, min2, height};
    }

    /**
     * Get components vector.
     *
     * @return the vector
     */
    public Vector<IShape> getComponents(){
        return this.components;
    }


    /**
     * Remove the component from this Composite.
     *
     * @param component the component
     */
    public void remove(IShape component) {
        this.components.remove(component);

    }

    /**
     * Clear the composite : size will be 0.
     */
    public void clear(){
        this.components.clear();
    }
    



}
