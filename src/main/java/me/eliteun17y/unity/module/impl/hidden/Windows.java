package me.eliteun17y.unity.module.impl.hidden;

import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import org.lwjgl.input.Keyboard;

public class Windows extends Module {
    public Windows() {
        super("Windows", "Windows.", Category.HIDDEN, Keyboard.KEY_COMMA);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(new me.eliteun17y.unity.ui.windows.Windows());
        toggle();
    }
}
