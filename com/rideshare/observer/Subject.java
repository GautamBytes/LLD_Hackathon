package com.rideshare.observer;

/**
 * Subject interface for registering/removing observers and notifying them.
 */
public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(String message);
}
