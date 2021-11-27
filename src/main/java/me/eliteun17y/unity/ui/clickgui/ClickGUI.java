package me.eliteun17y.unity.ui.clickgui;

import me.eliteun17y.unity.ui.clickgui.components.Panel;
import me.eliteun17y.unity.ui.clickgui.components.SettingPanel;
import me.eliteun17y.unity.module.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ClickGUI extends GuiScreen {
    public ArrayList<Panel> panels = new ArrayList<>();
    private static boolean areSettingsOpened = false;
    private static SettingPanel panel;

    public static boolean areSettingsOpened() {
        return areSettingsOpened;
    }

    public static void setSettingsOpened(boolean settingsOpened) {
        areSettingsOpened = settingsOpened;
    }

    public static SettingPanel getPanel() {
        return panel;
    }

    public static void setPanel(SettingPanel pan) {
        panel = pan;
    }

    public ClickGUI() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float width = 90;
        float height = 220;

        Category[] hiddenCategories = {Category.HIDDEN};

        float x = (sr.getScaledWidth() / 2.0f - (110 * (Category.values().length - hiddenCategories.length)) / 2.0f) + 10;
        float y = (sr.getScaledHeight() / 2.0f) - height / 2;
        setSettingsOpened(false);

        for(Category category : Category.values()) {
            if(Arrays.asList(hiddenCategories).contains(category)) continue;

            panels.add(new Panel(x, y, width, height, category));
            x += 110;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(Panel panel : panels)
            panel.draw(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for(Panel panel : panels)
            panel.click(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for(Panel panel : panels)
            panel.release(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Panel panel : panels)
            panel.key(character, key);
    }

    @Override
    public void handleMouseInput() {
        try {
            super.handleMouseInput();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = Mouse.getEventDWheel();

        for(Panel panel : panels)
            panel.mouse(i);
    }
}