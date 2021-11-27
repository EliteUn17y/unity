package me.eliteun17y.unity.ui.clickgui.components.button;

public interface Button {
    void draw(int mouseX, int mouseY, float partialTicks);

    void click(int mouseX, int mouseY, int mouseButton);

    void release(int mouseX, int mouseY, int mouseButton);

    void key(char character, int key);
}
