package editeur.model.commands;

import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;

public class CommandRecolor extends Command{
    
    private final int r;
    private final int g;
    private final int b;

    public CommandRecolor(Originator source, int r, int g, int b){
        super(source);
        this.r = r;
        this.g = g;
        this.b = b;
    }
    

    @Override
    public void execute(){
        super.execute();
        if(this.source instanceof IShape) 
            ((IShape) this.source).changeColor(this.r, this.g, this.b);
    }

    @Override
    public void undo(){
        super.undo();
    }
}
