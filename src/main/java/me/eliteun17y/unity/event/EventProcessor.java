package me.eliteun17y.unity.event;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.impl.*;
import me.eliteun17y.unity.module.Module;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.input.Keyboard;

public class EventProcessor {
    public EventProcessor() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKey(KeyInputEvent event) {
        if (Keyboard.isCreated()) {
            if (Keyboard.getEventKeyState()) {
                int keyCode = Keyboard.getEventKey();
                if (keyCode <= 0)
                    return;

                for(Module m : Unity.instance.moduleManager.getModules())
                    if(m.getKey() == keyCode)
                        m.toggle();

                EventKey e = new EventKey(keyCode);
                Unity.EVENT_BUS.post(e);
                if(e.isCancelled())
                    event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onRenderGame(RenderGameOverlayEvent event) {
        EventRenderGame e = new EventRenderGame(event.getPartialTicks(), event.getResolution(), event.getType());
        Unity.EVENT_BUS.post(e);
        if(e.isCancelled())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        EventRenderWorld e = new EventRenderWorld(event.getPartialTicks(), event.getContext());
        Unity.EVENT_BUS.post(e);
        if(e.isCancelled())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        EventInputUpdate e = new EventInputUpdate(event.getMovementInput());
        Unity.EVENT_BUS.post(e);
        if(e.isCancelled())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onBlockOverlay(RenderBlockOverlayEvent event) {
        EventBlockOverlay e = new EventBlockOverlay(EventBlockOverlay.Type.valueOf(event.getOverlayType().name()));
        Unity.EVENT_BUS.post(e);
        if(e.isCancelled())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onOverlay(EntityViewRenderEvent.FogDensity event) {
        EventRenderOverlay e = new EventRenderOverlay(event.getState().getMaterial());
        Unity.EVENT_BUS.post(e);
        if(e.isCancelled()) {
            event.setDensity(0);
            event.setCanceled(true);
        }
    }
}
