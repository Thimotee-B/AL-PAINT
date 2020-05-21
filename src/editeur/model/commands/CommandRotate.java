package editeur.model.commands;

import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;

/**
 * The type CommandRotate can rotate the source of factor angle and be undone.
 */
public class CommandRotate extends Command {

    private final double factor;

    /**
     * Instantiates a new Command rotate.
     *
     * @param source the source
     * @param factor the factor
     */
    public CommandRotate(Originator source, double factor) {
        super(source);
        this.factor = factor;
    }

    /**
     * Execute the command rotate and save the current state of source in memento.
     */
    @Override
    public void execute(){
        super.execute();
        if(this.source instanceof IShape)
            ((IShape) this.source).rotate(this.factor);
    }

    /**
     * Undo the command rotate and restore the source to his previous state with memento.
     */
    @Override
    public void undo(){
        super.undo();
    }

}
