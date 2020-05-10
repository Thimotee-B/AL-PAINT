package editeur.model.geometry;



import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import editeur.model.draw.DrawBridge;
import editeur.model.geometry.base.Point;
import editeur.model.geometry.base.Rectangle;
import editeur.model.geometry.base.SimplePolygon;
import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.MementoComposite;


public class Composite extends Shape {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7118837371818614670L;
    
    private Vector<IShape> components = new Vector<IShape>(); 
    private int height;
    private int width;

    public Composite(int x, int y, int height, int width) {
        super(x,
              y,
              new Point(x + width/2, y + height/2),
              new Point(x + width/2, y + height/2)
              );
        
        this.height = height;
        this.width  = width;
    }
    
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
    

    @Override
    public void scale(double factor) {
        this.height = (int) (this.height * factor);
        this.width  = (int) (this.width  * factor);
        for(IShape shape : components) {
            shape.scale(factor);
            int x = (int) ((shape.getPosition().getX() - this.getPosition().getX()) * factor);
            int y = (int) ((shape.getPosition().getY() - this.getPosition().getY()) * factor);
            shape.move(this.getPosition().getX() + x, this.getPosition().getY() + y);
        }


    }
    
    @Override
    public void setAlpha(double alpha) {
        super.setAlpha(alpha);
        for(IShape shape : components) shape.setAlpha(alpha);	
    }

    @Override
    public void setRotationCenter(Point p) {
        //TODO: dd
    }

    @Override
    public boolean isInside(Point p) {
        for(IShape shape : components) {
            if(shape.isInside(p))
                return true;
        }
        return false;
    }



    @Override
    public void changeColor(int r, int g, int b) {
        super.changeColor(r, g, b);
        for(IShape shape : components) shape.changeColor(r, g, b);
    }

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
                if (s instanceof  Rectangle)
                    width = ((Rectangle) s).getWidth();
                if (s instanceof  Composite)
                    width = ((Composite) s).getWidth();
                if (s instanceof  SimplePolygon)
                    width =  (int) ((SimplePolygon) s).getSideSize();
            }

            if (s.getPosition().getY() < min2) {
                min2 = s.getPosition().getY();
                if (s instanceof  Rectangle)
                    height = ((Rectangle) s).getHeight();
                if (s instanceof  Composite)
                    height = ((Composite) s).getHeight();
                if (s instanceof  SimplePolygon)
                    height =  (int) ((SimplePolygon) s).getSideSize();
            }

        }
        return new int[]{min, width, min2, height};
    }



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
    
    public void add(IShape component) {
        this.components.add(component);
        
    }
    
    public Vector<IShape> getComponents(){
        return this.components;
    }

    
    public void remove(IShape component) {
        this.components.remove(component);
        
    }

    public void clear(){
        this.components.clear();
    }

    @Override
    public Memento save() {
        MementoComposite m = new MementoComposite();
        super.save(m);
        
        m.setCompositeMapMemento(new HashMap<IShape,Memento>());
        m.saveCompositeComponents(components);
        m.set(m,this.getPosition(),this.getRotationCenter(),this.getTranslationCenter(),this.getRotation(),getColorR(),getColorG(),getColorB(),getAlpha());
        m.setHeight(this.height);
        m.setWidth(this.width);
        return m;
    }

    @Override
    public void restore(Memento memento) {
        this.components.clear();
        
        MementoComposite m = (MementoComposite) memento;
        super.restore(m);
        this.restoreComponent(m);
        System.out.println(components.size());
        this.width  = m.getWidth();
        this.height = m.getHeight();

        
    }
    
    public void restoreComponent(MementoComposite memento) {
        for (IShape shape : memento.getCompositeMapMemento().keySet()){
            this.remove(shape);
            shape.restore(memento.getCompositeMapMemento().get(shape));
            this.add(shape);
        }

        /*for (IShape shape : (Vector<IShape>)this.components.clone()){
            if (memento.getCompositeMapMemento().get(shape) == null) {
                this.remove(shape);
                continue;
            }
            else
                shape.restore(memento.getCompositeMapMemento().get(shape));
            IShape newshape = shape.clone();
            this.add(newshape);
            this.remove(shape);
        }*/
    }
    
    
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

    @Override
    public void draw(DrawBridge db, Object drawSurface) {
        for (IShape s : components)
            if( s != null) s.draw(db, drawSurface);
        
    }


}
