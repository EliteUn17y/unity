package me.eliteun17y.unity.module.impl.render;

import me.eliteun17y.unity.event.Direction;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventPacket;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import org.lwjgl.input.Keyboard;

public class Time extends Module {
    public NumberValue time = new NumberValue(this, "Time", 12, 24, 0, 1);

    public Time() {
        super("Time", "Allows you to change the in game time (client sided).", Category.RENDER, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        mc.world.setWorldTime(time.getLong() * 1000);
    }

    @Subscribe
    public void onPacket(EventPacket event) {
        if(event.getDirection() == Direction.INCOMING)
            if(event.getPacket() instanceof SPacketTimeUpdate)
                event.setCancelled(true);
    }
}