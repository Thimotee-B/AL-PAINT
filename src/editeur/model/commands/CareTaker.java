package editeur.model.commands;

import java.util.Vector;

public class CareTaker{
    
    private Vector<ICommand> undo, redo;
    
    public CareTaker() {
        undo = new Vector<ICommand>();
        redo = new Vector<ICommand>();
    }
    
    public void undo() {
        int last      = undo.size() - 1;
        ICommand cmd  = undo.remove(last);
        cmd.undo();
        redo.add(cmd);
    }
    
    public void redo() {
        int last      = undo.size() - 1;
        ICommand cmd  = redo.remove(last);
        cmd.undo();
        undo.add(cmd);
    }
    
    public void add (ICommand cmd) {
        undo.add(cmd);
        redo.clear();
    }
    
    
}
