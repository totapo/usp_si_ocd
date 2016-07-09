package halp;
//interface utilizada para aplicação do padrão de projeto: Observer
public interface Subject {
	public void addObserver(Observer o);
	public void notifyObservers();
}
