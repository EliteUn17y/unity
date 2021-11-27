package me.eliteun17y.unity.ui.windows;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.ui.clickgui.components.Panel;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.window.Window;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;

public class Windows extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Window focusedWindow = null;
        for(Window window : Unity.instance.windowManager.getWindows()) {
            if(window.isFocused())
                focusedWindow = window;
        }

        ScaledResolution sr = new ScaledResolution(mc);
        float x = 0;

        for(Window window : Unity.instance.windowManager.getWindows()) {
            if(!window.isMinimised() && window != focusedWindow)
                window.draw(mouseX, mouseY, partialTicks);

            if(window.isMinimised()) {
                Gui.drawRect((int) x, sr.getScaledHeight() - 30, (int) (x + 6 + FontManager.instance.robotoLightSmall.getStringWidth(window.name)), sr.getScaledHeight(), UIUtil.getOppositeNormalColor2().getRGB());
                FontManager.instance.robotoLightSmall.drawString(window.name, x + 3, sr.getScaledHeight() - 15 - FontManager.instance.robotoLightSmall.getStringHeight(window.name) / 2, UIUtil.getOppositeFontColor().getRGB());

                x += x + 6 + FontManager.instance.robotoLightSmall.getStringWidth(window.name);
            }
        }

        if(focusedWindow != null && !focusedWindow.isMinimised())
            focusedWindow.draw(mouseX, mouseY, partialTicks); // Draw the focused window on top
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for(Window window : Unity.instance.windowManager.getWindows()) {
            window.click(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for(Window window : Unity.instance.windowManager.getWindows()) {
            window.release(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Window window : Unity.instance.windowManager.getWindows()) {
            window.key(character, key);
        }
    }
}