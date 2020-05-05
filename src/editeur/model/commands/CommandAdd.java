package editeur.model.commands;

import editeur.controller.Mediator;
import editeur.model.geometry.IShape;
import editeur.model.geometry.Composite;
import editeur.model.geometry.memento.Originator;

public class CommandAdd extends Command {
    
    private IShape toAdd;
    
    public CommandAdd(Originator source, IShape toAdd){
        super(source);
        this.toAdd = toAdd;
    }
    
    @Override
    public void undo(){
        super.undo();
        if (this.source instanceof Composite) {
            ((Composite) this.source).remove(toAdd);
            Mediator.getInstance().undoShapeAdd(toAdd);
        }
    }
    
    @Override
    public void execute(){
        super.execute();
        if (this.source instanceof Composite)
            ((Composite) this.source).add(toAdd);
    }

}
