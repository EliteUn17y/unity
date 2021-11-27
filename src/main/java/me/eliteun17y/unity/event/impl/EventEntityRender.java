package me.eliteun17y.unity.event.impl;

import me.eliteun17y.unity.event.Event;
import net.minecraft.entity.Entity;

public class EventEntityRender extends Event {
    public Entity entity;
    public double x;
    public double y;
    public double z;
    public double yaw;
    public double partialTicks;

    public EventEntityRender(Entity entity, double x, double y, double z, double yaw, double partialTicks) {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.partialTicks = partialTicks;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(double partialTicks) {
        this.partialTicks = partialTicks;
    }
}
