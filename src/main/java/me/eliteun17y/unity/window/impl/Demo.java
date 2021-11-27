package me.eliteun17y.unity.window.impl;

import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.window.Window;

public class Demo extends Window {
    public Demo() {
        super("Demo", "A hello world window.");
        width = FontManager.instance.robotoRegular.getStringWidth("Hello, world!!");
        height = FontManager.instance.robotoRegular.getStringHeight("Hello, world!!");
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        FontManager.instance.robotoRegular.drawString("Hello, world!!", x, y, UIUtil.getFontColor().getRGB());
    }
}
