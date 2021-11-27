package me.eliteun17y.unity.ui.clickgui.components;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.ui.clickgui.ClickGUI;
import me.eliteun17y.unity.ui.clickgui.components.button.impl.ModuleButton;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.widgets.impl.ModuleList;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;

public class Panel {
    public float x;
    public float y;
    public float width;
    public float height;
    public boolean hovered;
    public Category category;
    public ArrayList<ModuleButton> buttons = new ArrayList<>();

    public boolean moving;

    public Panel(float x, float y, float width, float height, Category category) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.category = category;

        float y2 = y + 35;
        float width2 = 70;
        float height2 = 20;

        for(Module module : Unity.instance.moduleManager.getModulesByCategory(category)) {
            buttons.add(new ModuleButton(x, y2, width2, height2, module));
            y2 += 23;
        }
        hovered = false;
        moving = false;
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        if(!ClickGUI.areSettingsOpened()) {
            hovered = isHovered(mouseX, mouseY);
            GlStateManager.color(1, 1, 1, 1);
            // Scroll wheel
            //RenderHelper.drawFilledRoundedRectangle((int) x, (int) y, (int) (x + width), (int) (y + (23 / 2.0f) + FontManager.instance.robotoMedium.getStringHeight(category.name)), 10, UIUtil.getNormalColor().getRGB());

            //Gui.drawScaledCustomSizeModalRect((int) x, (int) y, 0, 0, (int) width,  (int) height, (int) width, (int) height, width, height);
            //Gui.drawRect((int) x, (int) y, (int) x + (int) width, (int) y + (int) height, -1);


        }

        if(!ClickGUI.areSettingsOpened())
            RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor((int) x, (int) y, (int) (x + width), (int) (y + height), 10, UIUtil.getNormalColor().getRGB(), new Color(UIUtil.getNormalColor().getRed(), UIUtil.getNormalColor().getGreen(), UIUtil.getNormalColor().getBlue(), 50).getRGB());

        for(ModuleButton moduleButton : buttons) {
            moduleButton.draw(mouseX, mouseY, partialTicks);
        }

        if(!ClickGUI.areSettingsOpened()) {
            RenderHelper.drawFilledRoundedRectangle((int) x, (int) y, (int) (x + width), (int) (y + (23 / 2.0f) + FontManager.instance.robotoMedium.getStringHeight(category.name)), 10, UIUtil.getNormalColor().getRGB());
            FontManager.instance.robotoMedium.drawString(category.name, x + (width / 2) - FontManager.instance.robotoMedium.getStringWidth(category.name) / 2, y + (23 / 2.0f) - FontManager.instance.robotoMedium.getStringHeight(category.name) / 2 + 2, UIUtil.getFontColor().getRGB());
        }
    }

    public void click(int mouseX, int mouseY, int mouseButton) {
        for(ModuleButton moduleButton : buttons)
            moduleButton.click(mouseX, mouseY, mouseButton);
    }

    public void release(int mouseX, int mouseY, int mouseButton) {
        for(ModuleButton moduleButton : buttons)
            moduleButton.release(mouseX, mouseY, mouseButton);
    }

    public void key(char character, int key) {
        for(ModuleButton moduleButton : buttons)
            moduleButton.key(character, key);
    }

    public void mouse(int i) {
        if(!hovered) return;

        boolean shouldMove = false;

        if(i > 0) {
            for(ModuleButton moduleButton : buttons) {
                if(moduleButton.y < y + (23 / 2.0f) + FontManager.instance.robotoMedium.getStringHeight(category.name)) {
                    i = 1;
                    shouldMove = true;
                }
            }
        }
        if(i <  0) {
            for(ModuleButton moduleButton : buttons) {
                if(moduleButton.y + moduleButton.height > y + height) {
                    i = -1;
                    shouldMove = true;
                }
            }
        }

        for(ModuleButton moduleButton : buttons) {
            if(shouldMove)
                moduleButton.y += i * moduleButton.height;
        }
    }

    public boolean isHovered(int x, int y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }
}
