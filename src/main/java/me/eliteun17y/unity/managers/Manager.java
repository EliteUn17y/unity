package me.eliteun17y.unity.managers;

import java.util.ArrayList;
import java.util.Collection;

public class Manager<T> {
    public ArrayList<T> REGISTRY = new ArrayList<>();

    public boolean add(T obj) {
        return REGISTRY.add(obj);
    }

    public boolean addAll(Collection<T> obj) {
        return REGISTRY.addAll(obj);
    }

    public boolean remove(T obj) {
        return REGISTRY.remove(obj);
    }

    public boolean contains(T obj) {
        return REGISTRY.contains(obj);
    }

    public T get(T obj) {
        return REGISTRY.stream().filter(friend -> friend.equals(obj)).findFirst().get();
    }

    public T get(int index) {
        return REGISTRY.get(index);
    }

    public ArrayList<T> getRegistry() {
        return REGISTRY;
    }

    public void setRegistry(ArrayList<T> REGISTRY) {
        this.REGISTRY = REGISTRY;
    }
}
