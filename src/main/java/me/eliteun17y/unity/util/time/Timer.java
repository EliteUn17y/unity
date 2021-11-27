package me.eliteun17y.unity.util.time;

public class Timer {
    public long timePassed = 0;

    public boolean hasTimePassed(long time) {
        return System.currentTimeMillis() >= timePassed + time;
    }

    public void reset() {
        timePassed = System.currentTimeMillis();
    }
}
