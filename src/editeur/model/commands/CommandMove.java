package editeur.model.commands;

import editeur.model.geometry.Shape;
import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;

/**
 * The type CommandMove can move the source to position dx,dy : can be undone.
 */
public class CommandMove extends Command {
    private final int dx;
    private final int dy;

    /**
     * Instantiates a new Command move.
     *
     * @param source the source
     * @param dx     the dx
     * @param dy     the dy
     */
    public CommandMove(Originator source, int dx, int dy) {
        super(source);
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Execute the command : move the source to pos dx,dy , and save the current state of source in memento.
     */
    @Override
    public void execute(){
        super.execute();
        if(this.source instanceof Shape) {
            ( (IShape) this.source).move(this.dx, this.dy);
        }

    }

    /**
     * Undo the command move and restore the source to his previous state with memento.
     */
    @Override
    public void undo(){
        super.undo();
    }

}
