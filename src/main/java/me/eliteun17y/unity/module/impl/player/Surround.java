package me.eliteun17y.unity.module.impl.player;

import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import org.lwjgl.input.Keyboard;

public class Surround extends Module {
    public Surround() {
        super("Surround", "Surrounds you with blocks.", Category.PLAYER, Keyboard.KEY_SEMICOLON);
    }
}
