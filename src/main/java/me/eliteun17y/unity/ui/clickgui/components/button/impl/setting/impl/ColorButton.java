package me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.impl;

import me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.SettingButton;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.setting.impl.ColorValue;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class ColorButton extends SettingButton {
    public double selectedX;
    public double selectedY;
    public int radius;
    public boolean gettingDragged;
    public boolean gettingDragged2;
    public int alpha;
    public int brightness;
    public boolean opened;

    public ColorButton(float x, float y, ColorValue value) {
        super(x, y, value);
        radius = 30;
        gettingDragged = false;
        opened = false;
        setColor(value.getObject());
        alpha = value.getObject().getAlpha();
        float[] hsb = Color.RGBtoHSB(value.getObject().getRed(), value.getObject().getBlue(), value.getObject().getGreen(), null);
        System.out.println(hsb[2]);
        brightness = (int) (hsb[2] * 100);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if(opened) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            Gui.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(UIUtil.getOppositeNormalColor2().getRed(), UIUtil.getOppositeNormalColor2().getBlue(), UIUtil.getOppositeNormalColor2().getGreen(), 100).getRGB());

            RenderHelper.drawFilledRoundedRectangle(sr.getScaledWidth() / 2.0f - 120, sr.getScaledHeight() / 2.0f - 80, sr.getScaledWidth() / 2.0f + 120, sr.getScaledHeight() / 2.0f + 60, 3, UIUtil.getOppositeNormalColor2().getRGB());
            FontManager.instance.robotoLight.drawString("Color Picker", sr.getScaledWidth() / 2.0f - FontManager.instance.robotoLight.getStringWidth("Color Picker") / 2.0f, sr.getScaledHeight() / 2.0f - 70, UIUtil.getOppositeFontColor().getRGB());

            FontManager.instance.robotoRegular.drawString("X", ((sr.getScaledWidth() / 2.0f + 120) - FontManager.instance.robotoRegular.getStringWidth("X")) - 6, (sr.getScaledHeight() / 2.0f - 80) + 6, UIUtil.getNormalColor().getRGB());

            RenderHelper.drawColorCircle((int) (sr.getScaledWidth() / 2.0f), (int) (sr.getScaledHeight() / 2.0f - 20), radius, -1);
            RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor((int) (sr.getScaledWidth() / 2.0f - 50), (int) (sr.getScaledHeight() / 2.0f + 15), (int) (sr.getScaledWidth() / 2.0f + 50), (int) (sr.getScaledHeight() / 2.0f + 20), 2, UIUtil.getNormalColor().getRGB(), new Color(UIUtil.getNormalColor().getRed(), UIUtil.getNormalColor().getGreen(), UIUtil.getNormalColor().getBlue(), 100).getRGB());

            int max = 100;
            int min = 0;

            if(!gettingDragged)
                RenderHelper.drawFilledCircle((int) (((((sr.getScaledWidth() / 2.0f - 50) + ((1.0f / (max - min) / 100.0f * (brightness - min) * 100)) * 100) - 5)) + 2.5f), (int) (sr.getScaledHeight() / 2.0f + 17), 5, UIUtil.getOppositeNormalColor().getRGB());
            else
                RenderHelper.drawFilledCircle((int) (mouseX + 2.5f), (int) (sr.getScaledHeight() / 2.0f + 17), 5, UIUtil.getOppositeNormalColor().getRGB());

            if(gettingDragged) {
                float x = mouseX - (sr.getScaledWidth() / 2.0f - 50);
                float val = min + ((max - min) * x) / 100;

                if(val > max)
                    val = max;

                if(val < min)
                    val = min;
                FontManager.instance.robotoRegularSmall.drawString("Brightness: " + round(val, 1), (int) (sr.getScaledWidth() / 2.0f - FontManager.instance.robotoRegularSmall.getStringWidth("Brightness: " + round(val, 1))), (int) (sr.getScaledHeight() / 2.0f + 20), UIUtil.getOppositeFontColor().getRGB());
            }else
                FontManager.instance.robotoRegularSmall.drawString("Brightness: " + brightness, (int) (sr.getScaledWidth() / 2.0f - FontManager.instance.robotoRegularSmall.getStringWidth("Brightness: " + brightness) / 2), (int) (sr.getScaledHeight() / 2.0f + 20), UIUtil.getOppositeFontColor().getRGB());

            // Alpha
            RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor((int) (sr.getScaledWidth() / 2.0f - 50), (int) (sr.getScaledHeight() / 2.0f + 37), (int) (sr.getScaledWidth() / 2.0f + 50), (int) (sr.getScaledHeight() / 2.0f + 43), 2, UIUtil.getNormalColor().getRGB(), new Color(UIUtil.getNormalColor().getRed(), UIUtil.getNormalColor().getGreen(), UIUtil.getNormalColor().getBlue(), 100).getRGB());

            int max2 = 255;
            int min2 = 0;

            if(!gettingDragged2)
                RenderHelper.drawFilledCircle((int) (((((sr.getScaledWidth() / 2.0f - 50) + ((1.0f / (max2 - min2) / 100.0f * (alpha - min2) * 100)) * 100) - 5)) + 2.5f), sr.getScaledHeight() / 2.0f + 39, 5, UIUtil.getOppositeNormalColor().getRGB());
            else
                RenderHelper.drawFilledCircle((int) (mouseX + 2.5f), sr.getScaledHeight() / 2.0f + 39, 5, UIUtil.getOppositeNormalColor().getRGB());


            if(gettingDragged2) {
                float x = mouseX - (sr.getScaledWidth() / 2.0f - 50);
                float val = min2 + ((max2 - min2) * x) / 100;

                if(val > max2)
                    val = max2;

                if(val < min2)
                    val = min2;

                FontManager.instance.robotoRegularSmall.drawString("Alpha: " + round(val, 1), (int) (sr.getScaledWidth() / 2.0f - FontManager.instance.robotoRegularSmall.getStringWidth("Alpha: " + round(val, 1)) / 2), (int) (sr.getScaledHeight() / 2.0f + 43), UIUtil.getOppositeFontColor().getRGB());
            }else
                FontManager.instance.robotoRegularSmall.drawString("Alpha: " + alpha, (int) (sr.getScaledWidth() / 2.0f - FontManager.instance.robotoRegularSmall.getStringWidth("Alpha: " + alpha) / 2), (int) (sr.getScaledHeight() / 2.0f + 43), UIUtil.getOppositeFontColor().getRGB());


            int x = (int) (sr.getScaledWidth() / 2.0f);
            int y = (int) (sr.getScaledHeight() / 2.0f - 20);
            RenderHelper.drawFilledCircle((int) selectedX + x, (int) selectedY + y, 5, UIUtil.getNormalColor().getRGB());
        }else {
            RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor((int) x, (int) y, (int) x + 65, (int) y + 15, 3, UIUtil.getNormalColor2().getRGB(), new Color(UIUtil.getNormalColor2().getRed(), UIUtil.getNormalColor2().getBlue(), UIUtil.getNormalColor2().getGreen(), 100).getRGB());
            FontManager.instance.robotoLightSmall.drawString("Open picker", x + 32.5f - FontManager.instance.robotoLightSmall.getStringWidth("Open picker") / 2, y + 7.5f - FontManager.instance.robotoLightSmall.getStringHeight("Open picker") / 2, UIUtil.getFontColor().getRGB());
        }

    }

    private static double round(double value, int places) {
        return places*(Math.round(value/places));
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
        //FontManager.instance.robotoRegular.drawString("X", ((sr.getScaledWidth() / 2.0f + 120) - FontManager.instance.robotoRegular.getStringWidth("X")) - 6, (sr.getScaledHeight() / 2.0f - 80) + 6, UIUtil.getNormalColor().getRGB());

        if(mouseButton == 0) {
            if(opened) {
                ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

                if(mouseX >= ((sr.getScaledWidth() / 2.0f + 120) - FontManager.instance.robotoRegular.getStringWidth("X")) - 6 && mouseX <= (sr.getScaledWidth() / 2.0f + 120) - 6 && mouseY >= (sr.getScaledHeight() / 2.0f - 80) + 6 && mouseY <= ((sr.getScaledHeight() / 2.0f - 80) + 6) + FontManager.instance.robotoRegular.getStringHeight("X")) {
                    opened = false;
                }

                int x = (int) (sr.getScaledWidth() / 2.0f);
                int y = (int) (sr.getScaledHeight() / 2.0f - 20);
                if(isHovered(x, y, radius, mouseX, mouseY)) {
                    int pX = mouseX - x;
                    int pY = mouseY - y;
                    selectedX = pX;
                    selectedY = pY;
                    System.out.println((float) (Math.hypot(selectedX, selectedY) / radius));
                    Color color = Color.getHSBColor(getNormalized(), (float) (Math.hypot(selectedX, selectedY) / this.radius), brightness / 100.0f);
                    ((ColorValue) value).setObject(new Color(color.getRed(), color.getBlue(), color.getGreen(), alpha));
                }
                if(isCircleHovered(mouseX, mouseY)) {
                    gettingDragged = true;
                }

                // Alpha

                if(isCircleHovered2(mouseX, mouseY)) {
                    gettingDragged2 = true;
                }
                //if(isHovered(mouseX, mouseY)) {
                //float x = mouseX - this.x;
                //float val = ((NumberValue) value).getMin() + ((((NumberValue) value).getMax() - ((NumberValue) value).getMin()) * x) / 100;
                // ((NumberValue) value).setObject(round(val, (int) ((NumberValue) value).getIncrement()));
                //}
            }else {
                if(isOpenHovered(mouseX, mouseY)) {
                    opened = true;
                }
            }
        }
    }

    @Override
    public void release(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0) {
            int max = 100;
            int min = 0;
            if(gettingDragged) {
                gettingDragged = false;

                ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

                float x = mouseX - (sr.getScaledWidth() / 2.0f - 50);
                float val = min + ((max - min) * x) / 100;

                if(val > max)
                    val = max;

                if(val < min)
                    val = min;
                System.out.println((float) (Math.hypot(selectedX, selectedY) / radius));

                brightness = (int) round(val, 1);
                Color color = Color.getHSBColor(getNormalized(), (float) (Math.hypot(selectedX, selectedY) / this.radius), brightness / 100.0f);
                ((ColorValue) value).setObject(new Color(color.getRed(), color.getBlue(), color.getGreen(), alpha));
            }

            int max2 = 255;
            int min2 = 0;
            if(gettingDragged2) {
                gettingDragged2 = false;

                ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

                float x = mouseX - (sr.getScaledWidth() / 2.0f - 50);
                float val = min2 + ((max2 - min2) * x) / 100;

                if(val > max2)
                    val = max2;

                if(val < min2)
                    val = min2;

                alpha = (int) round(val, 1);
                int pX = (int) (mouseX - (int) (sr.getScaledWidth() / 2.0f) - radius / 2);
                int pY = (int) (mouseY - (int) (sr.getScaledHeight() / 2.0f - 20) - radius / 2);
                Color color = Color.getHSBColor(getNormalized(), (float) (Math.hypot(selectedX, selectedY) / this.radius), brightness / 100.0f);
                ((ColorValue) value).setObject(new Color(color.getRed(), color.getBlue(), color.getGreen(), alpha));
            }
        }
    }

    public boolean isHovered(double x, double y, double radius, double pX, double pY) {
        return ((x - pX) * (x - pX)) + ((y - pY) * (y - pY)) <= radius * radius;
    }

    public boolean isOpenHovered(int x, int y) {
        //             RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor((int) x, (int) y, (int) x + 65, (int) y + 15, 3, UIUtil.getNormalColor2().getRGB(), new Color(UIUtil.getNormalColor2().getRed(), UIUtil.getNormalColor2().getBlue(), UIUtil.getNormalColor2().getGreen(), 100).getRGB());
        return x >= this.x && y >= this.y && x <= this.x + 65 && y <= this.y + 15;
    }

    public boolean isCircleHovered(int x, int y) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int max = 100;
        int min = 0;
        return x > (int) ((((sr.getScaledWidth() / 2.0f - 50) + ((1.0f / (max - min) / 100.0f * (brightness - min) * 100)) * 100) - 10)) && x < (int) ((((sr.getScaledWidth() / 2.0f - 50) + ((1.0f / (max - min) / 100.0f * (brightness - min) * 100)) * 100) - 5)) + 10 && y > sr.getScaledHeight() / 2.0f + 12 && y < sr.getScaledHeight() / 2.0f + 22;
    }

    public boolean isCircleHovered2(int x, int y) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int max = 255;
        int min = 0;
        return x > (int) ((((sr.getScaledWidth() / 2.0f - 50) + ((1.0f / (max - min) / 100.0f * (alpha - min) * 100)) * 100) - 10)) && x < (int) ((((sr.getScaledWidth() / 2.0f - 50) + ((1.0f / (max - min) / 100.0f * (alpha - min) * 100)) * 100) - 5)) + 10 && y > sr.getScaledHeight() / 2.0f + 34 && y < sr.getScaledHeight() / 2.0f + 44;
    }

    public void setColor(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getBlue(), color.getGreen(), null);

        this.selectedX = hsb[1] * this.radius * (Math.sin(Math.toRadians(hsb[0] * 360)) / Math.sin(Math.toRadians(90)));
        this.selectedY = hsb[1] * this.radius * (Math.sin(Math.toRadians(90 - (hsb[0] * 360))) / Math.sin(Math.toRadians(90)));
    }

    private float getNormalized() {
        return (float)((-Math.toDegrees(Math.atan2(selectedY, -selectedX)) + 450) % 360) / 360;
    }
}
