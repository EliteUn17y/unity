package me.eliteun17y.unity.ui.setup;

import me.eliteun17y.unity.ui.setup.stages.Stage0;
import me.eliteun17y.unity.util.Reference;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class Setup extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), UIUtil.getNormalColor().getRGB());

        // Title

        GlStateManager.scale(4, 4, 4);
        FontManager.instance.robotoRegular.drawString("Welcome!", (sr.getScaledWidth() / 2.0f - (FontManager.instance.robotoRegular.getStringWidth("Welcome!") / 2.0f * 4.0f)) / 4.0f, (sr.getScaledHeight() / 2.0f) / 4.0f - (FontManager.instance.robotoRegular.getStringHeight("Welcome!") / 2), UIUtil.getFontColor().getRGB());
        GlStateManager.scale(0.25, 0.25, 0.25);

        // Next

        RenderHelper.drawFilledRoundedRectangle(sr.getScaledWidth() - 50, sr.getScaledHeight() - 30, sr.getScaledWidth() - 10, sr.getScaledHeight() - 10, 5, UIUtil.getPreferredColor().getRGB());
        FontManager.instance.robotoLightSmall.drawString("Next", sr.getScaledWidth() - 30 - FontManager.instance.robotoLightSmall.getStringWidth("Next") / 2, sr.getScaledHeight() - 20 - FontManager.instance.robotoLightSmall.getStringHeight("Next") / 2, UIUtil.getOppositeFontColor().getRGB());
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if(mouseButton == 0) {
            if(mouseX >= sr.getScaledWidth() - 50 && mouseX <= sr.getScaledWidth() - 10 && mouseY >= sr.getScaledHeight() - 30 && mouseY <= sr.getScaledHeight() - 10) {
                mc.displayGuiScreen(new Stage0());
            }
        }
    }
}
