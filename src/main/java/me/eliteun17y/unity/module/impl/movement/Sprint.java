package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.impl.*;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Sprint extends Module {
    public BooleanValue booleanTest = new BooleanValue(this, "Boolean Demo", true);
    public ModeValue modeTest = new ModeValue(this, "Mode Demo", "Test 1", "Test 1", "Test 2", "Test 3", "Test 4", "Test 5");
    public NumberValue integerTest = new NumberValue(this, "Number Demo", 5, 10, 0, 2f);
    public TextValue textTest = new TextValue(this, "Text Demo", "Hello, just to prove this demo I am writing lots of text. Why am I doing this? I really don't know.");
    public ColorValue colorTest = new ColorValue(this, "Chests", new Color(0, 0, 0, 255));
    public ColorValue colorTest2 = new ColorValue(this, "Players", new Color(0, 0, 0, 255));

    public Sprint() {
        super("Sprint", "Automatically sprints for you.", Category.MOVEMENT, Keyboard.KEY_M);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if(mc.player.movementInput.moveForward > 0)
            mc.player.setSprinting(true);
    }
}