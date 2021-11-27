package me.eliteun17y.unity.ui.menu;

import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.ui.clickgui.components.Panel;
import me.eliteun17y.unity.ui.login.Login;
import me.eliteun17y.unity.ui.settings.Settings;
import me.eliteun17y.unity.util.Reference;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.Arrays;

public class MainMenu extends GuiScreen {
    public float titleAnim = 0;

    public MainMenu() {
        titleAnim = 1;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        // Background

        drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(232, 180, 184).getRGB(), new Color(103, 89, 94).getRGB());

        // Title

        GlStateManager.scale(4, 4, 4);
            FontManager.instance.robotoRegular.drawString(Reference.NAME + " " + Reference.VERSION, (sr.getScaledWidth() / 2.0f - (FontManager.instance.robotoRegular.getStringWidth(Reference.NAME + " " + Reference.VERSION) / 2.0f * 4.0f)) / 4.0f, (sr.getScaledHeight() / 4.0f) / 4.0f, UIUtil.getOppositeFontColor().getRGB());
        GlStateManager.scale(0.25, 0.25, 0.25);

        // Buttons

        String[] buttons = { "Singleplayer", "Multiplayer", "Login", "Unity Settings", "Settings", "Language", "Quit" };


        float width = 80;
        float height = 15;

        float x = (sr.getScaledWidth() / 2.0f - (90 * (buttons.length)) / 2.0f) + 10;
        float y = (sr.getScaledHeight() / 1.2f) - height / 2;

        for(String str : buttons) {
            if(mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height) {
                RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor((int) x, (int) y, (int) x + (int) width, (int) y + (int) height, 5, UIUtil.getNormalColor().getRGB(), new Color(UIUtil.getNormalColor().getRed(), UIUtil.getNormalColor().getBlue(), UIUtil.getNormalColor().getGreen(), 100).getRGB());
            }else {
                RenderHelper.drawRoundedRectangle((int) x, (int) y, (int) x + (int) width, (int) y + (int) height, 5, UIUtil.getNormalColor().getRGB());
            }
            FontManager.instance.robotoRegularSmall.drawString(str, x + (width / 2) - FontManager.instance.robotoRegularSmall.getStringWidth(str) / 2, y + (height / 2) - FontManager.instance.robotoRegularSmall.getStringHeight(str) / 2, UIUtil.getOppositeFontColor().getRGB());

            x += 90;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        String[] buttons = { "Singleplayer", "Multiplayer", "Login", "Unity Settings", "Settings", "Language", "Quit" };
        GuiScreen[] buttonScreens = { new GuiWorldSelection(this), new GuiMultiplayer(this), new Login(), new Settings(), new GuiOptions(this, mc.gameSettings), new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()) };

        float width = 80;
        float height = 15;

        float x = (sr.getScaledWidth() / 2.0f - (90 * (buttons.length)) / 2.0f) + 10;
        float y = (sr.getScaledHeight() / 1.2f) - height / 2;

        for(int i = 0; i < buttons.length; i++) {
            if(mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height) {
                if(!buttons[i].equalsIgnoreCase("Quit"))
                    mc.displayGuiScreen(buttonScreens[i]);
                else
                    mc.shutdown();
            }
            x += 90;
        }
    }
}
