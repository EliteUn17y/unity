package me.eliteun17y.unity.window.impl;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.managers.friend.Friend;
import me.eliteun17y.unity.util.config.ConfigUtil;
import me.eliteun17y.unity.util.file.FileUtil;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.CustomFontTextBox;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.util.uuid.UUIDUtil;
import me.eliteun17y.unity.window.Window;

import java.awt.*;
import java.io.File;
import java.util.Objects;

public class Configs extends Window {
    public CustomFontTextBox name;
    public boolean adding;

    public Configs() {
        super("Configs", "Allows you to save, load, update and remove configs.");
        width = 100;
        height = 100;

        adding = false;
        name = new CustomFontTextBox(FontManager.instance.robotoRegularSmall, x + width / 2 - 25, y + 50, 50, "Name", UIUtil.getOppositeFontColor(), UIUtil.getNormalColor());
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);

        name.x = x + width / 2 - 25;
        name.y = y + 50;

        FontManager.instance.robotoRegular.drawString("Configs", x, y, UIUtil.getFontColor().getRGB());

        float y = this.y + FontManager.instance.robotoRegular.getStringHeight("Configs") + 4;
        for(int i = 0; i < Objects.requireNonNull(FileUtil.configs.listFiles()).length; i++) {
            String name1 = FileUtil.configs.listFiles()[i].getName().replace(".json", "");
            FontManager.instance.robotoLightSmall.drawString(name1, x, y, UIUtil.getFontColor().getRGB());
            y += FontManager.instance.robotoLightSmall.getStringHeight(name1);
            if(y > this.y + height) {
                height += FontManager.instance.robotoLightSmall.getStringHeight(name1);
            }
        }

        RenderHelper.drawFilledRoundedRectangle(x + width - 30, this.y + height - 15, x + width, this.y + height - 2, 5, UIUtil.getPreferredColor().getRGB());
        FontManager.instance.robotoRegularSmall.drawString("Add", x + width - (30 / 2.0f) - FontManager.instance.robotoRegularSmall.getStringWidth("Add") / 2, this.y + height - (17 / 2.0f) - FontManager.instance.robotoRegularSmall.getStringHeight("Add") / 2.0f, UIUtil.getFontColor().getRGB());

        if(adding) {
            RenderHelper.drawFilledRoundedRectangle(x - 3, this.y, x + width + 3, this.y + height, 5, new Color(UIUtil.getOppositeNormalColor2().getRed(), UIUtil.getOppositeNormalColor2().getBlue(), UIUtil.getOppositeNormalColor2().getGreen(), 200).getRGB());
            FontManager.instance.robotoRegular.drawString("Add Config", x + width / 2 - FontManager.instance.robotoRegular.getStringWidth("Add Config") / 2, this.y + 10, UIUtil.getOppositeFontColor().getRGB());

            RenderHelper.drawFilledRoundedRectangle(x + width / 2 - 30, this.y + 70, x + width / 2 + 30, this.y + 90, 5, UIUtil.getPreferredColor().getRGB());
            FontManager.instance.robotoRegular.drawString("Add", x + width / 2 - FontManager.instance.robotoRegular.getStringWidth("Add") / 2, this.y + 80 - FontManager.instance.robotoRegular.getStringHeight("Add") / 2, UIUtil.getOppositeFontColor().getRGB());
            name.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
        super.click(mouseX, mouseY, mouseButton);
        File file = new File(FileUtil.configs.getPath() + "/" + name.getText() + ".json");
        if(mouseX >= x + width - 30 && mouseX <= x + width && mouseY >= y + height - 15 && mouseY <= this.y + height - 2) {
            adding = true;
        }

        if(mouseX >= x + width / 2 - 30 && mouseX <= x + width / 2 + 30 && mouseY >= this.y + 70 && mouseY <= this.y + 90 && adding) {
            if(!file.exists()) {
                ConfigUtil.save(file);
            }
            adding = false;
            name.setText("");
        }

        if(!adding) {
            float y = this.y + FontManager.instance.robotoRegular.getStringHeight("Friends") + 4;
            for(int i = 0; i < Objects.requireNonNull(FileUtil.configs.listFiles()).length; i++) {
                String name1 = FileUtil.configs.listFiles()[i].getName().replace(".json", "");

                FontManager.instance.robotoLightSmall.drawString(name1, x, y, UIUtil.getFontColor().getRGB());
                if(mouseButton == 0) {
                    if(mouseX >= x && mouseX <= x + FontManager.instance.robotoRegularSmall.getStringWidth(name1) && mouseY >= y && mouseY <= y + FontManager.instance.robotoRegularSmall.getStringHeight(name1)) {
                        ConfigUtil.load(FileUtil.configs.listFiles()[i]);
                    }
                }
                if(mouseButton == 2) {
                    if(mouseX >= x && mouseX <= x + FontManager.instance.robotoRegularSmall.getStringWidth(name1) && mouseY >= y && mouseY <= y + FontManager.instance.robotoRegularSmall.getStringHeight(name1)) {
                        ConfigUtil.update(FileUtil.configs.listFiles()[i]);
                    }
                }
                if(mouseButton == 1) {
                    if(mouseX >= x && mouseX <= x + FontManager.instance.robotoRegularSmall.getStringWidth(name1) && mouseY >= y && mouseY <= y + FontManager.instance.robotoRegularSmall.getStringHeight(name1)) {
                        ConfigUtil.remove(FileUtil.configs.listFiles()[i]);
                    }
                }
                y += FontManager.instance.robotoLightSmall.getStringHeight(name1);
                if(y > this.y + height) {
                    height += FontManager.instance.robotoLightSmall.getStringHeight(name1);
                }
            }
        }

        if(adding) {
            name.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void release(int mouseX, int mouseY, int mouseButton) {
        super.release(mouseX, mouseY, mouseButton);
        if(adding) {
            name.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void key(char character, int key) {
        super.key(character, key);
        if(adding) {
            name.keyTyped(character, key);
        }
    }
}
