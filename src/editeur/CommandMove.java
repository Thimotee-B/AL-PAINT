package editeur;

public class CommandMove extends Command {
	
	private int dx;
	private int dy;
	
	public CommandMove(Originator source, int dx, int dy) {
		super(source);
		this.dx=dx;
		this.dy=dy;
	}
	
	@Override
	public void execute(){
	}

	@Override
	public void undo(){
	}

}
