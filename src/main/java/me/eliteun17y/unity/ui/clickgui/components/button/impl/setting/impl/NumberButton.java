package me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.impl;

import me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.SettingButton;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberButton extends SettingButton {
    public boolean gettingDragged;

    public NumberButton(float x, float y, NumberValue value) {
        super(x, y, value);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1, 1, 1);

        RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor((int) x, (int) (y + 2.5f), (int) x + 100, (int) (y + 2.5f + 5), 2, UIUtil.getNormalColor().getRGB(), new Color(UIUtil.getNormalColor().getRed(), UIUtil.getNormalColor().getGreen(), UIUtil.getNormalColor().getBlue(), 100).getRGB());
        //Minecraft.getMinecraft().renderEngine.bindTexture(UIUtil.getSliderRail());
        //Gui.drawScaledCustomSizeModalRect((int) x, (int) ((int) y + 2.5f), 0, 0,100,  5, 100,  5, 100, 5);

        //Minecraft.getMinecraft().renderEngine.bindTexture(UIUtil.getSlider());
        if(!gettingDragged)
            RenderHelper.drawFilledCircle((int) ((((x + ((1 / (((NumberValue) value).getMax() - ((NumberValue) value).getMin()) / 100 * (((NumberValue)value).getDouble() - ((NumberValue) value).getMin()) * 100)) * 100) - 5)) + 2.5f), (int) y + 4, 5, UIUtil.getOppositeNormalColor().getRGB());
        else
            RenderHelper.drawFilledCircle((int) (mouseX + 2.5f), (int) (y + 4f), 5, UIUtil.getOppositeNormalColor().getRGB());
            //Gui.drawScaledCustomSizeModalRect(mouseX, (int) y, 0, 0, 10,  10, 10,  10, 10, 10);
        if(gettingDragged) {
            double x = mouseX - this.x;
            double val = ((NumberValue) value).getMin() + ((((NumberValue) value).getMax() - ((NumberValue) value).getMin()) * x) / 100;

            if(val > ((NumberValue) value).getMax())
                val = ((NumberValue) value).getMax();

            if(val < ((NumberValue) value).getMin())
                val = ((NumberValue) value).getMin();

            BigDecimal bd = new BigDecimal(round(val, ((NumberValue) value).getIncrement())).setScale(2, RoundingMode.HALF_UP);
            FontManager.instance.robotoRegularSmall.drawString("" + bd, this.x + 110, (y + 4) - FontManager.instance.robotoRegularSmall.getStringHeight("" + ((NumberValue) value).getDouble()) / 2, UIUtil.getFontColor().getRGB());
        }else
            FontManager.instance.robotoRegularSmall.drawString("" + ((NumberValue) value).getDouble(), x + 110, (y + 4) - FontManager.instance.robotoRegularSmall.getStringHeight("" + ((NumberValue) value).getDouble()) / 2, UIUtil.getFontColor().getRGB());
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0) {
            if(isCircleHovered(mouseX, mouseY)) {
                gettingDragged = true;
            }
            if(isHovered(mouseX, mouseY)) {
                float x = mouseX - this.x;
                double val = ((NumberValue) value).getMin() + ((((NumberValue) value).getMax() - ((NumberValue) value).getMin()) * x) / 100;
                BigDecimal bd = new BigDecimal(round(val, ((NumberValue) value).getIncrement())).setScale(2, RoundingMode.HALF_UP);
                ((NumberValue) value).setObject(bd);
            }
        }
    }

    @Override
    public void release(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0) {
            if(gettingDragged) {
                gettingDragged = false;

                double x = mouseX - this.x;
                double val = ((NumberValue) value).getMin() + ((((NumberValue) value).getMax() - ((NumberValue) value).getMin()) * x) / 100;

                if(val > ((NumberValue) value).getMax())
                    val = ((NumberValue) value).getMax();

                if(val < ((NumberValue) value).getMin())
                    val = ((NumberValue) value).getMin();

                BigDecimal bd = new BigDecimal(round(val, ((NumberValue) value).getIncrement())).setScale(2, RoundingMode.HALF_UP);
                ((NumberValue) value).setObject(bd);
            }
        }
    }

    private static double round(double value, double places) {
        return places*(Math.round(value/places));
    }

    public boolean isHovered(int x, int y) {
        return x > this.x + 2.5f && x < this.x + 2.5f + 100 && y > this.y && y < this.y + 5;
    }

    public boolean isCircleHovered(int x, int y) {
        return x > (int) (((this.x + ((1 / (((NumberValue) value).getMax() - ((NumberValue) value).getMin()) / 100 * (((NumberValue)value).getDouble() - ((NumberValue) value).getMin()) * 100)) * 100) - 10)) && x < (int) (((this.x + ((1 / (((NumberValue) value).getMax() - ((NumberValue) value).getMin()) / 100 * (((NumberValue)value).getDouble() - ((NumberValue) value).getMin()) * 100)) * 100) - 5)) + 10 && y > this.y && y < this.y + 10;
    }
}