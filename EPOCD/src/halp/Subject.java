package halp;

public interface Subject {
	public void addObserver(Observer o);
	//public void removeObserver(Observer o); não acho que vamos remover...
	public void notifyObservers();
}
