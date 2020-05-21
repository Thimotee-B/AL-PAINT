package editeur.model.commands;

import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;

/**
 * The type CommandRecolor can recolor the source and be undone.
 */
public class CommandRecolor extends Command{

    private final int r, g, b;

    /**
     * Instantiates a new Command recolor.
     *
     * @param source the source
     * @param r      the r
     * @param g      the g
     * @param b      the b
     */
    public CommandRecolor(Originator source, int r, int g, int b){
        super(source);
        this.r = r;
        this.g = g;
        this.b = b;
    }


    /**
     * Execute the command : recolor the source with rgb and save the current state of source in memento.
     */
    @Override
    public void execute(){
        super.execute();
        if(this.source instanceof IShape) 
            ((IShape) this.source).changeColor(this.r, this.g, this.b);
    }

    /**
     * Undo the command recolor and restore the source to his previous state with memento.
     */
    @Override
    public void undo(){
        super.undo();
    }
}
