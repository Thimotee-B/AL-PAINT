package editeur.model.commands;

public interface ICommand {
    void undo();
    void execute();
}
