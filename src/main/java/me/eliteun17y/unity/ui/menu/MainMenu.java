package me.eliteun17y.unity.ui.menu;

import me.eliteun17y.unity.Unity;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class MainMenu extends GuiScreen {
    public ArrayList<String> changelog;
    public float titleAnim = 0;
    public long startTime;

    public MainMenu() {
        titleAnim = 1;
        changelog = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/changes.txt"))));

            String line;
            while((line = bufferedReader.readLine()) != null) {
                changelog.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        // Changelog

        float h = 0;
        for(String str : changelog) {
            h += FontManager.instance.robotoRegularSmall.getStringHeight(str);
        }

        RenderHelper.drawFilledRoundedRectangle(8, 8, 188, h + 14 + FontManager.instance.robotoRegular.getStringHeight("Changelog"), 5, UIUtil.getNormalColor().getRGB());
        FontManager.instance.robotoRegular.drawString("Changelog", 12, 12, UIUtil.getFontColor().getRGB());

        float yPos = 30;
        for(String str : changelog) {
            FontManager.instance.robotoRegularSmall.drawString(str, 12, yPos, UIUtil.getFontColor().getRGB());
            yPos += FontManager.instance.robotoRegularSmall.getStringHeight(str);
        }

        FontManager.instance.robotoRegularSmall.drawString(Unity.instance.user.username, 3, sr.getScaledHeight() - FontManager.instance.robotoRegularSmall.getStringHeight(Unity.instance.user.username) - FontManager.instance.robotoRegularSmall.getStringHeight("Made by eliteun17y") - 6, UIUtil.getOppositeFontColor().getRGB());
        FontManager.instance.robotoRegularSmall.drawString("Made by eliteun17y", 3, sr.getScaledHeight() - FontManager.instance.robotoRegularSmall.getStringHeight("Made by eliteun17y") - 3, UIUtil.getOppositeFontColor().getRGB());
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
