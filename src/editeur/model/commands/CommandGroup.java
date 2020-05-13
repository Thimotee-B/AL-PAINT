package editeur.model.commands;


import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;

import java.util.Vector;

public class CommandGroup extends Command {

    private Composite group;
    private final Vector<IShape> selected;
    private final int[] c;
    public CommandGroup(Originator source, Vector<IShape> selected, int[] coordinates){
        super(source);
        this.selected = selected;
        this.c = coordinates;
    }
    
    @Override
    public void undo(){
        super.undo();
        if (this.source instanceof Composite)
            ((Composite) this.source).remove(group);
        group.clear();
    }
    
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
