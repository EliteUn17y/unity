package me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.impl;

import me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.SettingButton;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.renderer.GlStateManager;

public class TickboxButton extends SettingButton {
    public TickboxButton(float x, float y, BooleanValue value) {
        super(x, y, value);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1, 1, 1);

        if(((BooleanValue) value).getObject())
            RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor((int) x, (int) y, (int) x + 10, (int) y + 10, 3, UIUtil.getNormalColor().getRGB(), UIUtil.getPreferredColor().getRGB());
        else
            RenderHelper.drawRoundedRectangle((int) x, (int) y, (int) x + 10, (int) y + 10, 3, UIUtil.getNormalColor().getRGB());
        //Minecraft.getMinecraft().renderEngine.bindTexture(UIUtil.getTickbox());
        //Gui.drawScaledCustomSizeModalRect((int) x, (int) y, 0, 0, 10,  10, 10,  10, 10, 10);
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0)
            if(isHovered(mouseX, mouseY))
                ((BooleanValue) value).setObject(!((BooleanValue) value).getObject());
    }

    public boolean isHovered(int x, int y) {
        return x > this.x && x < this.x + 10 && y > this.y && y < this.y + 10;
    }
}
