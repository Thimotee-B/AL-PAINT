package editeur;

public class CommandDelete extends Command {
	
private Component toDelete;
	
	public CommandDelete(Originator source, Component toDelete){
		super(source);
		this.toDelete = toDelete;
	}
	
	@Override
	public void undo(){
	}
	
	@Override
	public void execute(){
	}

}
