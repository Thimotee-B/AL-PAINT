package editeur.model.commands;

import java.util.Vector;

/**
 * The type CareTaker : work with the Memento Pattern and Command Pattern,
 * track the commands and add it to undo and redo vectors to further use.
 */
public class CareTaker{

    /**
     * The Undo vector contains commands which can be undo.
     */
    private final Vector<ICommand> undo;
    /**
     * The Redo vector contains commands which can be redo.
     */
    private final Vector<ICommand> redo;

    /**
     * Instantiates a new Care taker.
     */
    public CareTaker() {
        undo = new Vector<ICommand>();
        redo = new Vector<ICommand>();
    }

    /**
     * Simply undo the last command of vector undo and add it to redo vector.
     */
    public void undo() {
        int last      = undo.size() - 1;
        if (undo.size() > 0) {
            ICommand cmd = undo.remove(last);
            cmd.undo();
            redo.add(cmd);
        }
    }

    /**
     * Simply redo the last command of vector redo and add it to undo vector.
     */
    public void redo() {
        int last      = redo.size() - 1;
        if(redo.size() > 0) {
            ICommand cmd = redo.remove(last);
            cmd.execute();
            undo.add(cmd);
        }
    }

    /**
     * Add a new command which has been currented executed to caretaker for further redo undo.
     *
     * @param cmd the cmd
     */
    public void add (ICommand cmd) {
        undo.add(cmd);
        redo.clear();
    }
    
    
}
