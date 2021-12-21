package me.eliteun17y.unity.util.ui;

import me.eliteun17y.unity.util.font.CustomFont;
import me.eliteun17y.unity.util.font.CustomFontRenderer;
import me.eliteun17y.unity.util.font.manager.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class CustomFontTextBox {
    public CustomFontRenderer fontRenderer;
    public float x;
    public float y;
    public float width;
    public Color color;
    public Color barColor;
    public String placeholderText;

    public String text;
    public boolean clicked;
    public int cursorPosition;

    public CustomFontTextBox(CustomFontRenderer fontRenderer, float x, float y, float width, String placeholderText, Color color, Color barColor) {
        this.fontRenderer = fontRenderer;
        this.x = x;
        this.y = y;
        this.width = width;
        this.text = "";
        this.placeholderText = placeholderText;
        this.color = color;
        this.barColor = barColor;
        cursorPosition = -1;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        text = text.replace("\0", "");

        if(cursorPosition > text.length())
            cursorPosition -= 1;
        float x1 = 0;
        float x2 = x;
        if(text.length() > 0 && cursorPosition <= text.length()) {
            for(char c : text.substring(0, cursorPosition).toCharArray()) {
                CustomFont.Character character = fontRenderer.returnCharacter(c);
                if(x1 + character.width > width) {
                    x2 -= character.width;
                }
                x1 += character.width;
            }
        }

        fontRenderer.drawStringWithXDeadzone(text.equals("") ? placeholderText : text, x2, y, color.getRGB(), x, x + width);
        Gui.drawRect((int) x, (int) (y + fontRenderer.getStringHeight(text.equals("") ? placeholderText : text)), (int) (x + width), (int) (y + fontRenderer.getStringHeight(text.equals("") ? placeholderText : text) + 1), barColor.getRGB());
        if(cursorPosition >= 0 && clicked)
            if(cursorPosition <= text.length())
                Gui.drawRect((int) (x2 + fontRenderer.getStringWidth(text.substring(0, cursorPosition))), (int) y, (int) (x2 + fontRenderer.getStringWidth(text.substring(0, cursorPosition))) + 1, (int) (y + fontRenderer.getStringHeight(text)), -1);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(isHovered(mouseX, mouseY)) {
            if(mouseButton == 0) {
                clicked = true;
            }
        }else{
            if(mouseButton == 0) {
                clicked = false;
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    public boolean isCtrlKeyDown()
    {
        if (Minecraft.IS_RUNNING_ON_MAC)
        {
            return Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220);
        }
        else
        {
            return Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
        }
    }

    public void keyTyped(char character, int key) {
        if(clicked) {
            if(isCtrlKeyDown() && key != 47) {
                return;
            }
            System.out.println(key);
            switch(key) {
                case 1:
                    break;
                case 15:
                    break;
                case 47:
                     if(isCtrlKeyDown()) {
                         Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
                         try {
                             setText(getText() + c.getData(DataFlavor.stringFlavor));
                             cursorPosition = (getText() + c.getData(DataFlavor.stringFlavor)).length();
                         } catch (UnsupportedFlavorException | IOException e) {
                             e.printStackTrace();
                         }
                         break;
                     }else {
                         if(text.length() == 0) {
                             text = addCharAt(text, character, 0);
                             cursorPosition += 1;
                         }else {
                             if(cursorPosition <= text.length())
                                 text = addCharAt(text, character, cursorPosition);
                         }
                         cursorPosition += 1;
                     }
                case 29:
                    break;
                case 157:
                    break;
                case 220:
                    break;
                case 184:
                    break;
                case 219:
                    break;
                case 56:
                    break;
                case 28:
                    clicked = false;
                    break;
                case 205:
                    if(cursorPosition + 1 <= text.length())
                        cursorPosition += 1;
                    break;
                case 203:
                    if(cursorPosition > 0)
                        cursorPosition -= 1;
                    break;
                case 14:
                    if(text.length() > 0) {
                        cursorPosition -= 1;
                        if(cursorPosition <= text.length() - 1)
                            text = deleteCharAt(text, cursorPosition);
                    }else if(cursorPosition == 0){
                        cursorPosition -= 1;
                    }
                    break;
                default:

                    if(text.length() == 0) {
                        text = addCharAt(text, character, 0);
                        cursorPosition += 1;
                    }else {
                        if(cursorPosition <= text.length())
                            text = addCharAt(text, character, cursorPosition);
                    }
                    cursorPosition += 1;
                    break;
            }
        }
    }

    private static String deleteCharAt(String strValue, int index) {
        return strValue.substring(0, index) + strValue.substring(index + 1);
    }

    private static String addCharAt(String strValue, char c, int index) {
        return strValue.substring(0, index) + c + strValue.substring(index);
    }

    public boolean isHovered(int x, int y) {
        return x > this.x && x < this.x + width && y > this.y && y < this.y + fontRenderer.getStringHeight(text.equals("") ? placeholderText : text) + 1;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        cursorPosition = text.length();
    }
}
