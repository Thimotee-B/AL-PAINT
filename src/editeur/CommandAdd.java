package editeur;

public class CommandAdd extends Command {
	
	private Component toAdd;
	
	public CommandAdd(Orginator source, Component toAdd){
		super(source);
		this.toAdd = toAdd;
	}
	
	@Override
	public void undo(){
	}
	
	@Override
	public void execute(){
	}

}
