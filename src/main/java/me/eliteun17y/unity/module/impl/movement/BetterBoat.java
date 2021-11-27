package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import net.minecraft.entity.item.EntityBoat;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.input.Keyboard;

public class BetterBoat extends Module {
    public BooleanValue deltaRotation = new BooleanValue(this, "Delta Rotation", true);
    public BooleanValue matchYaw = new BooleanValue(this, "Match Player Yaw", true);

    public BetterBoat() {
        super("Better Boat", "Makes riding entities easier.", Category.MOVEMENT, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if(deltaRotation.getObject())
            if(mc.player.getRidingEntity() instanceof EntityBoat)
                ObfuscationReflectionHelper.setPrivateValue(EntityBoat.class, (EntityBoat) mc.player.getRidingEntity(), 0, "field_184475_as"); // field_184475_as = deltaRotation

        if(matchYaw.getObject())
            if(mc.player.getRidingEntity() != null)
                mc.player.getRidingEntity().rotationYaw = mc.player.rotationYaw;
    }
}
