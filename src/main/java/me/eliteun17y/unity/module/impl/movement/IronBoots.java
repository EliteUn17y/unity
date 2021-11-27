package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventHandleWaterMovement;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.input.Keyboard;

public class IronBoots extends Module {
    public IronBoots() {
        super("Iron Boots", "Allows you to sink under water.", Category.MOVEMENT, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onHandleWaterMovementPost(EventHandleWaterMovement event) {
        ObfuscationReflectionHelper.setPrivateValue(Entity.class, mc.player, false, "field_70171_ac"); // field_70171_ac = inWater
        event.setCancelled(true);
    }
}
