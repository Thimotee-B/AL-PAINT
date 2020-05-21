package editeur.model.commands;

import editeur.controller.Mediator;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.MementoComposite;
import editeur.model.geometry.memento.Originator;

/**
 * The type Command represents the Command Pattern and Work with Memento for undo-redo easily with preserved Encapsulation.
 * Store the memento of the Originator source.
 */
public abstract class Command implements ICommand{
    protected  Memento memento;
    protected Originator source;

    /**
     * Instantiates a new Command.
     *
     * @param source the source
     */
    public Command(Originator source){
        this.source = source;
    }

    /**
     * Undo the command and restore the source to his previous state with memento.
     */
    @Override
    public void undo() {
        this.source.restore(this.memento);
    }

    /**
     * Execute the command and save the current state of source in memento.
     */
    @Override
    public void execute(){
        this.memento = this.source.save();
    }

}
