package me.eliteun17y.unity.ui.authlogin;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.auth.AuthenticatedUser;
import me.eliteun17y.unity.auth.Authenticator;
import me.eliteun17y.unity.auth.HWID;
import me.eliteun17y.unity.ui.clickgui.components.Panel;
import me.eliteun17y.unity.ui.menu.MainMenu;
import me.eliteun17y.unity.util.Reference;
import me.eliteun17y.unity.util.file.FileUtil;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.CustomFontTextBox;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class AuthLogin extends GuiScreen {
    public CustomFontTextBox username;
    public CustomFontTextBox password;

    public String status = "You are on this screen because we found out that your authentication information doesn't exist.";

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if(username == null) {
            this.username = new CustomFontTextBox(FontManager.instance.robotoLightSmall, sr.getScaledWidth() / 2.0f - 25, sr.getScaledHeight() / 2.0f + 20, 50, "Username", UIUtil.getOppositeFontColor(), UIUtil.getNormalColor());
            this.password = new CustomFontTextBox(FontManager.instance.robotoLightSmall, sr.getScaledWidth() / 2.0f - 25, sr.getScaledHeight() / 2.0f + 40, 50, "Password", UIUtil.getOppositeFontColor(), UIUtil.getNormalColor());
        }

        this.username.x = sr.getScaledWidth() / 2.0f - 25;
        this.password.x = sr.getScaledWidth() / 2.0f - 25;
        this.username.y = sr.getScaledHeight() / 2.0f + 20;
        this.password.y = sr.getScaledHeight() / 2.0f + 40;

        // Background

        drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(232, 180, 184).getRGB(), new Color(103, 89, 94).getRGB());

        // Title

        GlStateManager.scale(4, 4, 4);
        FontManager.instance.robotoRegular.drawString(Reference.NAME + " " + Reference.VERSION, (sr.getScaledWidth() / 2.0f - (FontManager.instance.robotoRegular.getStringWidth(Reference.NAME + " " + Reference.VERSION) / 2.0f * 4.0f)) / 4.0f, (sr.getScaledHeight() / 4.0f) / 4.0f, UIUtil.getOppositeFontColor().getRGB());
        GlStateManager.scale(0.25, 0.25, 0.25);

        // Status

        FontManager.instance.robotoRegularSmall.drawString(status, (sr.getScaledWidth() / 2.0f - (FontManager.instance.robotoRegularSmall.getStringWidth(status) / 2.0f)), (sr.getScaledHeight() / 2.0f), UIUtil.getOppositeFontColor().getRGB());

        // Inputs

        username.drawScreen(mouseX, mouseY, partialTicks);
        password.drawScreen(mouseX, mouseY, partialTicks);

        // Submit

        RenderHelper.drawFilledRoundedRectangle(sr.getScaledWidth() / 2.0f - 35, sr.getScaledHeight() / 2.0f + 58, sr.getScaledWidth() / 2.0f + 35, sr.getScaledHeight() / 2.0f + 78, 5, UIUtil.getPreferredColor().getRGB());
        FontManager.instance.robotoLight.drawString("Submit", sr.getScaledWidth() / 2.0f - FontManager.instance.robotoLight.getStringWidth("Submit") / 2.0f, sr.getScaledHeight() / 2.0f + 68 - (FontManager.instance.robotoLight.getStringHeight("Submit") / 2), UIUtil.getOppositeFontColor().getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        username.mouseClicked(mouseX, mouseY, mouseButton);
        password.mouseClicked(mouseX, mouseY, mouseButton);

        if(mouseX >= sr.getScaledWidth() / 2.0f - 35 && mouseX <= sr.getScaledWidth() / 2.0f + 35 && mouseY >= sr.getScaledHeight() / 2.0f + 58 && mouseY <= sr.getScaledHeight() / 2.0f + 78) {
            AuthenticatedUser u = Authenticator.getUser(username.getText(), password.getText(), HWID.getHWID());
            if(Objects.equals(u.username, "")) {
                status = "Incorrect password.";
            }else {
                FileUtil.writeContent(FileUtil.auth, "{\"username\": \"" + username.getText() + "\", \"password\": \"" + password.getText() + "\"}");
                Unity.instance.user = u;
                mc.displayGuiScreen(new MainMenu());
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        username.mouseReleased(mouseX, mouseY, mouseButton);
        password.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        username.keyTyped(character, key);
        password.keyTyped(character, key);
    }
}
