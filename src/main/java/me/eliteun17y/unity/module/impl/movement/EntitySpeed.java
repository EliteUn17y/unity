package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.player.PlayerUtil;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntityHorse;
import org.lwjgl.input.Keyboard;

public class EntitySpeed extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Normal", "Normal");
    public NumberValue speed = new NumberValue(this, "Speed", 0.5, 5, 0, 0.1);

    public EntitySpeed() {
        super("Entity Speed", "Allows for faster movement on rideable entities.", Category.MOVEMENT, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        switch(mode.getMode()) {
            case "Normal":
                if (mc.player.getRidingEntity() != null) {
                    PlayerUtil.strafe(mc.player.getRidingEntity(), speed.getDouble());
                }
                break;
        }
    }
}
