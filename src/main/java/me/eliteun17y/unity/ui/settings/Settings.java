package me.eliteun17y.unity.ui.settings;

import me.eliteun17y.unity.ui.clickgui.components.button.Button;
import me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.SettingButton;
import me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.impl.ColorButton;
import me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.impl.ModeButton;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.setting.impl.ColorValue;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Settings extends GuiScreen {
    // Settings
    public ArrayList<SettingButton> buttons;

    public Settings() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        buttons = new ArrayList<>();

        float x = (((sr.getScaledHeight() / 8.0f) / 1.6f) / 2) - (FontManager.instance.robotoThin.getStringHeight("Settings") / 2.2f);
        float y = (sr.getScaledHeight() / 8.0f) + 12;

        buttons.add(new ColorButton(x + FontManager.instance.robotoRegularSmall.getStringWidth("Preferred Color") + 5, y, new ColorValue("Preferred Color", UIUtil.preferredColor)));
        y += 20;
        buttons.add(new ModeButton(x + FontManager.instance.robotoRegularSmall.getStringWidth("Theme") + 5, y, new ModeValue("Theme", "Light", "Light", "Dark")));
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), UIUtil.getNormalColor().getRGB());

        // Title

        drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight() / 8, UIUtil.getNormalColor2().getRGB());
        GlStateManager.scale(1.6f, 1.6f, 1.6f);
        FontManager.instance.robotoThin.drawString("Settings", (((sr.getScaledHeight() / 8.0f) / 1.6f) / 2) - (FontManager.instance.robotoThin.getStringHeight("Settings") / 2.2f), (((sr.getScaledHeight() / 8.0f) / 1.6f) / 2) - (FontManager.instance.robotoThin.getStringHeight("Settings") / 2.2f), UIUtil.getFontColor().getRGB());
        GlStateManager.scale(0.625f, 0.625f, 0.265f);

        // Settings



        for(SettingButton button : buttons) {
            switch(button.value.getName()) {
                case "Preferred Color":
                    UIUtil.preferredColor = (Color) button.value.getObject();
                    break;
                case "Theme":
                    UIUtil.darkMode = ((ModeValue) button.value).getMode().equalsIgnoreCase("Dark") ? true : false;
                    break;
            }
            FontManager.instance.robotoRegularSmall.drawString(button.value.name, button.x - FontManager.instance.robotoRegularSmall.getStringWidth(button.value.name) - 5, button.y, UIUtil.getFontColor().getRGB());
            button.draw(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for(Button button : buttons)
            button.click(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for(Button button : buttons)
            button.release(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Button button : buttons)
            button.key(character, key);
    }
}
