package me.eliteun17y.unity.event.impl;

import me.eliteun17y.unity.event.Event;
import net.minecraft.block.material.Material;

public class EventRenderOverlay extends Event {
    public Material material;

    public EventRenderOverlay(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
