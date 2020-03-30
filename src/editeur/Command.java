package editeur;

public abstract class Command implements ICommand{
	
	private Memento memento;
	protected Originator source;
	
	public Command(Mementable source){
		this.source = source;
	}

	@Override
	public void undo(){
		this.source.restoreMemento(this.memento);
	}
	
	@Override
	public void execute(){
		this.memento=this.source.createMemento();
	}

}
