package editeur.model.commands;

import editeur.model.geometry.Shape;
import editeur.model.geometry.Composite;
import editeur.model.geometry.memento.Originator;

public class CommandAdd extends Command {
    
    private Shape toAdd;
    
    public CommandAdd(Originator source, Shape toAdd){
        super(source);
        this.toAdd = toAdd;
    }
    
    @Override
    public void undo(){
        super.undo();
        if (this.source instanceof Composite) ((Composite) this.source).remove(toAdd);
    }
    
    @Override
    public void execute(){
        super.execute();
        if (this.source instanceof Composite) ((Composite) this.source).add(toAdd);
    }

}
