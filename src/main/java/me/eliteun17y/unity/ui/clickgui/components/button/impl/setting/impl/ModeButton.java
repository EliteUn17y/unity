package me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.impl;

import me.eliteun17y.unity.ui.clickgui.components.SettingPanel;
import me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.SettingButton;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class ModeButton extends SettingButton {
    public boolean opened;

    public ModeButton(float x, float y, ModeValue value) {
        super(x, y, value);
        opened = false;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1, 1, 1);

        if(opened) {
            int y = 0;

            for(String str : ((ModeValue)value).getModes()) {
                y += FontManager.instance.robotoLightSmall.getStringHeight(str) + 4;
            }
            RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor((int) x, (int) this.y, (int) x + 100, (int) (this.y + FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) + y), 3, UIUtil.getNormalColor().getRGB(), new Color(UIUtil.getNormalColor().getRed(), UIUtil.getNormalColor().getGreen(), UIUtil.getNormalColor().getBlue(), 100).getRGB());

            //Minecraft.getMinecraft().renderEngine.bindTexture(UIUtil.getModeExtended());
            //Gui.drawScaledCustomSizeModalRect((int) x, (int) this.y, 0, 0, 60, (int) FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) + y, 60,  (int) FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) + y, 60, (int) FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) + y);
        } else
            RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor((int) x, (int) y, (int) x + 100, (int) (y + FontManager.instance.robotoLightSmall.getStringHeight(value.getName())), 3, UIUtil.getNormalColor().getRGB(), new Color(UIUtil.getNormalColor().getRed(), UIUtil.getNormalColor().getGreen(), UIUtil.getNormalColor().getBlue(), 100).getRGB());

        //Minecraft.getMinecraft().renderEngine.bindTexture(UIUtil.getDropdown());
        //Gui.drawScaledCustomSizeModalRect((int) x + 48, (int) ((int) y + 2.5f), 0, 0, 10,  10, 10,  10, 10, 10);
        RenderHelper.drawTriangle((int) x + 88, (int) ((int) y + 4f), 6, UIUtil.getOppositeNormalColor().getRGB());
        FontManager.instance.robotoRegularSmall.drawString(((ModeValue)value).getMode(), x + 5, y - 0.5f, UIUtil.getFontColor().getRGB());

        if(opened) {
            int y = (int) FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) + 2;

            for (String str : ((ModeValue) value).getModes()) {
                FontManager.instance.robotoLightSmall.drawString(str, x+5, this.y + y, UIUtil.getFontColor().getRGB());
                y += FontManager.instance.robotoLightSmall.getStringHeight(str) + 4;
            }
        }
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
        int y = (int) FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) + 2;

        if(opened) {
            if(mouseButton == 0) {
                for (String str : ((ModeValue) value).getModes()) {
                    System.out.println(str);
                    if (mouseX > this.x + 5 && mouseX < this.x + 100 && mouseY > this.y + y && mouseY < this.y + y + FontManager.instance.robotoLightSmall.getStringHeight(str)) {
                        ((ModeValue) value).setMode(str);
                        opened = false;
                    }
                    y += FontManager.instance.robotoLightSmall.getStringHeight(str) + 4;
                }
            }
        }

        if(mouseButton == 0)
            if(isHovered(mouseX, mouseY))
                opened = !opened;

        if (opened) {
            SettingPanel.openModeButton = this;
        }
        SettingPanel.isModeButtonOpened = opened;
    }

    public boolean isHovered(int x, int y) {
        return x > this.x && x < this.x + 100 && y > this.y && y < this.y + FontManager.instance.robotoLightSmall.getStringHeight(value.getName());
    }
}