package editeur.model.commands;

import editeur.controller.Mediator;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.MementoComposite;
import editeur.model.geometry.memento.Originator;

public abstract class Command implements ICommand{
    
    protected  Memento memento;
    protected Originator source;
    
    public Command(Originator source){
        this.source = source;
    }

    @Override
    public void undo() {
        this.source.restore(this.memento);
        Mediator.getInstance().clearView();
        Mediator.getInstance().Notify();
    }
    
    @Override
    public void execute(){
        this.memento = this.source.save();
    }

}
