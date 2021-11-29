package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.player.PlayerUtil;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import org.lwjgl.input.Keyboard;

public class Flight extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Motion", "Motion");
    public NumberValue speed = new NumberValue(this, "Speed", 0.5, 5, 0, 0.1);
    public NumberValue verticalSpeed = new NumberValue(this, "Vertical Speed", 0.5, 5, 0, 0.1);

    public Flight() {
        super("Flight", "Allows you to fly in survival mode.", Category.MOVEMENT, Keyboard.KEY_G);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        switch(mode.getMode()) {
            case "Motion":
                mc.player.motionY = mc.player.movementInput.jump ? verticalSpeed.getFloat() : mc.player.movementInput.sneak ? -verticalSpeed.getFloat() : 0;
                PlayerUtil.strafe(speed.getDouble());
                break;
        }
    }
}
