package editeur.model.commands;


import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;


public class CommandUngroup extends Command {

    private final Composite group;
    public CommandUngroup(Originator source, Composite group){
        super(source);
        this.group = group;
    }
    
    @Override
    public void undo(){
        if (this.source instanceof Composite)
            ((Composite) this.source).add(group);

        super.undo();
    }
    
    @Override
    public void execute(){
        super.execute();
        for (IShape s : group.getComponents())
            ((Composite) this.source).add(s);

        ((Composite) this.source).remove(group);

    }

}
