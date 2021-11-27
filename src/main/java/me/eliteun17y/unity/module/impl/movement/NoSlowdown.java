package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventInputUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class NoSlowdown extends Module {
    public BooleanValue sneaking = new BooleanValue(this, "Sneaking", true);
    public BooleanValue item = new BooleanValue(this, "Using Item", true);
    public BooleanValue liquid = new BooleanValue(this, "Liquid", true);

    public NoSlowdown() {
        super("No Slowdown", "Prevents you from being slowed.", Category.MOVEMENT, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onInputUpdate(EventInputUpdate event) {
        if(mc.player.isSneaking() && sneaking.getObject()) {
            mc.player.movementInput.moveForward *= 3.3333;
            mc.player.movementInput.moveStrafe *= 3.3333;
        }

        if(mc.player.isHandActive() && item.getObject()) {
            mc.player.movementInput.moveForward *= 5;
            mc.player.movementInput.moveStrafe *= 5;
        }

        if((mc.player.isInWater() || mc.player.isInLava()) && liquid.getObject()) {
            mc.player.movementInput.moveForward *= 5;
            mc.player.movementInput.moveStrafe *= 5;
        }
    }
}