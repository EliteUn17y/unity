package me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.impl;

import me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.SettingButton;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.setting.impl.TextValue;
import me.eliteun17y.unity.util.ui.CustomFontTextBox;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.ArrayList;

public class TextButton extends SettingButton {
    public CustomFontTextBox textBox;

    public TextButton(float x, float y, TextValue value) {
        super(x, y, value);
        textBox = new CustomFontTextBox(FontManager.instance.robotoLightSmall, x, y, 50, value.getName(), UIUtil.getOppositeFontColor(), UIUtil.getNormalColor());
        textBox.setText(value.getObject());
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        textBox.drawScreen(mouseX, mouseY, partialTicks);
        ((TextValue) value).setObject(textBox.getText());
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
        textBox.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void release(int mouseX, int mouseY, int mouseButton) {
        textBox.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void key(char character, int key) {
        textBox.keyTyped(character, key);
    }
}