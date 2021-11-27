package me.eliteun17y.unity.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class EventBus {
    public ArrayList<Data> REGISTRY = new ArrayList<>();

    public void register(Object object) {
        for(Method method : object.getClass().getDeclaredMethods()) {
            if(isGoodMethod(method)) {
                REGISTRY.add(new Data(method, object, 0));
            }
        }

        REGISTRY.sort(new EventBusComparator());
    }

    public void register(Object object, int priority) {
        for(Method method : object.getClass().getDeclaredMethods()) {
            if(isGoodMethod(method)) {
                REGISTRY.add(new Data(method, object, priority));
            }
        }

        REGISTRY.sort(new EventBusComparator());
    }
    public void unregister(Object object) {
        REGISTRY.removeIf(data -> data.owner.equals(object));
    }

    public void post(Event event) {
        for(int i = 0; i < REGISTRY.size(); i++) {
            Data data = REGISTRY.get(i);
            Method method = data.method;
            Object owner = data.owner;
            if(method.isAnnotationPresent(Subscribe.class) && method.getParameterTypes().length == 1) {
                if(method.getParameterTypes()[0] == event.getClass()) {
                    try {
                        method.invoke(owner, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean isGoodMethod(Method method) {
        return method.isAnnotationPresent(Subscribe.class) && method.getParameterTypes().length == 1;
    }

    public static class EventBusComparator implements Comparator<Data> {
        @Override
        public int compare(Data o1, Data o2) {
            return o1.priority >= o2.priority ? -1 : 1;
        }
    }
}
