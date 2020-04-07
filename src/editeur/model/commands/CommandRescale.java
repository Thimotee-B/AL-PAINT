package editeur.model.commands;

import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;

public class CommandRescale extends Command {
    
    private double factor;

    public CommandRescale(Originator source, double factor){
        super(source);
        this.factor = factor;
    }
    
    @Override
    public void execute(){
        super.execute();
        if(this.source instanceof IShape)
            ((IShape) this.source).scale(this.factor);
        
    }

    @Override
    public void undo(){
        super.undo();
    }

}
