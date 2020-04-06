package editeur.model.commands;

import editeur.model.geometry.Shape;
import editeur.model.geometry.Composite;
import editeur.model.geometry.memento.Originator;

public class CommandDelete extends Command {
	
	private Shape toDelete;
	
	public CommandDelete(Originator source, Shape toDelete){
		super(source);
		this.toDelete = toDelete;
	}
	
	@Override
	public void undo(){
		super.undo();
		if (this.source instanceof Composite) ((Composite) this.source).add(toDelete);
	}
	
	@Override
	public void execute(){
		super.execute();
		if (this.source instanceof Composite) ((Composite) this.source).remove(toDelete);
	}

}
