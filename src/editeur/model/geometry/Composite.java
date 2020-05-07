package editeur.model.geometry;



import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import editeur.model.draw.DrawBridge;
import editeur.model.geometry.base.Point;
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
        for(IShape shape : components) shape.scale(factor);

    }
    
    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
        for(IShape shape : components) shape.setAlpha(alpha);	
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
        //this.components.clear();
        
        MementoComposite m = (MementoComposite) memento;
        super.restore(m);
        this.restoreComponent(m);
        System.out.println(components.size());
        this.width  = m.getWidth();
        this.height = m.getHeight();

        
    }
    
    public void restoreComponent(MementoComposite memento) {
        this.components.clear();
        for (IShape shape : memento.getCompositeMapMemento().keySet()){
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
