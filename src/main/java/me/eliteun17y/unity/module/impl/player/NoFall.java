package me.eliteun17y.unity.module.impl.player;

import me.eliteun17y.unity.event.Direction;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventPacket;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.chat.ChatUtil;
import me.eliteun17y.unity.util.player.PlayerUtil;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.world.PacketUtil;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Spoof Ground", "Spoof Ground", "Anti Cheat Reloaded", "Reduce");

    public NoFall() {
        super("No Fall", "Makes it so you don't take fall damage.", Category.PLAYER, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        switch(mode.getMode()) {
            case "Anti Cheat Reloaded":
                ChatUtil.sendClientMessage("Fall distance: " + PlayerUtil.getDistanceToGround());
                if(PlayerUtil.getDistanceToGround() > 1 && PlayerUtil.getDistanceToGround() < 5) {
                    mc.player.motionY = -1.8;
                }
                break;
        }
    }

    @Subscribe
    public void onPacket(EventPacket event) {
        switch(mode.getMode()) {
            case "Spoof Ground":
                if(event.getDirection() == Direction.OUTGOING)
                    if(event.getPacket() instanceof CPacketPlayer)
                        ObfuscationReflectionHelper.setPrivateValue(CPacketPlayer.class, ((CPacketPlayer) event.getPacket()), true, "field_149474_g");
                break;

            case "Reduce":
                if(event.getDirection() == Direction.OUTGOING)
                    if(event.getPacket() instanceof CPacketPlayer)
                        if(mc.player.ticksExisted % 5 == 0)
                            ObfuscationReflectionHelper.setPrivateValue(CPacketPlayer.class, ((CPacketPlayer) event.getPacket()), true, "field_149474_g");
                break;
        }
    }
}
