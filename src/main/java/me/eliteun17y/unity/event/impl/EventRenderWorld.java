package me.eliteun17y.unity.event.impl;

import me.eliteun17y.unity.event.Event;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderGlobal;

public class EventRenderWorld extends Event {
    public float partialTicks;
    public RenderGlobal renderGlobal;

    public EventRenderWorld(float partialTicks, RenderGlobal renderGlobal) {
        this.partialTicks = partialTicks;
        this.renderGlobal = renderGlobal;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public RenderGlobal getRenderGlobal() {
        return renderGlobal;
    }
}
