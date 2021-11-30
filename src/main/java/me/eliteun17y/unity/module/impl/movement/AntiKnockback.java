package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Direction;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventPacket;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import org.lwjgl.input.Keyboard;

public class AntiKnockback extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Vanilla", "Vanilla");

    public AntiKnockback() {
        super("Anti Knockback", "Removes all knockback from taking damage.", Category.MOVEMENT, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onPacket(EventPacket event) {
        switch(mode.getMode()) {
            case "Vanilla":
                if(event.getDirection() == Direction.INCOMING) {
                    if(event.getPacket() instanceof SPacketEntityVelocity)
                        if (((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId())
                            event.setCancelled(true);

                    if(event.getPacket() instanceof SPacketExplosion)
                        event.setCancelled(true);
                }
                break;
        }
    }
}
