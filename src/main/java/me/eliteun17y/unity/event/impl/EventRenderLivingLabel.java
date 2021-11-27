package me.eliteun17y.unity.event.impl;

import me.eliteun17y.unity.event.Event;
import net.minecraft.entity.Entity;

public class EventRenderLivingLabel extends Event {
    public Entity entity;
    public String string;
    public double x;
    public double y;
    public double z;
    public double maxDistance;

    public EventRenderLivingLabel(Entity entity, String string, double x, double y, double z, double maxDistance) {
        this.entity = entity;
        this.string = string;
        this.x = x;
        this.y = y;
        this.z = z;
        this.maxDistance = maxDistance;
    }
}
