package editeur.model.commands;


import editeur.model.geometry.IShape;
import editeur.model.geometry.Composite;

import editeur.model.geometry.memento.Originator;


/**
 * The type CommandAdd add a shape to the source when executed, can be undone.
 */
public class CommandAdd extends Command {

    private final IShape toAdd;

    /**
     * Instantiates a new Command add.
     *
     * @param source the source
     * @param toAdd  the shape to add
     */
    public CommandAdd(Originator source, IShape toAdd){
        super(source);
        this.toAdd = toAdd;
    }

    /**
     * Undo the command and restore the source to his previous state with memento.
     */
    @Override
    public void undo(){
        super.undo();
    }

    /**
     * Execute the command : add the shape toAdd to source , and save the current state of source in memento.
     */
    @Override
    public void execute(){
        super.execute();
        if (this.source instanceof Composite)
            ((Composite) this.source).add(toAdd);
    }

}
