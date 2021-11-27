package me.eliteun17y.unity.event;

import java.lang.reflect.Method;

public class Data {
    public Method method;
    public Object owner;
    public int priority;

    public Data(Method method, Object owner, int priority) {
        this.method = method;
        this.owner = owner;
        this.priority = priority;
    }
}
