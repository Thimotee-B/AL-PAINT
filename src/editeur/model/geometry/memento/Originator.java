package editeur.model.geometry.memento;

public interface Originator {
	
	Memento save();
	
	void restore(Memento memento);
}
