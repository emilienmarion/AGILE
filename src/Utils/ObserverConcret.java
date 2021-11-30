package Utils;

import java.util.ArrayList;
import java.util.List;

public class ObserverConcret implements Observable{

    private List<Observer> observer = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o: observers) {
            o.update(this);
        }
    }
}