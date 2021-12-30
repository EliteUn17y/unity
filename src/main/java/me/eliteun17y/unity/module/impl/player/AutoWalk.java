package me.eliteun17y.unity.module.impl.player;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventInputUpdate;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class AutoWalk extends Module {
    public AutoWalk() {
        super("Auto Walk", "Automatically presses w.", Category.PLAYER, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onInputUpdate(EventInputUpdate event) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
        mc.player.movementInput.forwardKeyDown = true;
        mc.player.movementInput.moveForward = 1;
    }
}
