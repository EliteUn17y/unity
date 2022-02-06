package me.eliteun17y.unity.event.impl;

import me.eliteun17y.unity.event.Event;
import net.minecraft.entity.MoverType;

public class EventMove extends Event {
    public MoverType moverType;
    public double x;
    public double y;
    public double z;

    public EventMove(MoverType moverType, double x, double y, double z) {
        this.moverType = moverType;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
