package editeur.model.commands;

public interface ICommand {
    public void undo();
    public void execute();
}
