package me.eliteun17y.unity.module.impl.player;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventPacket;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.chat.ChatUtil;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import net.minecraft.network.play.server.SPacketExplosion;
import org.lwjgl.input.Keyboard;

public class AntiCrash extends Module {
    public BooleanValue explosionCrash = new BooleanValue(this, "Explosion Crash", true);

    public AntiCrash() {
        super("Anti Crash", "Prevents your client from crashing due to exploits.", Category.PLAYER, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onPacket(EventPacket event) {
        if(explosionCrash.getObject()) {
            if(event.getPacket() instanceof SPacketExplosion) {
                if(((SPacketExplosion) event.getPacket()).getStrength() >= 500) { // Check for unreasonably large values (due to it crashing the client)
                    ChatUtil.sendClientMessage("Server attempted to crash client. Cancelling packet.");
                    event.setCancelled(true);
                }
            }
        }
    }
}
