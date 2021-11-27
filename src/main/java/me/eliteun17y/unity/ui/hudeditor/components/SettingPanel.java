package me.eliteun17y.unity.ui.hudeditor.components;

import me.eliteun17y.unity.ui.clickgui.ClickGUI;
import me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.SettingButton;
import me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.impl.*;
import me.eliteun17y.unity.ui.hudeditor.HUDEditor;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.util.setting.impl.*;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.widgets.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.ArrayList;

public class SettingPanel {
    public Widget widget;
    public ArrayList<SettingButton> buttons = new ArrayList<>();
    public static ModeButton openModeButton;
    public static boolean isModeButtonOpened;

    public SettingPanel(Widget widget) {
        this.widget = widget;

        calculateSettings();
    }

    public void calculateSettings() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float y = sr.getScaledHeight() / 2.0f - 70*2 + 25;
        buttons.clear();

        for(Value value : widget.getValues()) {
            if (value instanceof BooleanValue)
                buttons.add(new TickboxButton(sr.getScaledWidth() / 2.0f - 100 * 2 + 1 + FontManager.instance.robotoLightSmall.getStringWidth(value.getName()) + 8, y + 2, (BooleanValue) value));
            if (value instanceof ModeValue)
                buttons.add(new ModeButton(sr.getScaledWidth() / 2.0f - 100 * 2 + 1 + FontManager.instance.robotoLightSmall.getStringWidth(value.getName()) + 8, y + FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) / 2 - 5, (ModeValue) value));
            if (value instanceof NumberValue)
                buttons.add(new NumberButton(sr.getScaledWidth() / 2.0f - 100 * 2 + 1 + FontManager.instance.robotoLightSmall.getStringWidth(value.getName()) + 8, y + 2.5f, (NumberValue) value));
            if (value instanceof TextValue)
                buttons.add(new TextButton(sr.getScaledWidth() / 2.0f - 100 * 2 + 1 + FontManager.instance.robotoLightSmall.getStringWidth(value.getName()) + 8, y + FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) / 2 - 5, (TextValue) value));
            if (value instanceof ColorValue)
                buttons.add(new ColorButton(sr.getScaledWidth() / 2.0f - 100 * 2 + 1 + FontManager.instance.robotoLightSmall.getStringWidth(value.getName()) + 8, y + 15, (ColorValue) value));
            if(!(value instanceof ColorValue))
                y += FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) + 4;
            else
                y += 80 + 4;
        }
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        HUDEditor.setPanel(this);
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        GlStateManager.color(1, 1, 1);
        ClickGUI.setSettingsOpened(true);
        RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor(sr.getScaledWidth() / 2 - 100*2, sr.getScaledHeight() / 2 - 70*2, sr.getScaledWidth() / 2 - 100*2 + 400, sr.getScaledHeight() / 2 - 70*2 + 280, 10, UIUtil.getNormalColor().getRGB(), new Color(UIUtil.getNormalColor().getRed(), UIUtil.getNormalColor().getGreen(), UIUtil.getNormalColor().getBlue(), 100).getRGB());
        //Minecraft.getMinecraft().renderEngine.bindTexture(UIUtil.getSettingsPanel());
        //Gui.drawScaledCustomSizeModalRect(sr.getScaledWidth() / 2 - 100*2, sr.getScaledHeight() / 2 - 70*2, 0, 0, 100*4,  70*4, 100*4, 70*4, 100*4, 70*4);

        FontManager.instance.robotoLight.drawString(widget.getName(), sr.getScaledWidth() / 2.0f - FontManager.instance.robotoLight.getStringWidth(widget.getName())/2, sr.getScaledHeight() / 2.0f - 70*2 + 6, UIUtil.getFontColor().getRGB());

        float y = sr.getScaledHeight() / 2.0f - 70*2 + 25;
        for(Value value : widget.getValues()) {
            FontManager.instance.robotoLightSmall.drawString(value.getName(), sr.getScaledWidth() / 2.0f - 100 * 2 + 5, y, UIUtil.getFontColor().getRGB());
            if(!(value instanceof ColorValue))
                y += FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) + 4;
            else
                y += 80 + 4;
            //y += FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) + 4;
        }

        for(int i = 0; i < buttons.size(); i++) {
            SettingButton settingButton = buttons.get(i);
            if (!(settingButton instanceof ModeButton))
                settingButton.draw(mouseX, mouseY, partialTicks);
        }

        for(int i = 0; i < buttons.size(); i++) {
            SettingButton settingButton = buttons.get(i);
            if (settingButton instanceof ModeButton)
                settingButton.draw(mouseX, mouseY, partialTicks);
        }
    }

    public void click(int mouseX, int mouseY, int mouseButton) {
        if(isModeButtonOpened)
            openModeButton.click(mouseX, mouseY, mouseButton);
        else
            for(int i = 0; i < buttons.size(); i++) {
                SettingButton settingButton = buttons.get(i);
                settingButton.click(mouseX, mouseY, mouseButton);
            }
    }

    public void release(int mouseX, int mouseY, int mouseButton) {
        if(isModeButtonOpened)
            openModeButton.release(mouseX, mouseY, mouseButton);
        else
            for(int i = 0; i < buttons.size(); i++) {
                SettingButton settingButton = buttons.get(i);
                settingButton.release(mouseX, mouseY, mouseButton);
            }
    }

    public void key(char character, int key) {
        for(int i = 0; i < buttons.size(); i++) {
            SettingButton settingButton = buttons.get(i);
            settingButton.key(character, key);
        }
    }
}
