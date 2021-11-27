package me.eliteun17y.unity.event;

public class Event {
    public boolean cancelled = false;
    public Direction direction = Direction.INCOMING;
    public Era era = Era.PRE;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Era getEra() {
        return era;
    }

    public void setEra(Era era) {
        this.era = era;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isPre() {
        return era == Era.PRE;
    }

    public boolean isPost() {
        return era == Era.POST;
    }
}
