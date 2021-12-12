package me.eliteun17y.unity.ui.login;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.managers.alts.Alt;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.login.Authenticator;
import me.eliteun17y.unity.util.login.MinecraftProfile;
import me.eliteun17y.unity.util.ui.CustomFontTextBox;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.Session;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class Login extends GuiScreen {
    public boolean addMenuOpened;
    public CustomFontTextBox email;
    public CustomFontTextBox password;

    public Login() {
        addMenuOpened = false;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        this.email = new CustomFontTextBox(FontManager.instance.robotoLightSmall, sr.getScaledWidth() / 2.0f - 25, sr.getScaledHeight() / 2.0f - 10, 50, "Email", UIUtil.getOppositeFontColor(), UIUtil.getNormalColor());
        this.password = new CustomFontTextBox(FontManager.instance.robotoLightSmall, sr.getScaledWidth() / 2.0f - 25, sr.getScaledHeight() / 2.0f + 10, 50, "Password", UIUtil.getOppositeFontColor(), UIUtil.getNormalColor());

        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), UIUtil.getNormalColor().getRGB());

        // Title

        drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight() / 8, UIUtil.getNormalColor2().getRGB());
        GlStateManager.scale(1.6f, 1.6f, 1.6f);
        FontManager.instance.robotoThin.drawString("Alt Manager", (((sr.getScaledHeight() / 8.0f) / 1.6f) / 2) - (FontManager.instance.robotoThin.getStringHeight("Alt Manager") / 2.2f), (((sr.getScaledHeight() / 8.0f) / 1.6f) / 2) - (FontManager.instance.robotoThin.getStringHeight("Alt Manager") / 2.2f), UIUtil.getFontColor().getRGB());
        GlStateManager.scale(0.625f, 0.625f, 0.265f);

        // Alts

        float x = (((sr.getScaledHeight() / 8.0f) / 1.6f) / 2) - (FontManager.instance.robotoThin.getStringHeight("Alt Manager") / 2.2f);
        float y = (sr.getScaledHeight() / 8.0f) + 12;

        for(Alt alt : Unity.instance.altManager.getRegistry()) {
            RenderHelper.drawFilledRoundedRectangle(x, y, x + 200, y + 40, 5, UIUtil.getNormalColor2().getRGB());
            FontManager.instance.robotoLightSmall.drawString("Name: " + alt.getName(), x + 6 + 26, y + 20 - (FontManager.instance.robotoLightSmall.getStringHeight("Name: " + alt.getName()) / 2), UIUtil.getFontColor().getRGB());

            if(alt.image == null) {
                y += 52;
                continue;
            }

            DynamicTexture dynamicTexture = new DynamicTexture(alt.image);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, dynamicTexture.getGlTextureId());
            GlStateManager.color(1, 1, 1);
            drawModalRectWithCustomSizedTexture((int) x + 6, (int) y + 10, 20, 20, 20, 20, 20, 20);

            y += 52;
        }

        // Add

        RenderHelper.drawFilledCircle(20, sr.getScaledHeight() - 20, 10, UIUtil.getPreferredColor().getRGB());
        drawRect(19, sr.getScaledHeight() - 26, 21, sr.getScaledHeight() - 14, -1);
        drawRect(14, sr.getScaledHeight() - 19, 26, sr.getScaledHeight() - 21, -1);

        // This should be at the bottom as to render over everything
        if(addMenuOpened) {
            drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(UIUtil.getOppositeNormalColor2().getRed(), UIUtil.getOppositeNormalColor2().getBlue(), UIUtil.getOppositeNormalColor2().getGreen(), 200).getRGB());

            RenderHelper.drawFilledRoundedRectangle(sr.getScaledWidth() / 2.0f - 80, sr.getScaledHeight() / 2.0f - 60, sr.getScaledWidth() / 2.0f + 80, sr.getScaledHeight() / 2.0f + 60, 3, UIUtil.getOppositeNormalColor2().getRGB());
            FontManager.instance.robotoLight.drawString("Add Alt", sr.getScaledWidth() / 2.0f - FontManager.instance.robotoLight.getStringWidth("Add Alt") / 2.0f, sr.getScaledHeight() / 2.0f - 50, UIUtil.getOppositeFontColor().getRGB());

            email.drawScreen(mouseX, mouseY, partialTicks);
            password.drawScreen(mouseX, mouseY, partialTicks);

            RenderHelper.drawFilledRoundedRectangle(sr.getScaledWidth() / 2.0f - 35, sr.getScaledHeight() / 2.0f + 38, sr.getScaledWidth() / 2.0f + 35, sr.getScaledHeight() / 2.0f + 58, 5, UIUtil.getPreferredColor().getRGB());
            FontManager.instance.robotoLight.drawString("Add", sr.getScaledWidth() / 2.0f - FontManager.instance.robotoLight.getStringWidth("Add") / 2.0f, sr.getScaledHeight() / 2.0f + 48 - (FontManager.instance.robotoLight.getStringHeight("Add") / 2), UIUtil.getOppositeFontColor().getRGB());
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());


        // Add

        if(mouseButton == 0) {
            if(mouseX >= 10 && mouseY >= sr.getScaledHeight() - 30 && mouseX <= 30 && mouseY <= sr.getScaledHeight() - 10) {
                addMenuOpened = true;
            }
        }

        if(addMenuOpened) {
            if(mouseX >= sr.getScaledWidth() / 2.0f - 35 && mouseY >= sr.getScaledHeight() / 2.0f + 38 && mouseX <= sr.getScaledWidth() / 2.0f + 35 && mouseY <= sr.getScaledHeight() / 2.0f + 58) {
                if(password.getText().isEmpty()) {
                    Unity.instance.altManager.add(new Alt(email.getText(), email.getText(), "", null));
                }else {
                    MinecraftProfile minecraftProfile = Authenticator.getAccount(email.getText(), password.getText());
                    if(minecraftProfile == null) return;
                    System.out.println(minecraftProfile);

                    Unity.instance.altManager.add(new Alt(minecraftProfile.name, email.getText(), password.getText(), minecraftProfile.uuid));
                }
                addMenuOpened = false;
            }

            email.mouseClicked(mouseX, mouseY, mouseButton);
            password.mouseClicked(mouseX, mouseY, mouseButton);
        }else {
            float x = (((sr.getScaledHeight() / 8.0f) / 1.6f) / 2) - (FontManager.instance.robotoThin.getStringHeight("Alt Manager") / 2.2f);
            float y = (sr.getScaledHeight() / 8.0f) + 12;

            for(Alt alt : Unity.instance.altManager.getRegistry()) {
                if(mouseX >= x && mouseY >= y && mouseX <= x + 200 && y <= y + 40) {
                    if(alt.getUuid() == null) {
                        Session session = new Session(alt.getName(), "", "", "mojang");
                        ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, session, "field_71449_j"); // field_71449_j = session

                    }else {
                        MinecraftProfile minecraftProfile = Authenticator.getAccount(alt.getEmail(), alt.getPassword());
                        assert minecraftProfile != null;
                        Session session = new Session(minecraftProfile.name, minecraftProfile.uuid.toString(), minecraftProfile.jwt, "mojang");
                        ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, session, "field_71449_j"); // field_71449_j = session
                    }
                }

                y += 52;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if(addMenuOpened) {
            email.mouseReleased(mouseX, mouseY, mouseButton);
            password.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addMenuOpened) {
            email.keyTyped(character, key);
            password.keyTyped(character, key);
        }
    }
}
