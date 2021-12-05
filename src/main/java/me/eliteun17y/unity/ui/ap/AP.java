package me.eliteun17y.unity.ui.ap;

import me.eliteun17y.unity.util.font.manager.FontManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class AP extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(255, 0, 0).getRGB());
        FontManager.instance.robotoBlack.drawString("Fuck you, piracy is no joke.", 0, 0, new Color(0, 0, 0).getRGB());
    }
}
