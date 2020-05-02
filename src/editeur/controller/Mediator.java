package editeur.controller;

import java.util.Vector;

import editeur.model.commands.CareTaker;
import editeur.model.commands.Command;
import editeur.model.commands.CommandAdd;
import editeur.model.commands.CommandDelete;
import editeur.model.commands.CommandMove;
import editeur.model.commands.CommandRescale;
import editeur.model.commands.CommandRotate;
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
        r.draw(app.getDrawBridge(), app.getToolBar().get()); // TODO:a changer avec observer et observer
        app.getToolBar().addShape(r);
    }
    
    @Override
    public void move(IShape shape, int dx, int dy) {
        Command cmd = new CommandMove(shape, dx, dy);
        cmd.execute();
        careTaker.add(cmd);
        shape.draw(app.getDrawBridge(), app.getToolBar().get());//TODO: Draw avec observer
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
		shape.draw(app.getDrawBridge(), app.getToolBar().get());//TODO:Draw avec observer
	}

	@Override
	public void rotate(IShape shape, double factor) {
	      Command cmd = new CommandRotate(shape, factor);
	      cmd.execute();
	      careTaker.add(cmd);
	      //TODO: redraw observer
	}
	
	public static Mediator getInstance() {
	    return instance;
	}

    @Override
    public void undo() {
        careTaker.undo();
        //TODO: tout redraw
        
    }

    @Override
    public void redo() {
        careTaker.redo();
       //TODO: Tout redraw;
        
    }

    @Override
    public void add(IShape shapes, IShape toAdd) {
        Command cmd = new CommandAdd(shapes, toAdd);
        cmd.execute();
        careTaker.add(cmd);
        //TODO: update shapes observer
        
    }
    @Override
    public void delete(IShape shapes, IShape toDelete) {
        Command cmd = new CommandDelete(shapes, toDelete);
        cmd.execute();
        careTaker.add(cmd);
        //TODO: update shapes observer
    }


   

	
	//Mouse events ? c'est un peu différent de java awt (la first gen javafx)
	//donc je sais pas si on peut hériter en javafx d'une classe mouse listener

}
