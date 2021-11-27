package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.player.PlayerUtil;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import me.eliteun17y.unity.util.world.TimerUtil;
import org.lwjgl.input.Keyboard;

public class LongJump extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Normal", "Normal");
    public NumberValue distance = new NumberValue(this, "Distance", 0.5, 5, 0, 0.1);

    public LongJump() {
        super("Long Jump", "Allows you to jump farther than intended.", Category.MOVEMENT, Keyboard.KEY_NONE);
        addValueOnValueChange(mode, distance, "Normal");
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        switch(mode.getMode()) {
            case "Normal":
                if(mc.player.movementInput.jump) {
                    PlayerUtil.strafe(distance.getDouble());
                }
                break;
        }
    }
}
