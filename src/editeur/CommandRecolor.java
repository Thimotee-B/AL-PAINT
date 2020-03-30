package editeur;

public class CommandRecolor extends Command{
	
	private int b;
	private int g;
	private int r;

	public CommandRecolor(Originator source, int r, int g, int b){
		super(source);
		this.r = r;
		this.b = b;
		this.g = g;
	}
	
	@Override
	public void undo(){
	}

	@Override
	public void execute(){
	}

}
