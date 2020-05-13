package editeur.model.commands;

import java.util.Vector;

public class CareTaker{
    
    private final Vector<ICommand> undo;
    private final Vector<ICommand> redo;
    
    public CareTaker() {
        undo = new Vector<ICommand>();
        redo = new Vector<ICommand>();
    }
    
    public void undo() {
        int last      = undo.size() - 1;
        if (undo.size() > 0) {
            ICommand cmd = undo.remove(last);
            cmd.undo();
            redo.add(cmd);
        }
    }
    
    public void redo() {
        int last      = redo.size() - 1;
        if(redo.size() > 0) {
            ICommand cmd = redo.remove(last);
            cmd.execute();
            undo.add(cmd);
        }
    }
    
    public void add (ICommand cmd) {
        undo.add(cmd);
        redo.clear();
    }
    
    
}
