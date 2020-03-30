package editeur;

public class CommandRotate extends Command {

	private double factor;

	public CommandRotate(Originator source, double factor) {
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
