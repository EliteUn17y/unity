package me.eliteun17y.unity.util.font;

import me.eliteun17y.unity.util.file.FileUtil;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CustomFont {
    public Font font;
    public boolean antiAliasing;
    public boolean fractionalMetrics;
    public Character[] characters;
    public BufferedImage fontImage;
    public DynamicTexture fontTexture;
    public int size;
    public int multiplier = 8;

    public CustomFont(Font font) {
        this.font = font;
        this.antiAliasing = true;
        this.fractionalMetrics = true;
        this.fontImage = generate();
        this.fontTexture = new DynamicTexture(fontImage);
    }

    public CustomFont(Font font, boolean antiAliasing, boolean fractionalMetrics) {
        this.font = font;
        this.antiAliasing = antiAliasing;
        this.fractionalMetrics = fractionalMetrics;
        this.fontImage = generate();
        this.fontTexture = new DynamicTexture(fontImage);
    }

    public BufferedImage generate() {
        characters = new Character[256];
        size = 512*multiplier;
        BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();

        graphics.setFont(font);
        graphics.setColor(new Color(255, 255, 255, 0));
        graphics.fillRect(0, 0, size, size);
        graphics.setColor(Color.WHITE);

        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAliasing ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAliasing ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        graphics.scale(multiplier, multiplier);
        FontMetrics fontMetrics = graphics.getFontMetrics();

        int x = 0;
        int y = 0;

        for(int i = 0; i < characters.length; i++) {
            char c = (char) i;
            Rectangle2D rect = fontMetrics.getStringBounds(String.valueOf(c), graphics);
            if(x + rect.getBounds().getWidth()+5 >= size / (float)multiplier) {
                y += rect.getBounds().height;
                x = 0;
            }

            graphics.drawString(String.valueOf(c), x, y + fontMetrics.getAscent());
            Character character = new Character(c, rect.getBounds().height, rect.getBounds().width, x, y);
            characters[i] = character;
            x += rect.getBounds().width + 5;
        }

        return bufferedImage;
    }

    public void drawCharacter(Character c, float x, float y) {
        float charX = c.x*(float)multiplier;
        float charY = c.y*(float)multiplier;
        float scaleX = c.width*(float)multiplier;
        float scaleY = c.height*(float)multiplier;
        GL11.glTexCoord2f(charX/size + scaleX/size, charY/size);
        GL11.glVertex2d(x + scaleX, y);
        GL11.glTexCoord2f(charX/size, charY/size);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2f(charX/size, charY/size + scaleY/size);
        GL11.glVertex2d(x, y+scaleY);
        GL11.glTexCoord2f(charX/size, charY/size + scaleY/size);
        GL11.glVertex2d(x, y+scaleY);
        GL11.glTexCoord2f(charX/size+scaleX/size, charY/size + scaleY/size);
        GL11.glVertex2d(x+scaleX, y+scaleY);
        GL11.glTexCoord2f(charX/size+scaleX/size, charY/size);
        GL11.glVertex2d(x+scaleX, y);
    }

    public void drawCharacterWithXDeadzone(Character c, float x, float y, float xLeftDeadzone, float xRightDeadzone) {
        float charX = c.x*(float)multiplier;
        float charY = c.y*(float)multiplier;
        float scaleX = c.width*(float)multiplier;
        float scaleY = c.height*(float)multiplier;

        float x1 = x / multiplier;

        if(x1 < xRightDeadzone && x1 + c.width > xRightDeadzone)
            scaleX -= ((x1 + c.width) - xRightDeadzone)*multiplier;
        if(x1 >= xRightDeadzone)
            return;

        float charX2 = charX;
        float x2 = x;

        if(x1 < xLeftDeadzone && x1 + c.width < xLeftDeadzone)
            return;
        else if(x1 < xLeftDeadzone) {
            x2 += (xLeftDeadzone - x1)*multiplier;
            charX2 += (xLeftDeadzone - x1)*multiplier;
        }

        GL11.glTexCoord2f(charX2/size + scaleX/size, charY/size);
        GL11.glVertex2d(x + scaleX, y);
        GL11.glTexCoord2f(charX2/size, charY/size);
        GL11.glVertex2d(x2, y);
        GL11.glTexCoord2f(charX2/size, charY/size + scaleY/size);
        GL11.glVertex2d(x2, y+scaleY);
        GL11.glTexCoord2f(charX2/size, charY/size + scaleY/size);
        GL11.glVertex2d(x2, y+scaleY);
        GL11.glTexCoord2f(charX2/size+scaleX/size, charY/size + scaleY/size);
        GL11.glVertex2d(x+scaleX, y+scaleY);
        GL11.glTexCoord2f(charX2/size+scaleX/size, charY/size);
        GL11.glVertex2d(x+scaleX, y);
    }

    public void drawCharacterWithDeadzone(Character c, float x, float y, float deadzoneTopY, float deadzoneBottomY) {
        float charX = c.x*(float)multiplier;
        float charY = c.y*(float)multiplier;
        float scaleX = c.width*(float)multiplier;
        float scaleY = c.height*(float)multiplier;

        float y1 = y / multiplier;

        if(y1 < deadzoneBottomY && y1 + c.height > deadzoneBottomY)
            scaleY -= ((y1 + c.height) - deadzoneBottomY)*multiplier;

        GL11.glTexCoord2f(charX/size + scaleX/size, charY/size);
        GL11.glVertex2d(x + scaleX, y);
        GL11.glTexCoord2f(charX/size, charY/size);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2f(charX/size, charY/size + scaleY/size);
        GL11.glVertex2d(x, y + scaleY);
        GL11.glTexCoord2f(charX/size, charY/size + scaleY/size);
        GL11.glVertex2d(x, y + scaleY);
        GL11.glTexCoord2f(charX/size+scaleX/size, charY/size + scaleY/size);
        GL11.glVertex2d(x + scaleX, y + scaleY);
        GL11.glTexCoord2f(charX/size+scaleX/size, charY/size);
        GL11.glVertex2d(x+scaleX, y);
    }

    public Character returnCharacter(char c) {
        for(Character character : characters) {
            if(character.character == c) {
                return character;
            }
        }
        return null;
    }

    public static class Character {
        public Character(char character, int height, int width, int x, int y) {
            this.character = character;
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
        }

        public char character;
        public int width;
        public int height;
        public int x;
        public int y;
    }
}
