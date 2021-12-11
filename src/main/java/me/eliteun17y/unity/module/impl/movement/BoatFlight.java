package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Direction;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventPacket;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.chat.ChatUtil;
import me.eliteun17y.unity.util.player.PlayerUtil;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

public class BoatFlight extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Normal", "Normal", "Development");
    public NumberValue speed = new NumberValue(this, "Speed", 0.5, 5, 0, 0.1);
    public NumberValue verticalSpeed = new NumberValue(this, "Vertical Speed", 0.5, 5, 0, 0.1);

    public BoatFlight() {
        super("Boat Flight", "Allows you to fly in survival mode, on entities.", Category.MOVEMENT, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        switch(mode.getMode()) {
            case "Normal":
                if (mc.player.getRidingEntity() != null) {
                    mc.player.getRidingEntity().setNoGravity(true);
                    mc.player.getRidingEntity().motionY = mc.player.movementInput.jump ? verticalSpeed.getFloat() : mc.player.movementInput.sneak ? -verticalSpeed.getFloat() : 0;
                    PlayerUtil.strafe(speed.getDouble());
                    PlayerUtil.strafe(mc.player.getRidingEntity(), speed.getDouble());
                }
                break;
            case "Development":
                if (mc.player.getRidingEntity() != null) {
                    mc.player.getRidingEntity().setNoGravity(true);
                    mc.player.getRidingEntity().motionY = mc.player.movementInput.jump ? verticalSpeed.getFloat() : mc.player.movementInput.sneak ? -verticalSpeed.getFloat() : mc.player.ticksExisted % 2 == 0 ? 0.1 : -0.1;
                    PlayerUtil.strafe(speed.getDouble());
                    PlayerUtil.strafe(mc.player.getRidingEntity(), speed.getDouble());
                }
                break;
        }
    }

    @Subscribe
    public void onPacket(EventPacket event) {
        switch(mode.getMode()) {
            case "Normal":
                break;
            case "Development":
                if(event.getDirection() == Direction.OUTGOING) {
                    if(event.getPacket() instanceof CPacketPlayer && mc.player.isRiding()) {
                        event.setCancelled(true);
                    }
                    if(event.getPacket() instanceof CPacketVehicleMove) {
                        event.setCancelled(mc.player.ticksExisted % 2 == 0);
                    }
                }
                break;
        }
    }
}
