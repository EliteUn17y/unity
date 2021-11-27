package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import org.lwjgl.input.Keyboard;

public class Step extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Vanilla", "Vanilla", "Jump");
    public NumberValue height = new NumberValue(this, "Height", 2, 5, 1, 0.1);

    public float oldStepHeight;

    public Step() {
        super("Step", "Allows you to step up more blocks than normal.", Category.MOVEMENT, Keyboard.KEY_NONE);
        addValueOnValueChange(mode, height, "Vanilla");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        oldStepHeight = mc.player.stepHeight;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.player.stepHeight = oldStepHeight;
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        switch(mode.getMode()) {
            case "Vanilla":
                mc.player.stepHeight = height.getFloat();
                break;
            case "Jump":
                if(mc.player.collidedHorizontally && mc.player.onGround )
                    mc.player.jump();
                break;
        }
    }
}
