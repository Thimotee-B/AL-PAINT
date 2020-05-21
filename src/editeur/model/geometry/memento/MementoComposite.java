package editeur.model.geometry.memento;

import java.util.Map;
import java.util.Vector;

import editeur.model.geometry.IShape;

/**
 * The type MementoComposite extends MementoShape and contributes on saving a composite.
 */
public class MementoComposite extends MementoShape {

	/**
	 * The Composite map memento : we store for each shape its memento.
	 */
	private Map<IShape, Memento> compositeMapMemento;


	/**
	 * Gets composite map memento.
	 *
	 * @return the composite map memento
	 */
	public Map<IShape, Memento> getCompositeMapMemento() {
		return compositeMapMemento;
	}

	/**
	 * Sets composite map memento.
	 *
	 * @param compositeMapMemento the composite map memento
	 */
	public void setCompositeMapMemento(Map<IShape, Memento> compositeMapMemento) {
		this.compositeMapMemento = compositeMapMemento;
	}

	/**
	 * Save composite components.
	 *
	 * @param vecShape the vector of Shape to save.
	 */
	public void saveCompositeComponents(Vector<IShape> vecShape) {
		for (IShape shape : vecShape)
			this.compositeMapMemento.put(shape,shape.save());
	}
	

	
	

}
