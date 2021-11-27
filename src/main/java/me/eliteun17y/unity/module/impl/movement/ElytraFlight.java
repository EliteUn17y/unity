package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventTravel;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.player.PlayerUtil;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import org.lwjgl.input.Keyboard;

public class ElytraFlight extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Motion", "Motion");
    public NumberValue speed = new NumberValue(this, "Speed", 0.5, 5, 0, 0.1);
    public NumberValue verticalSpeed = new NumberValue(this, "Vertical Speed", 0.5, 5, 0, 0.1);

    public ElytraFlight() {
        super("Elytra Flight", "Allows you to infinitely fly with an elytra.", Category.MOVEMENT, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onTravel(EventTravel event) {
        switch(mode.getMode()) {
            case "Motion":
                if(mc.player.isElytraFlying()) {
                    event.setForward(0);
                    event.setStrafe(0);
                    event.setVertical(0);

                    mc.player.motionY = mc.player.movementInput.jump ? verticalSpeed.getFloat() : mc.player.movementInput.sneak ? -verticalSpeed.getFloat() : 0;
                    PlayerUtil.strafe(speed.getDouble());
                    event.setCancelled(true);
                }
        }
    }
}
