package me.eliteun17y.unity.window.impl;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.managers.friend.Friend;
import me.eliteun17y.unity.managers.friend.FriendManager;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.CustomFontTextBox;
import me.eliteun17y.unity.util.ui.RenderHelper;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.util.uuid.UUIDUtil;
import me.eliteun17y.unity.window.Window;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class Friends extends Window {
    public boolean adding;
    public CustomFontTextBox username;

    public Friends() {
        super("Friends", "Allows you to add and remove friends.");
        width = 100;
        height = 100;

        adding = false;
        username = new CustomFontTextBox(FontManager.instance.robotoRegularSmall, x + width / 2 - 25, y + 50, 50, "Username", UIUtil.getOppositeFontColor(), UIUtil.getNormalColor());
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);

        username.x = x + width / 2 - 25;
        username.y = y + 50;

        FontManager.instance.robotoRegular.drawString("Friends", x, y, UIUtil.getFontColor().getRGB());

        float y = this.y + FontManager.instance.robotoRegular.getStringHeight("Friends") + 4;
        for(Friend friend : Unity.instance.friendManager.getRegistry()) {
            FontManager.instance.robotoLightSmall.drawString(friend.name, x, y, UIUtil.getFontColor().getRGB());
            y += FontManager.instance.robotoLightSmall.getStringHeight(friend.name);
            if(y > this.y + height) {
                height += FontManager.instance.robotoLightSmall.getStringHeight(friend.name);
            }
        }

        RenderHelper.drawFilledRoundedRectangle(x + width - 30, this.y + height - 15, x + width, this.y + height - 2, 5, UIUtil.getPreferredColor().getRGB());
        FontManager.instance.robotoRegularSmall.drawString("Add", x + width - (30 / 2.0f) - FontManager.instance.robotoRegularSmall.getStringWidth("Add") / 2, this.y + height - (17 / 2.0f) - FontManager.instance.robotoRegularSmall.getStringHeight("Add") / 2.0f, UIUtil.getFontColor().getRGB());

        if(adding) {
            RenderHelper.drawFilledRoundedRectangle(x - 3, this.y, x + width + 3, this.y + height, 5, new Color(UIUtil.getOppositeNormalColor2().getRed(), UIUtil.getOppositeNormalColor2().getBlue(), UIUtil.getOppositeNormalColor2().getGreen(), 200).getRGB());
            FontManager.instance.robotoRegular.drawString("Add Friend", x + width / 2 - FontManager.instance.robotoRegular.getStringWidth("Add Friend") / 2, this.y + 10, UIUtil.getOppositeFontColor().getRGB());

            RenderHelper.drawFilledRoundedRectangle(x + width / 2 - 30, this.y + 70, x + width / 2 + 30, this.y + 90, 5, UIUtil.getPreferredColor().getRGB());
            FontManager.instance.robotoRegular.drawString("Add", x + width / 2 - FontManager.instance.robotoRegular.getStringWidth("Add") / 2, this.y + 80 - FontManager.instance.robotoRegular.getStringHeight("Add") / 2, UIUtil.getOppositeFontColor().getRGB());
            username.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
        super.click(mouseX, mouseY, mouseButton);
        if(mouseX >= x + width - 30 && mouseX <= x + width && mouseY >= y + height - 15 && mouseY <= this.y + height - 2) {
            adding = true;
        }

        if(mouseX >= x + width / 2 - 30 && mouseX <= x + width / 2 + 30 && mouseY >= this.y + 70 && mouseY <= this.y + 90 && adding) {
            if(Unity.instance.friendManager.getRegistry().stream().noneMatch(friend -> friend.name.equals(username.getText()))) {
                Unity.instance.friendManager.add(new Friend(username.getText(), UUIDUtil.getUUID(username.getText())));
            }
            adding = false;
            username.setText("");
        }

        if(!adding) {
            float y = this.y + FontManager.instance.robotoRegular.getStringHeight("Friends") + 4;
            for(int i = 0; i < Unity.instance.friendManager.getRegistry().size(); i++) {
                Friend friend = Unity.instance.friendManager.get(i);

                FontManager.instance.robotoLightSmall.drawString(friend.name, x, y, UIUtil.getFontColor().getRGB());
                if(mouseButton == 0) {
                    if(mouseX >= x && mouseX <= x + FontManager.instance.robotoRegularSmall.getStringWidth(friend.name) && mouseY >= y && mouseY <= y + FontManager.instance.robotoRegularSmall.getStringHeight(friend.name)) {
                        Unity.instance.friendManager.getRegistry().removeIf(friend1 -> friend1.equals(friend));
                    }
                }
                y += FontManager.instance.robotoLightSmall.getStringHeight(friend.name);
                if(y > this.y + height) {
                    height += FontManager.instance.robotoLightSmall.getStringHeight(friend.name);
                }
            }
        }

        if(adding) {
            username.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void release(int mouseX, int mouseY, int mouseButton) {
        super.release(mouseX, mouseY, mouseButton);
        if(adding) {
            username.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void key(char character, int key) {
        super.key(character, key);
        if(adding) {
            username.keyTyped(character, key);
        }
    }
}
