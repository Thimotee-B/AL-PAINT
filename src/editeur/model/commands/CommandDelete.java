package editeur.model.commands;

import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;

/**
 * The type CommandDelete delete the toDeleteShape from the source : can be undone.
 */
public class CommandDelete extends Command {

    private final IShape toDelete;

    /**
     * Instantiates a new Command delete.
     *
     * @param source   the source
     * @param toDelete the shape to delete
     */
    public CommandDelete(Originator source, IShape toDelete){
        super(source);
        this.toDelete = toDelete;
    }

    /**
     * Undo the command delete and restore the source to his previous state with memento.
     */
    @Override
    public void undo(){
        super.undo();
    }

    /**
     * Execute the command : delete the shape from source and save the current state of source in memento.
     */
    @Override
    public void execute(){
        super.execute();
        if (this.source instanceof Composite) 
            ((Composite) this.source).remove(toDelete);
    }

}
