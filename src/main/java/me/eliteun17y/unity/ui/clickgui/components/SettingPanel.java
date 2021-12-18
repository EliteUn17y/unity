package me.eliteun17y.unity.ui.clickgui.components;

import me.eliteun17y.unity.ui.clickgui.ClickGUI;
import me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.SettingButton;
import me.eliteun17y.unity.ui.clickgui.components.button.impl.setting.impl.*;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.util.setting.impl.*;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.ArrayList;

public class SettingPanel {
    public Module module;
    public ArrayList<SettingButton> buttons = new ArrayList<>();
    public static ModeButton openModeButton;
    public static boolean isModeButtonOpened;

    public SettingPanel(Module module) {
        this.module = module;

        calculateSettings();
    }

    public void calculateSettings() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float y = sr.getScaledHeight() / 2.0f - 70*3 + 25;
        buttons.clear();

        for(Value value : module.getValues()) {
            if (value instanceof BooleanValue)
                buttons.add(new TickboxButton(sr.getScaledWidth() / 2.0f - 100 * 3 + 1 + FontManager.instance.robotoLightSmall.getStringWidth(value.getName()) + 8, y + 2, (BooleanValue) value));
            if (value instanceof ModeValue)
                buttons.add(new ModeButton(sr.getScaledWidth() / 2.0f - 100 * 3 + 1 + FontManager.instance.robotoLightSmall.getStringWidth(value.getName()) + 8, y + FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) / 2 - 5, (ModeValue) value));
            if (value instanceof NumberValue)
                buttons.add(new NumberButton(sr.getScaledWidth() / 2.0f - 100 * 3 + 1 + FontManager.instance.robotoLightSmall.getStringWidth(value.getName()) + 8, y + 2.5f, (NumberValue) value));
            if (value instanceof TextValue)
                buttons.add(new TextButton(sr.getScaledWidth() / 2.0f - 100 * 3 + 1 + FontManager.instance.robotoLightSmall.getStringWidth(value.getName()) + 8, y + FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) / 2 - 5, (TextValue) value));
            if (value instanceof ColorValue)
                buttons.add(new ColorButton(sr.getScaledWidth() / 2.0f - 100 * 3 + 1 + FontManager.instance.robotoLightSmall.getStringWidth(value.getName()) + 8, y, (ColorValue) value));
            if(!(value instanceof ColorValue))
                y += FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) + 4;
            else
                y += FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) + 8;
        }
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        ClickGUI.setPanel(this);
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        GlStateManager.color(1, 1, 1);
        ClickGUI.setSettingsOpened(true);
        RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor(sr.getScaledWidth() / 2 - 100*3, sr.getScaledHeight() / 2 - 70*3, sr.getScaledWidth() / 2 - 100*2 + 500, sr.getScaledHeight() / 2 + 70*3, 10, UIUtil.getNormalColor().getRGB(), new Color(UIUtil.getNormalColor().getRed(), UIUtil.getNormalColor().getGreen(), UIUtil.getNormalColor().getBlue(), 100).getRGB());
        //Minecraft.getMinecraft().renderEngine.bindTexture(UIUtil.getSettingsPanel());
        //Gui.drawScaledCustomSizeModalRect(sr.getScaledWidth() / 2 - 100*2, sr.getScaledHeight() / 2 - 70*2, 0, 0, 100*4,  70*4, 100*4, 70*4, 100*4, 70*4);

        FontManager.instance.robotoLight.drawString(module.getName(), sr.getScaledWidth() / 2.0f - FontManager.instance.robotoLight.getStringWidth(module.getName())/2, sr.getScaledHeight() / 2.0f - 70*3 + 6, UIUtil.getFontColor().getRGB());

        float y = sr.getScaledHeight() / 2.0f - 70*3 + 25;
        for(Value value : module.getValues()) {
            FontManager.instance.robotoRegularSmall.drawString(value.getName(), sr.getScaledWidth() / 2.0f - 100 * 3 + 5, y, UIUtil.getFontColor().getRGB());
            if(!(value instanceof ColorValue))
                y += FontManager.instance.robotoRegularSmall.getStringHeight(value.getName()) + 4;
            else
                y += FontManager.instance.robotoRegularSmall.getStringHeight(value.getName()) + 8;
            //y += FontManager.instance.robotoLightSmall.getStringHeight(value.getName()) + 4;
        }

        ColorButton buttonOnTop = null;

        for(SettingButton settingButton : buttons) {
            if(settingButton instanceof ColorButton) {
                if(((ColorButton) settingButton).opened) {
                    buttonOnTop = (ColorButton) settingButton;
                    continue;
                }
            }
            if(!(settingButton instanceof ModeButton))
                settingButton.draw(mouseX, mouseY, partialTicks);
        }

        for(SettingButton settingButton : buttons)
            if(settingButton instanceof ModeButton)
                settingButton.draw(mouseX, mouseY, partialTicks);

        if(buttonOnTop != null)
            buttonOnTop.draw(mouseX, mouseY, partialTicks);
    }

    public void click(int mouseX, int mouseY, int mouseButton) {
        for(SettingButton settingButton : buttons) {
            if(settingButton instanceof ColorButton) {
                if(((ColorButton) settingButton).opened) {
                    settingButton.click(mouseX, mouseY, mouseButton);
                    return;
                }
            }
        }
        if(isModeButtonOpened)
            openModeButton.click(mouseX, mouseY, mouseButton);
        else
            for(int i = 0; i < buttons.size(); i++) {
                SettingButton settingButton = buttons.get(i);
                settingButton.click(mouseX, mouseY, mouseButton);
            }
    }

    public void release(int mouseX, int mouseY, int mouseButton) {
        for(SettingButton settingButton : buttons) {
            if(settingButton instanceof ColorButton) {
                if(((ColorButton) settingButton).opened) {
                    settingButton.release(mouseX, mouseY, mouseButton);
                    return;
                }
            }
        }
        if(isModeButtonOpened)
            openModeButton.release(mouseX, mouseY, mouseButton);
        else
            for(SettingButton settingButton : buttons)
                settingButton.release(mouseX, mouseY, mouseButton);
    }

    public void key(char character, int key) {
        for(SettingButton settingButton : buttons)
            settingButton.key(character, key);
    }
}
