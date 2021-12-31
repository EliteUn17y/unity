package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Direction;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventPacket;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.player.PlayerUtil;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import me.eliteun17y.unity.util.time.Timer;
import me.eliteun17y.unity.util.world.PacketUtil;
import me.eliteun17y.unity.util.world.TimerUtil;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.stats.StatList;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.input.Keyboard;

public class Flight extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Motion", "Motion", "Development", "Taka", "Verus");
    public NumberValue speed = new NumberValue(this, "Speed", 0.5, 5, 0, 0.1);
    public NumberValue verticalSpeed = new NumberValue(this, "Vertical Speed", 0.5, 5, 0, 0.1);

    public Flight() {
        super("Flight", "Allows you to fly in survival mode.", Category.MOVEMENT, Keyboard.KEY_G);
    }

    public Timer timer = new Timer();

    @Override
    public void onEnable() {
        super.onEnable();
        timer.reset();
        switch(mode.getMode()) {
            case "Development":
                double x = PlayerUtil.getDirection()[0] * 1;
                double z = PlayerUtil.getDirection()[1] * 1;
                PacketUtil.sendSilentPacket(new CPacketPlayer.Position(mc.player.posX + x, mc.player.posY, mc.player.posZ + z, true));
                PacketUtil.sendSilentPacket(new CPacketPlayer.Position(mc.player.posX + 17, mc.player.posY, mc.player.posZ + 17, true));
                break;
            case "Verus":
                PlayerUtil.damage(1);
                break;
        }
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        switch(mode.getMode()) {
            case "Motion":
                mc.player.motionY = mc.player.movementInput.jump ? verticalSpeed.getFloat() : mc.player.movementInput.sneak ? -verticalSpeed.getFloat() : 0;
                PlayerUtil.strafe(speed.getDouble());
                break;
            case "Taka":
                mc.player.jump();
                mc.player.motionY = mc.player.movementInput.jump ? verticalSpeed.getFloat() : mc.player.movementInput.sneak ? -verticalSpeed.getFloat() : 0.1;
                mc.player.onGround = true;
                break;
            case "Verus":
                mc.player.motionY = 0;
                mc.player.onGround = true;
                PlayerUtil.strafe(mc.player.hurtTime > 0 ? speed.getDouble() : 0.2d);
                break;
            case "Development":
                TimerUtil.setSpeed(mc.player.ticksExisted % 2 == 0 ? 1.4f : 1.6f);
                mc.player.motionY = mc.player.movementInput.jump ? verticalSpeed.getFloat() : mc.player.movementInput.sneak ? -verticalSpeed.getFloat() : 0;
                PlayerUtil.strafe(speed.getDouble());
                break;
        }
    }

    @Subscribe
    public void onPacket(EventPacket event) {
        switch(mode.getMode()) {
            case "Development":
                if(event.getDirection() == Direction.OUTGOING) {
                    if(event.getPacket() instanceof CPacketPlayer) {
                        ObfuscationReflectionHelper.setPrivateValue(CPacketPlayer.class, (CPacketPlayer) event.getPacket(), false, "field_149474_g");
                    }
                }
                break;
        }
    }
}
