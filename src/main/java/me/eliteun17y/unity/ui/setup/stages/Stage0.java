package me.eliteun17y.unity.ui.setup.stages;

import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class Stage0 extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), UIUtil.getNormalColor().getRGB());

        // Title

        GlStateManager.scale(2, 2, 2);
        FontManager.instance.robotoRegular.drawString("Theme", (sr.getScaledWidth() - FontManager.instance.robotoRegular.getStringWidth("Theme") * 2.0f) / 4.0f, 8, UIUtil.getFontColor().getRGB());
        GlStateManager.scale(0.5, 0.5, 0.5);

        // Themes

        float x = (sr.getScaledWidth() / 2.0f - 110);
        float y = sr.getScaledHeight() / 2.0f - 10;

        RenderHelper.drawFilledRoundedRectangle(x - 2, y - 2, x + 92, y + 22, 5, new Color(13, 45, 44).getRGB());
        RenderHelper.drawFilledRoundedRectangle(x, y, x + 90, y + 20, 5, new Color(210, 210, 210).getRGB());
        FontManager.instance.robotoLight.drawString("Light Mode", x + 45 - FontManager.instance.robotoLight.getStringWidth("Light Mode") / 2.0f, y + 10 - FontManager.instance.robotoLight.getStringHeight("Light Mode") / 2.0f, new Color(90, 90, 90).getRGB());

        x += 130;

        RenderHelper.drawFilledRoundedRectangle(x - 2, y - 2, x + 92, y + 22, 5, new Color(0, 0, 0).getRGB());
        RenderHelper.drawFilledRoundedRectangle(x, y, x + 90, y + 20, 5, new Color(90, 90, 90).getRGB());
        FontManager.instance.robotoLight.drawString("Dark Mode", x + 45 - FontManager.instance.robotoLight.getStringWidth("Dark Mode") / 2.0f, y + 10 - FontManager.instance.robotoLight.getStringHeight("Dark Mode") / 2.0f, new Color(210, 210, 210).getRGB());

        // Next

        RenderHelper.drawFilledRoundedRectangle(sr.getScaledWidth() - 50, sr.getScaledHeight() - 30, sr.getScaledWidth() - 10, sr.getScaledHeight() - 10, 5, UIUtil.getPreferredColor().getRGB());
        FontManager.instance.robotoLightSmall.drawString("Next", sr.getScaledWidth() - 30 - FontManager.instance.robotoLightSmall.getStringWidth("Next") / 2, sr.getScaledHeight() - 20 - FontManager.instance.robotoLightSmall.getStringHeight("Next") / 2, UIUtil.getOppositeFontColor().getRGB());
    }
}
