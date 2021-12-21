package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import org.lwjgl.input.Keyboard;

public class SafeWalk extends Module {
    public SafeWalk() {
        super("Safe Walk", "Prevents you from falling off ledges.", Category.MOVEMENT, Keyboard.KEY_NONE);
    }
}
