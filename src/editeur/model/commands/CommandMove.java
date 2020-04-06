package editeur.model.commands;

import editeur.model.geometry.Shape;
import editeur.model.geometry.IShape;
import editeur.model.geometry.memento.Originator;

public class CommandMove extends Command {
	
	private int dx, dy;
	
	public CommandMove(Originator source, int dx, int dy) {
		super(source);
		this.dx = dx;
		this.dy = dy;
	}
	
	@Override
	public void execute(){
		super.execute();
		if(this.source instanceof Shape) {
			super.execute();
			( (IShape) this.source).move(this.dx, this.dy);
		}
	}

	@Override
	public void undo(){
		super.undo();
	}

}
