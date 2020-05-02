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
    public void changeColor(int r, int g, int b) {
        super.changeColor(r, g, b);
        for(IShape shape : components) shape.changeColor(r, g, b);
    }
    
    @Override
    public void move(int dx, int dy) {
        for(IShape shape : components) shape.move(dx,  dy);
        translate(dx, dy);
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
        
        this.width  = m.getWidth();
        this.height = m.getHeight();
        
    }
    
    public void restoreComponent(MementoComposite memento) {
        Iterator<Map.Entry<IShape, Memento>> i = memento.getCompositeMapMemento().entrySet().iterator();
        
        while (i.hasNext()) {
            IShape shape = (IShape) i.next();
            this.add(shape);
            shape.restore(memento.getCompositeMapMemento().get(shape));
        }
    }
    
    
    @Override
    public Shape clone() {
        return null;
    }

    @Override
    public void draw(DrawBridge db, Object drawSurface) {
        // TODO Auto-generated method stub
        
    }


}
