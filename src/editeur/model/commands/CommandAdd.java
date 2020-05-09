package editeur.model.commands;

import editeur.controller.Mediator;
import editeur.model.geometry.IShape;
import editeur.model.geometry.Composite;
import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.MementoComposite;
import editeur.model.geometry.memento.Originator;

import javax.print.attribute.standard.Media;

public class CommandAdd extends Command {
    
    private IShape toAdd;
    
    public CommandAdd(Originator source, IShape toAdd){
        super(source);
        this.toAdd = toAdd;
    }
    
    @Override
    public void undo(){
        /*if (this.source instanceof Composite) {
            ((Composite) this.source).remove(toAdd);
            Mediator.getInstance().undoShapeAdd(toAdd);
        }*/
        super.undo();
    }
    
    @Override
    public void execute(){
        super.execute();
        if (this.source instanceof Composite)
            ((Composite) this.source).add(toAdd);
    }

}
