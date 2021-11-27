package me.eliteun17y.unity.ui.hudeditor;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.ui.hudeditor.components.SettingPanel;
import me.eliteun17y.unity.ui.hudeditor.components.button.impl.WidgetButton;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.math.Vector3;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.widgets.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class HUDEditor extends GuiScreen {
    public ArrayList<WidgetButton> widgetButtons;
    public ArrayList<WidgetButton> widgetButtonsForShowcaseWidgets;
    public ArrayList<Vector3> widgetsToToggleOff;
    public boolean opened;
    private static boolean areSettingsOpened = false;
    private static SettingPanel panel;

    public HUDEditor() {
        widgetButtons = new ArrayList<>();
        widgetsToToggleOff = new ArrayList<>();
        widgetButtonsForShowcaseWidgets = new ArrayList<>();

        setSettingsOpened(false);
        setPanel(null);

        for(Widget widget : Unity.instance.widgetManager.getWidgets()) {
            if(widget.isToggled())
                widgetButtons.add(new WidgetButton(widget.getX(), widget.getY(), widget.getWidth(), widget.getHeight(), widget));
        }
        opened = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        RenderHelper.drawLineX(sr.getScaledHeight() / 2.0f, 0, sr.getScaledWidth(), UIUtil.getOppositeNormalColor().getRGB());
        RenderHelper.drawLineY(sr.getScaledWidth() / 2.0f, 0, sr.getScaledHeight(), UIUtil.getOppositeNormalColor().getRGB());

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();

        for(WidgetButton widgetButton : widgetButtonsForShowcaseWidgets) {
            widgetButton.height = widgetButton.widget.height;
            widgetButton.width = widgetButton.widget.width;
            widgetButton.x = widgetButton.widget.getX();
            widgetButton.y = widgetButton.widget.getY();
        }

        if(!opened) {
            if (!isHovered(mouseX, mouseY))
                RenderHelper.drawRoundedRectangle(-5, sr.getScaledHeight() / 2 - 20, 10, sr.getScaledHeight() / 2 + 20, 5, UIUtil.getNormalColor().getRGB());
            else
                RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor(-5, sr.getScaledHeight() / 2 - 20, 10, sr.getScaledHeight() / 2 + 20, 5, UIUtil.getNormalColor().getRGB(), new Color(UIUtil.getOppositeNormalColor().getRed(), UIUtil.getOppositeNormalColor().getGreen(), UIUtil.getOppositeNormalColor().getBlue(), 100).getRGB());
        }else {
            RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor(-5, 0, 260, sr.getScaledHeight(), 5, UIUtil.getNormalColor().getRGB(), new Color(UIUtil.getNormalColor().getRed(), UIUtil.getNormalColor().getGreen(), UIUtil.getNormalColor().getBlue(), 100).getRGB());

            ArrayList<WidgetButton> widgets = new ArrayList<>();

            int x = 6;
            int y = 6;
            for(Vector3 vec : widgetsToToggleOff) {
                Widget widget = (Widget) vec.getA();
                float xToChange = (float) vec.getB();
                float yToChange = (float) vec.getC();

                widget.setToggled(false);
                widget.setX(xToChange);
                widget.setY(yToChange);
            }

            for(Widget widget : Unity.instance.widgetManager.getWidgets()) {
                if(!widget.isToggled()) {
                    FontManager.instance.robotoRegular.drawString(widget.getName(), x, y, UIUtil.getFontColor().getRGB());
                    FontManager.instance.robotoLightSmall.drawString(widget.getDescription(), x, y + FontManager.instance.robotoRegular.getStringHeight(widget.getName()), UIUtil.getFontColor().getRGB());
                    float oldX = widget.getX();
                    float oldY = widget.getY();
                    widget.setToggled(true);
                    widget.setX(x);
                    widget.setY(y + FontManager.instance.robotoRegular.getStringHeight(widget.getName()) + FontManager.instance.robotoLightSmall.getStringHeight(widget.getDescription()) + 8);
                    if(widgetButtonsForShowcaseWidgets.stream().noneMatch(widgetButton -> widgetButton.widget.equals(widget)))
                        widgetButtonsForShowcaseWidgets.add(new WidgetButton(widget.getX(), widget.getY(), widget.getWidth(), widget.getHeight(), widget));
                    if(widgetsToToggleOff.stream().noneMatch(vector3 -> vector3.getA().equals(widget)))
                        widgetsToToggleOff.add(new Vector3(widget, oldX, oldY));
                    y += FontManager.instance.robotoRegular.getStringHeight(widget.getName()) + FontManager.instance.robotoLightSmall.getStringHeight(widget.getDescription()) + (widget.getHeight()*widget.getScale()) + 14;
                }
            }
        }


        if(!opened) {
            for(WidgetButton widgetButton : widgetButtons)
                if(widgetButton.widget.isToggled())
                    widgetButton.draw(mouseX, mouseY, partialTicks);
        }else {
            for(WidgetButton widgetButton : widgetButtons)
                if(widgetButton.widget.isToggled())
                    widgetButton.draw(-100, -100, partialTicks); // Change the mouse position to avoid being able to hover over the widgets while in the widget selection menu
        }

        for(int i = 0; i < widgetButtonsForShowcaseWidgets.size(); i++) {
            WidgetButton widgetButton = widgetButtonsForShowcaseWidgets.get(i);
            if (widgetButton.widget.isToggled()) {
                widgetButton.draw(mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(!opened) {
            if(isHovered(mouseX, mouseY))
                if (mouseButton == 0)
                    opened = true;

            for(WidgetButton widgetButton : widgetButtons)
                if (widgetButton.widget.isToggled())
                    widgetButton.click(mouseX, mouseY, mouseButton);
        }

        for(int i = 0; i < widgetButtonsForShowcaseWidgets.size(); i++) {
            WidgetButton widgetButton = widgetButtonsForShowcaseWidgets.get(i);
            if (widgetButton.widget.isToggled()) {
                widgetButton.click(mouseX, mouseY, mouseButton);
                if(widgetButton.isHovered(mouseX, mouseY)) {
                    if(mouseButton == 0) {
                        ArrayList<WidgetButton> buttonsToRemove = new ArrayList<>();

                        for(WidgetButton widgetButton1 : widgetButtonsForShowcaseWidgets) {
                            if(widgetButton.equals(widgetButton1)) continue;
                            buttonsToRemove.add(widgetButton1);
                            widgetButton1.widget.setToggled(false);
                        }
                        widgetButtonsForShowcaseWidgets.removeAll(buttonsToRemove);
                        opened = false;
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if(!opened) {
            for(int i = 0; i < widgetButtons.size(); i++) {
                WidgetButton widgetButton = widgetButtons.get(i);

                if(widgetButton.widget.isToggled()) {
                    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

                    if(widgetButton.isHovered(0, sr.getScaledHeight() / 2)) {
                        widgetButton.widget.setToggled(false);
                        widgetButtons.remove(widgetButton);
                    }else
                        widgetButton.release(mouseX, mouseY, mouseButton);
                }
            }
        }
        for(int i = 0; i < widgetButtonsForShowcaseWidgets.size(); i++) {
            WidgetButton widgetButton = widgetButtonsForShowcaseWidgets.get(i);
            if(widgetButton.beingDragged) {
                if(mouseButton == 0) {
                    widgetButtonsForShowcaseWidgets.remove(widgetButton);
                    widgetsToToggleOff.removeIf(vec -> vec.getA().equals(Unity.instance.widgetManager.getWidgets().get(Unity.instance.widgetManager.getWidgets().indexOf(widgetButton.widget))));
                    Unity.instance.widgetManager.getWidgets().get(Unity.instance.widgetManager.getWidgets().indexOf(widgetButton.widget)).setToggled(true);
                    widgetButtons.add(widgetButton);
                    widgetButton.release(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!opened)
            for(WidgetButton widgetButton : widgetButtons)
                if(widgetButton.widget.isToggled())
                    widgetButton.key(character, key);

        for(int i = 0; i < widgetButtonsForShowcaseWidgets.size(); i++) {
            WidgetButton widgetButton = widgetButtonsForShowcaseWidgets.get(i);
            if (widgetButton.widget.isToggled()) {
                widgetButton.key(character, key);
            }
        }
    }

    @Override
    public void onGuiClosed() {
        for(Vector3 vec : widgetsToToggleOff) {
            Widget widget = (Widget) vec.getA();
            float xToChange = (float) vec.getB();
            float yToChange = (float) vec.getC();

            widget.setToggled(false);
            widget.setX(xToChange);
            widget.setY(yToChange);
        }
    }

    public boolean isHovered(int x, int y) {
        ScaledResolution sr = new ScaledResolution(mc);
        return x >= 0 && x <= 10 && y >= sr.getScaledHeight() / 2 - 20 && y <= sr.getScaledHeight() / 2 + 20;
    }

    public static boolean areSettingsOpened() {
        return areSettingsOpened;
    }

    public static void setSettingsOpened(boolean settingsOpened) {
        areSettingsOpened = settingsOpened;
    }

    public static SettingPanel getPanel() {
        return panel;
    }

    public static void setPanel(SettingPanel pan) {
        panel = pan;
    }
}