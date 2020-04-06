package editeur.model.geometry.base;

import editeur.model.geometry.Shape;
import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.MementoRectangle;

public class Rectangle extends Shape{

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
    public void scale(double factor){
        this.length = (int) (this.length * factor);
        this.width  = (int) (this.width  * factor);
        return;
    }


    @Override
    public Memento save(){
        MementoRectangle mem = new MementoRectangle();
        return mem;
    }

    @Override
    public void restore(Memento memento){
    }
    
    @Override
    public Shape clone(){
        return super.clone();
    }



}
