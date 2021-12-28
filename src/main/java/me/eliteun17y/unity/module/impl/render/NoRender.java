package me.eliteun17y.unity.module.impl.render;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventBlockOverlay;
import me.eliteun17y.unity.event.impl.EventRenderOverlay;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.chat.ChatUtil;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import net.minecraft.block.material.Material;
import org.lwjgl.input.Keyboard;

public class NoRender extends Module {
    public BooleanValue fire = new BooleanValue(this, "Fire", true);
    public BooleanValue water = new BooleanValue(this, "Water", true);
    public BooleanValue blocks = new BooleanValue(this, "Blocks", true);

    public NoRender() {
        super("No Render", "Doesn't render certain things.", Category.RENDER, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onEventBlockOverlay(EventBlockOverlay event) {
        if(event.getType() == EventBlockOverlay.Type.FIRE && fire.getObject()) {
            event.setCancelled(true);
        }
        if(event.getType() == EventBlockOverlay.Type.WATER && water.getObject()) {
            event.setCancelled(true);
        }
        if(event.getType() == EventBlockOverlay.Type.BLOCK && blocks.getObject()) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void onEventRenderOverlay(EventRenderOverlay event) {
        if(event.getMaterial() == Material.FIRE && fire.getObject()) {
            event.setCancelled(true);
        }
        if(event.getMaterial() == Material.WATER && water.getObject()) {
            event.setCancelled(true);
        }
    }
}
