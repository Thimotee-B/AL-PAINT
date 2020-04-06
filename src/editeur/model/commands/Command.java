package editeur.model.commands;

import editeur.model.geometry.memento.Memento;
import editeur.model.geometry.memento.Originator;

public abstract class Command implements ICommand{
	
	private Memento memento;
	protected Originator source;
	
	public Command(Originator source){
		this.source = source;
	}

	@Override
	public void undo(){
		this.source.restore(this.memento);
	}
	
	@Override
	public void execute(){
		this.memento = this.source.save();
	}

}
