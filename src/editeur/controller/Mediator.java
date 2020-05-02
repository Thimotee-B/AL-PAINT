package editeur.controller;

import java.util.Vector;

import editeur.model.commands.CareTaker;
import editeur.model.commands.Command;
import editeur.model.commands.CommandRescale;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Rectangle;
import editeur.view.AbstractApplication;

public class Mediator implements IMediator {
    
	//On peut en faire un singleton si on veut
    private static Mediator instance = new Mediator(null);
    
    private AbstractApplication app;
    private CareTaker           careTaker;
    private Vector<Integer>     selectedShapes;
    
    public  Mediator(AbstractApplication app) {
        this.app            = app;
        this.careTaker      = new CareTaker();
        this.selectedShapes = new Vector<Integer>(); 
    }
    
    @Override
    public void start() {
        Rectangle r = new Rectangle(20,50,100,100);
        r.changeColor(255, 165, 0);
        r.draw(app.getDrawBridge(), app.getToolBar().get()); // a changer avec observer et observ�
        app.getToolBar().addShape(r);
    }
    
	@Override
	public void group() {
		// TODO Auto-generated method stub
	}

	@Override
	public void unGroup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reColor() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ReScale(IShape shape, double factor) {
		Command cmd = new CommandRescale(shape, factor);
		cmd.execute();
		careTaker.add(cmd);
		shape.draw(app.getDrawBridge(), app.getToolBar().get());//Draw avec observer
	}

	@Override
	public void rotate() {
		// TODO Auto-generated method stub
		
	}
	
	public static Mediator getInstance() {
	    return instance;
	}

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void redo() {
        // TODO Auto-generated method stub
        
    }

	
	//Mouse events ? c'est un peu différent de java awt (la first gen javafx)
	//donc je sais pas si on peut hériter en javafx d'une classe mouse listener

}
