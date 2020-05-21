package editeur.model.commands;

/**
 * The interface ICommand represents the Pattern command.
 * Encapsulate High level operations which can be undone.
 */
public interface ICommand {
    /**
     * Undo the command
     */
    void undo();

    /**
     * Execute the command
     */
    void execute();
}
