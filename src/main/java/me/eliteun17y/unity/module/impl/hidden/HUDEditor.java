package me.eliteun17y.unity.module.impl.hidden;

import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import org.lwjgl.input.Keyboard;

public class HUDEditor extends Module {
    public HUDEditor() {
        super("HUD Editor", "Allows you to modify the HUD.", Category.HIDDEN, Keyboard.KEY_PERIOD);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(new me.eliteun17y.unity.ui.hudeditor.HUDEditor());
        toggle();
    }
}
