package me.eliteun17y.unity.ui.hudeditor.components.button.impl;

import me.eliteun17y.unity.ui.hudeditor.HUDEditor;
import me.eliteun17y.unity.ui.hudeditor.components.SettingPanel;
import me.eliteun17y.unity.ui.hudeditor.components.button.Button;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.widgets.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class WidgetButton implements Button {
    public float x;
    public float y;
    public float width;
    public float height;

    public float draggingOffsetX;
    public float draggingOffsetY;
    public boolean beingDragged;

    public float startXScale;
    public boolean beingScaleDragged;

    public Widget widget;

    public boolean opened;
    public SettingPanel settingPanel;

    public WidgetButton(float x, float y, float width, float height, Widget widget) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.widget = widget;
        draggingOffsetX = 0;
        draggingOffsetY = 0;
        startXScale = 0;
        beingScaleDragged = false;
        settingPanel = new SettingPanel(widget);
        opened = false;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        width = widget.getWidth();
        height = widget.getHeight();

        if(beingScaleDragged) {
            double amountMoved = mouseX - startXScale;
            float oldX = widget.getX();
            float oldY = widget.getY();
            widget.setScale((float) (1 + ((amountMoved / width) * widget.getScale())));
            widget.setX(oldX);
            widget.setY(oldY);
        }

        if(beingDragged) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

            boolean nearXCenter = mouseX >= (sr.getScaledWidth() / 2.0f) - 10 && mouseX <= (sr.getScaledWidth() / 2.0f) + 10;
            boolean nearYCenter = mouseY >= (sr.getScaledHeight() / 2.0f) - 10 && mouseY <= (sr.getScaledHeight() / 2.0f) + 10;

            if(nearXCenter) {
                widget.setX((sr.getScaledWidth() / 2.0f - width / 2) / widget.getScale());
                x = (sr.getScaledWidth() / 2.0f - width / 2) / widget.getScale();
            }else{
                widget.setX((mouseX - (draggingOffsetX)) / widget.getScale());
                x = (mouseX - (draggingOffsetX)) / widget.getScale();
            }

            if(nearYCenter) {
                widget.setY((sr.getScaledHeight() / 2.0f - height / 2) / widget.getScale());
                y = (sr.getScaledHeight() / 2.0f - height / 2) / widget.getScale();
            }else{
                widget.setY((mouseY - (draggingOffsetY)) / widget.getScale());
                y = (mouseY - (draggingOffsetY)) / widget.getScale();
            }
        }

        if(!isHovered(mouseX, mouseY)) {
            RenderHelper.drawRoundedRectangle((int) (x * widget.getScale()) - 3, (int) (y * widget.getScale()) - 3, (int) (x * widget.getScale() + width) + 3, (int) (y * widget.getScale() + height) + 3, 3, UIUtil.getNormalColor().getRGB());
        }else{
            RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor((int) (x * widget.getScale()) - 3, (int) (y * widget.getScale()) - 3, (int) (x * widget.getScale() + width) + 3, (int) (y * widget.getScale() + height) + 3, 3, UIUtil.getNormalColor().getRGB(), new Color(UIUtil.getOppositeNormalColor().getRed(), UIUtil.getOppositeNormalColor().getGreen(), UIUtil.getOppositeNormalColor().getBlue(), 100).getRGB());
            widget.scale();
            FontManager.instance.robotoLightSmall.drawString(widget.getName(), x + (width / 2) / widget.getScale() - (FontManager.instance.robotoLightSmall.getStringWidth(widget.getName()) / 2), y + (height / 2) / widget.getScale() - (FontManager.instance.robotoLightSmall.getStringHeight(widget.getName()) / 2), UIUtil.getFontColor().getRGB());
            widget.unscale();
        }

        if(opened) {
            settingPanel.draw(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
        if(isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                draggingOffsetX = mouseX - widget.getX() * widget.getScale();
                draggingOffsetY = mouseY - widget.getY() * widget.getScale();
                beingDragged = true;
            }
        }
        if(isScaleHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                startXScale = mouseX;
                beingScaleDragged = true;
                widget.setScaling(true);
            }
        }

        if(opened) {
            settingPanel.click(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void release(int mouseX, int mouseY, int mouseButton) {
        if(!HUDEditor.areSettingsOpened()) {
            if(mouseButton == 1) {
                if(isHovered(mouseX, mouseY)) {
                    opened = !opened;
                    HUDEditor.setSettingsOpened(!HUDEditor.areSettingsOpened());
                }
            }
        }
        if(beingScaleDragged) {
            double amountMoved = mouseX - startXScale;
            widget.setScale((float) (1 + ((amountMoved / width) * widget.getScale())));
            beingScaleDragged = false;
            widget.setScaling(false);
        }
        if(beingDragged) {
            if(mouseButton == 0) {
                ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
                boolean nearXCenter = mouseX >= (sr.getScaledWidth() / 2.0f) - 10 && mouseX <= (sr.getScaledWidth() / 2.0f) + 10;
                boolean nearYCenter = mouseY >= (sr.getScaledHeight() / 2.0f) - 10 && mouseY <= (sr.getScaledHeight() / 2.0f) + 10;

                if(nearXCenter) {
                    widget.setX((sr.getScaledWidth() / 2.0f - width / 2) / widget.getScale());
                    x = (sr.getScaledWidth() / 2.0f - width / 2) / widget.getScale();
                }else{
                    widget.setX((mouseX - (draggingOffsetX)) / widget.getScale());
                    x = (mouseX - (draggingOffsetX)) / widget.getScale();
                }

                if(nearYCenter) {
                    widget.setY((sr.getScaledHeight() / 2.0f - height / 2) / widget.getScale());
                    y = (sr.getScaledHeight() / 2.0f - height / 2) / widget.getScale();
                }else{
                    widget.setY((mouseY - (draggingOffsetY)) / widget.getScale());
                    y = (mouseY - (draggingOffsetY)) / widget.getScale();
                }

                draggingOffsetX = 0;
                draggingOffsetY = 0;
                beingDragged = false;
            }
        }

        if(opened) {
            settingPanel.release(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void key(char character, int key) {
        if(opened) {
            settingPanel.key(character, key);
        }
    }

    public boolean isScaleHovered(int x, int y) {
        return x >= this.x * widget.getScale() + width - 3 && x <= this.x * widget.getScale() + width + 3 && y >= this.y * widget.getScale() + height - 3 && y <= this.y * widget.getScale() + height + 3;
    }

    public boolean isHovered(int x, int y) {
        return x >= this.x * widget.getScale() - 3 && x <= this.x * widget.getScale() + width + 3 && y >= this.y * widget.getScale() - 3 && y <= this.y * widget.getScale() + height + 3 && !isScaleHovered(x, y);
    }
}
