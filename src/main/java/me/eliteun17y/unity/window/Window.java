package me.eliteun17y.unity.window;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Window {
    public float x;
    public float y;

    public float width;
    public float height;

    public float oldX;
    public float oldY;

    public float oldWidth;
    public float oldHeight;

    public String name;
    public String description;

    public boolean focused;
    public boolean minimised;
    public boolean maximised;
    public boolean beingDragged;

    public float dragX;
    public float dragY;

    public Window(String name, String description) {
        this.name = name;
        this.description = description;
        y = 20;
    }

    public boolean isMinimised() {
        return minimised;
    }

    public void setMinimised(boolean minimised) {
        this.minimised = minimised;
    }

    public boolean isMaximised() {
        return maximised;
    }

    public void setMaximised(boolean maximised) {
        this.maximised = maximised;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        RenderHelper.drawFilledRoundedRectangle((int) x - 3, (int) y - 10, (int) (x + width) + 3, (int) (y + height), 5, UIUtil.getNormalColor2().getRGB());
        RenderHelper.drawRoundedRectangle((int) x - 3, (int) y - 10, (int) (x + width) + 3, (int) (y), 5, UIUtil.getNormalColor().getRGB());
        RenderHelper.drawMaximise(x + width - 5, y - 7.5f, x + width, y - 2.5f, UIUtil.getOppositeNormalColor2().getRGB());
        RenderHelper.drawMinimise(x + width - 15, y - 5, x + width - 10, UIUtil.getOppositeNormalColor2().getRGB());

        if(beingDragged) {
            x = mouseX + dragX;
            y = mouseY + dragY;
        }
    }

    public void click(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0) {
            if(minimised) {
                ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
                float x = 0;
                for(Window window : Unity.instance.windowManager.getWindows()) {
                    if(window.isMinimised() && window != this)
                        x += x + 6 + FontManager.instance.robotoLightSmall.getStringWidth(window.name);
                }
                if(mouseX >=  x && mouseX <= x + 6 + FontManager.instance.robotoLightSmall.getStringWidth(name) && mouseY >= sr.getScaledHeight() - 30 && mouseY <= sr.getScaledHeight()) {
                    minimised = false;
                }
            }

            if(mouseX >= x - 3 && mouseY >= y - 10 && mouseX <= x + width - 15 && mouseY <= y) {
                if(maximised) {
                    width = oldWidth;
                    height = oldHeight;
                    x = mouseX - width / 2;
                    y = mouseY + 10;
                    maximised = false;
                }
                dragX = x - mouseX;
                dragY = y - mouseY;
                beingDragged = true;
            }

            if(mouseX >= x - 3 && mouseY >= y - 10 && mouseX <= x + width + 3 && mouseY <= y + height) {
                focused = true;
            }else {
                focused = false;
                return;
            }

            if(mouseX >= x + width - 5 && mouseY >= y - 7.5f && mouseX <= x + width && mouseY <= y - 2.5f) {
                if(!maximised) {
                    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

                    oldX = x;
                    oldY = y;
                    oldWidth = width;
                    oldHeight = height;

                    x = 3;
                    y = 10;
                    width = sr.getScaledWidth() - 6;
                    height = sr.getScaledHeight() - 10;
                    maximised = true;
                }else {
                    x = oldX;
                    y = oldY;
                    width = oldWidth;
                    height = oldHeight;
                    maximised = false;
                }
            }
            if(mouseX >= x + width - 15 && mouseY >= y - 7.5f && mouseX <= x + width - 10 && mouseY <= y - 2.5f) {
                minimised = true;
            }
        }
    }

    public void release(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0) {
            if(beingDragged) {
                x = mouseX + dragX;
                y = mouseY + dragY;
                dragX = 0;
                dragY = 0;
                beingDragged = false;
            }
        }
    }

    public void key(char character, int key) {

    }
}