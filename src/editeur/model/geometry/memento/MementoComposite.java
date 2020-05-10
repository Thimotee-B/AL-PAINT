package editeur.model.geometry.memento;

import java.util.Map;
import java.util.Vector;

import editeur.model.geometry.IShape;

public class MementoComposite extends MementoShape {
	
	private Map<IShape, Memento> compositeMapMemento;

	
	public Map<IShape, Memento> getCompositeMapMemento() {
		return compositeMapMemento;
	}
	public void setCompositeMapMemento(Map<IShape, Memento> compositeMapMemento) {
		this.compositeMapMemento = compositeMapMemento;
	}
	
	public void saveCompositeComponents(Vector<IShape> vecShape) {
		for (IShape shape : vecShape)
			this.compositeMapMemento.put(shape,shape.save());
	}
	

	
	

}
