package me.eliteun17y.unity.event.impl;

import me.eliteun17y.unity.event.Event;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class EventRenderGame extends Event {
    public float partialTicks;
    public ScaledResolution scaledResolution;
    public RenderGameOverlayEvent.ElementType elementType;

    public EventRenderGame(float partialTicks, ScaledResolution scaledResolution, RenderGameOverlayEvent.ElementType elementType) {
        this.partialTicks = partialTicks;
        this.scaledResolution = scaledResolution;
        this.elementType = elementType;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    public RenderGameOverlayEvent.ElementType getElementType() {
        return elementType;
    }
}
