package editeur.model.commands;

import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;

/**
 * The type CommandRescale can rescale the source of factor times, can be undone.
 */
public class CommandRescale extends Command {

    private final double factor;

    /**
     * Instantiates a new Command rescale.
     *
     * @param source the source
     * @param factor the factor
     */
    public CommandRescale(Originator source, double factor){
        super(source);
        this.factor = factor;
    }

    /**
     * Execute the command: rescale the source and save the current state of source in memento.
     */
    @Override
    public void execute(){
        super.execute();
        if(this.source instanceof IShape)
            ((IShape) this.source).scale(this.factor);
        
    }

    /**
     * Undo the command rescale and restore the source to his previous state with memento.
     */
    @Override
    public void undo(){
        super.undo();
    }

}
