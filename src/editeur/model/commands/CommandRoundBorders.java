package editeur.model.commands;

import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Rectangle;
import editeur.model.geometry.memento.Originator;

public class CommandRoundBorders extends Command {

    private final int roundWidth;
    private final int roundHeight;

    public CommandRoundBorders(Originator source, int roundWidth, int roundHeight){
        super(source);
        this.roundWidth  = roundWidth;
        this.roundHeight = roundHeight;
    }
    
    @Override
    public void execute(){
        super.execute();
        if(this.source instanceof Rectangle) {
            ((Rectangle) this.source).setRoundWidth(roundWidth);
            ((Rectangle) this.source).setRoundHeight(roundHeight);
        }
        if(this.source instanceof Composite){
            Composite c = (Composite) this.source;

            for (IShape s : c.getComponents())
                if (!(s instanceof  Rectangle))
                    return;

            for (IShape s : c.getComponents()){
                ((Rectangle) s).setRoundWidth(roundWidth);
                ((Rectangle) s).setRoundHeight(roundHeight);
            }

        }
        
    }

    @Override
    public void undo(){
        super.undo();
    }

}
