package me.eliteun17y.unity.ui.clickgui.components.button.impl.setting;

import me.eliteun17y.unity.ui.clickgui.components.button.Button;
import me.eliteun17y.unity.util.setting.Value;

public class SettingButton implements Button {
    public float x;
    public float y;
    public Value value;

    public SettingButton(float x, float y, Value value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void release(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void key(char character, int key) {

    }
}
