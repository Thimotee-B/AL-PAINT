package editeur.model.commands;


import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;


/**
 * The type CommandUngroup can ungroup a composite group and add it to source : can be undone.
 */
public class CommandUngroup extends Command {

    private final Composite group;

    /**
     * Instantiates a new Command ungroup.
     *
     * @param source the source
     * @param group  the group
     */
    public CommandUngroup(Originator source, Composite group){
        super(source);
        this.group = group;
    }

    /**
     * Undo the command ungroup and restore the source to his previous state with memento.
     */
    @Override
    public void undo(){
        if (this.source instanceof Composite)
            ((Composite) this.source).add(group);

        super.undo();
    }

    /**
     * Execute the command ungroup : remove the group and add its object to source and save the current state of source in memento.
     */
    @Override
    public void execute(){
        super.execute();
        for (IShape s : group.getComponents())
            ((Composite) this.source).add(s);

        ((Composite) this.source).remove(group);

    }

}
