package editeur.model.commands;

import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Rectangle;
import editeur.model.geometry.memento.Originator;

/**
 * The type CommandRoundBorders can make borders rounds if source is able to, can be undone.
 */
public class CommandRoundBorders extends Command {

    private final int roundWidth;
    private final int roundHeight;

    /**
     * Instantiates a new Command round borders.
     *
     * @param source      the source
     * @param roundWidth  the round width
     * @param roundHeight the round height
     */
    public CommandRoundBorders(Originator source, int roundWidth, int roundHeight){
        super(source);
        this.roundWidth  = roundWidth;
        this.roundHeight = roundHeight;
    }

    /**
     * Execute the command : round the borders if possible and save the current state of source in memento.
     */
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

    /**
     * Undo the command roundborders and restore the source to his previous state with memento.
     */
    @Override
    public void undo(){
        super.undo();
    }

}
