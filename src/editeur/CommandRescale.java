package editeur;

public class CommandScale extends Command {
	
	private double factor;

	public CommandRescale(Originator source, double factor){
		super(source);
		this.factor=factor;
	}
	
	@Override
	public void execute(){
	}

	@Override
	public void undo(){
	}

}
