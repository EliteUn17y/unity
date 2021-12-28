package me.eliteun17y.unity.event.impl;

import me.eliteun17y.unity.event.Event;

public class EventBlockOverlay extends Event {
    public Type type;

    public EventBlockOverlay(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        FIRE,
        WATER,
        BLOCK
    }
}