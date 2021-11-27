package me.eliteun17y.unity.ui.clickgui.components.button.impl;

import me.eliteun17y.unity.ui.clickgui.ClickGUI;
import me.eliteun17y.unity.ui.clickgui.components.SettingPanel;
import me.eliteun17y.unity.ui.clickgui.components.button.Button;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;


public class ModuleButton implements Button {
    public float x;
    public float y;
    public float width;
    public float height;
    public float anim;
    public Module module;
    public boolean opened;
    public SettingPanel settingPanel;

    public ModuleButton(float x, float y, float width, float height, Module module) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.module = module;
        this.settingPanel = new SettingPanel(module);
    }

    public boolean isHovered(int x, int y) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float y2 = sr.getScaledHeight();
        float panelY = (y2 / 2) + 110;

        if(this.y < (int) (((y2 / 2) - 110) + (23 / 2.0f) + FontManager.instance.robotoMedium.getStringHeight(module.getCategory().name))) return false;
        if(panelY < (this.y + height / 2 - FontManager.instance.robotoLightSmall.getStringHeight(module.getName()) / 2) + FontManager.instance.robotoLightSmall.getStringHeight(module.getName()) && panelY < this.y + height / 2 - FontManager.instance.robotoLightSmall.getStringHeight(module.getName())/2) return false;

        float y1 = (new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() / 2.0f) - height / 2;
        if(y1 + 110 < this.y + height / 2 - FontManager.instance.robotoLightSmall.getStringHeight(module.getName())) return false;
        return x > this.x && x < this.x + 110 && y > this.y && y < this.y + height;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if(module.isToggled()) {
            if(anim < 0.5)
                anim += 0.03f;
            if(anim > 0.5 && anim != 0.5)
                anim = 0.5f;
        }else{
            if(anim >= 0.5 || anim > 0)
                anim -= 0.03f;
        }

        if(!ClickGUI.areSettingsOpened()) {
            GlStateManager.color(1-anim, 1, 1-anim);


            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            float y = sr.getScaledHeight();
            float panelY = (y / 2) + 110;
            if(this.y < (y / 2) - 110) return;
            RenderHelper.drawFilledRoundedRectangleWithDeadzones((int) (x + (90/2) - width/2), (int) this.y, (int) ((x + (90/2) - width/2) + width), (int) (this.y + height), 8, module.isToggled() ? UIUtil.getPreferredColor().getRGB() : UIUtil.getNormalColor().getRGB(), 0,  panelY);

            if(panelY < (this.y + height / 2 - FontManager.instance.robotoLightSmall.getStringHeight(module.getName()) / 2) + FontManager.instance.robotoLightSmall.getStringHeight(module.getName()) && panelY < this.y + height / 2 - FontManager.instance.robotoLightSmall.getStringHeight(module.getName())/2) return;
            // (width - amount of pixels to shorten it) / width
            float width = FontManager.instance.robotoLightSmall.getStringWidth(module.getName());
            float amountOffscreen = (width - 70) + 3;
            if(amountOffscreen >= 0) {
                GlStateManager.scale((70 - amountOffscreen) / 70, (70 - amountOffscreen) / 70, (70 - amountOffscreen) / 70);
            }

            if(amountOffscreen >= 0) {
                FontManager.instance.robotoLightSmall.drawStringWithDeadzone(module.getName(), (x + (90.0f / 2) - (FontManager.instance.robotoRegularSmall.getStringWidth(module.getName()) * ((70 - amountOffscreen) / 70)) / 2) / ((70 - amountOffscreen) / 70), (int) (this.y + height / 2 - (FontManager.instance.robotoLightSmall.getStringHeight(module.getName()) * ((70 - amountOffscreen) / 70)) / 2) / ((70 - amountOffscreen) / 70), UIUtil.getFontColor().getRGB(), 0, (y / 2) + 110);
            }else {
                FontManager.instance.robotoLightSmall.drawStringWithDeadzone(module.getName(), x + (90.0f / 2) - FontManager.instance.robotoLightSmall.getStringWidth(module.getName())/2, (int) this.y + height / 2 - FontManager.instance.robotoLightSmall.getStringHeight(module.getName())/2, UIUtil.getFontColor().getRGB(), 0, (y / 2) + 110);

            }
            if(amountOffscreen >= 0) {
                GlStateManager.scale(1 / ((70 - amountOffscreen) / 70), 1 / ((70 - amountOffscreen) / 70), 1 / ((70 - amountOffscreen) / 70));
            }
        }

        if(opened) {
            settingPanel.draw(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
        if(!ClickGUI.areSettingsOpened()) {
            if(mouseButton == 0)
                if(isHovered(mouseX, mouseY))
                    module.toggle();
            if(mouseButton == 1)
                if(isHovered(mouseX, mouseY)) {
                    opened = !opened;
                    ClickGUI.setSettingsOpened(!ClickGUI.areSettingsOpened());
                }
        }

        if(opened) {
            settingPanel.click(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void release(int mouseX, int mouseY, int mouseButton) {
        if(opened) {
            settingPanel.release(mouseX, mouseY, mouseButton);
        }
    }

    public void key(char character, int key) {
        if(opened) {
            settingPanel.key(character, key);
        }
    }
}
