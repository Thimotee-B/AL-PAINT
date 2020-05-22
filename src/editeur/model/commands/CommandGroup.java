package editeur.model.commands;


import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;

import java.util.Vector;

/**
 * The type CommandGroup : can group into a composite and be undone.
 */
public class CommandGroup extends Command {

    /**
     * The composite group.
     */
    private Composite group;
    /**
     * The Selected shapes in a vector.
     */
    private final Vector<IShape> selected;
    /**
     * The Coordinates of selection.
     */
    private final int[] c;

    /**
     * Instantiates a new Command group.
     *
     * @param source      the source
     * @param selected    the selected
     * @param coordinates the coordinates
     */
    public CommandGroup(Originator source, Vector<IShape> selected, int[] coordinates){
        super(source);
        this.selected = selected;
        this.c = coordinates;
    }

    /**
     * Undo the command and restore the source to his previous state with memento.
     */
    @Override
    public void undo(){
        super.undo();
        group.clear();
    }

    /**
     * Execute the command : add the selected shapes and in a composite and add it to source and save the current state of source in memento.
     */
    @Override
    public void execute(){
        if(group == null) {
            group = new Composite(c[0], c[1], c[2], c[3]);
            for (IShape shape : selected)
                group.add(shape);
            if (this.source instanceof Composite)
                ((Composite) this.source).add(group);
        }
        super.execute();
    }

}
