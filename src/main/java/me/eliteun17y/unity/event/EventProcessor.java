package me.eliteun17y.unity.event;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.impl.EventInputUpdate;
import me.eliteun17y.unity.event.impl.EventKey;
import me.eliteun17y.unity.event.impl.EventRenderGame;
import me.eliteun17y.unity.event.impl.EventRenderWorld;
import me.eliteun17y.unity.module.Module;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
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
            }
        }
    }

    @SubscribeEvent
    public void onRenderGame(RenderGameOverlayEvent event) {
        EventRenderGame e = new EventRenderGame(event.getPartialTicks(), event.getResolution(), event.getType());
        Unity.EVENT_BUS.post(e);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        EventRenderWorld e = new EventRenderWorld(event.getPartialTicks(), event.getContext());
        Unity.EVENT_BUS.post(e);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        EventInputUpdate e = new EventInputUpdate(event.getMovementInput());
        Unity.EVENT_BUS.post(e);
    }
}
