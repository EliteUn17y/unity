package me.eliteun17y.unity.module.impl.render;

import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("Click GUI", "A clickable GUI that allows you to toggle modules and configure their settings.", Category.RENDER, Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(new me.eliteun17y.unity.ui.clickgui.ClickGUI());
        toggle();
    }
}
