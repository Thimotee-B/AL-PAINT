package editeur.model.geometry.memento;

import java.util.Map;

import editeur.model.geometry.IShape;

public class MementoComposite extends MementoShape {
	
	public Map<IShape, Memento> compositeMapMemento;
	public int height, width;

}
