package editeur.model.commands;


import editeur.controller.Mediator;
import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;

import javax.print.attribute.standard.Media;
import java.util.Vector;

public class CommandGroup extends Command {

    private Composite group;
    private Vector<IShape> selected;
    private int[] c;
    public CommandGroup(Originator source, Vector<IShape> selected, int[] coordinates){
        super(source);
        this.selected    = (Vector<IShape>) selected.clone();
        this.c = coordinates;
    }
    
    @Override
    public void undo(){
        for (IShape s : group.getComponents()) {
            ((Composite) this.source).add(s);
            group.remove(s);
        }
        super.undo();
    }
    
    @Override
    public void execute(){
        IShape s = selected.get(0);
        group = new Composite(c[0], c[1], c[2], c[3]);
        for (IShape shape : selected) group.add(shape);
        if (this.source instanceof Composite) {
            ((Composite) this.source).add(group);
        }

        super.execute();
    }

}
