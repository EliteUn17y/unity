package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.player.PlayerUtil;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

public class Flight extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Motion", "Motion", "Taka", "Verus");
    public NumberValue speed = new NumberValue(this, "Speed", 0.5, 5, 0, 0.1);
    public NumberValue verticalSpeed = new NumberValue(this, "Vertical Speed", 0.5, 5, 0, 0.1);

    public Flight() {
        super("Flight", "Allows you to fly in survival mode.", Category.MOVEMENT, Keyboard.KEY_G);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        switch(mode.getMode()) {
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
        }
    }
}
