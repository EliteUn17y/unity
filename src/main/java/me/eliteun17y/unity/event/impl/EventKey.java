package me.eliteun17y.unity.event.impl;

import me.eliteun17y.unity.event.Event;

public class EventKey extends Event {
    public int key;

    public int getKey() {
        return key;
    }

    public EventKey(int key) {
        this.key = key;
    }
}
