package editeur.model.commands;

import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;

public class CommandRotate extends Command {

    private final double factor;

    public CommandRotate(Originator source, double factor) {
        super(source);
        this.factor = factor;
    }
    
    @Override
    public void execute(){
        super.execute();
        if(this.source instanceof IShape)
            ((IShape) this.source).rotate(this.factor);
    }

    @Override
    public void undo(){
        super.undo();
    }

}
