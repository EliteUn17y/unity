package me.eliteun17y.unity.util.font;

import me.eliteun17y.unity.util.font.manager.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class CustomFontRenderer extends CustomFont {
    public CustomFontRenderer(Font font) {
        super(font);
    }

    public CustomFontRenderer(Font font, boolean antiAliasing, boolean fractionalMetrics) {
        super(font, antiAliasing, fractionalMetrics);
    }

    public void drawString(String str, float x, float y, int color) {
        if(FontManager.instance.mcFont) {
            Minecraft.getMinecraft().fontRenderer.drawString(str, (int) x, (int) y, color);
            return;
        }
        x *= multiplier;
        y *= multiplier;
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(fontTexture.getGlTextureId());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, fontTexture.getGlTextureId());

        GlStateManager.scale(0.125, 0.125, 0.125);
        float r = ((color >> 16) & 0xff) / 255.0f;
        float g = ((color >>  8) & 0xff) / 255.0f;
        float b = ((color      ) & 0xff) / 255.0f;
        float a = ((color >> 24) & 0xff) / 255.0f;
        GlStateManager.color(r, g, b, a);

        int i = 0;
        for(char c : str.toCharArray()) {
            Character character = returnCharacter(c);
            GL11.glBegin(4);
            drawCharacter(character, x+(i), y);
            GL11.glEnd();
            i+=character.width * multiplier;
        }

        GL11.glHint(3155, 4352);
        GL11.glPopMatrix();
    }

    public void drawStringWithXDeadzone(String str, float x, float y, int color, float xLeftDeadzone, float xRightDeadzone) {
        x *= multiplier;
        y *= multiplier;
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(fontTexture.getGlTextureId());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, fontTexture.getGlTextureId());

        GlStateManager.scale(0.125, 0.125, 0.125);
        float r = ((color >> 16) & 0xff) / 255.0f;
        float g = ((color >>  8) & 0xff) / 255.0f;
        float b = ((color      ) & 0xff) / 255.0f;
        float a = ((color >> 24) & 0xff) / 255.0f;
        GlStateManager.color(r, g, b, a);

        int i = 0;
        for(char c : str.toCharArray()) {
            Character character = returnCharacter(c);
            GL11.glBegin(4);
            drawCharacterWithXDeadzone(character, x+(i), y, xLeftDeadzone, xRightDeadzone);
            GL11.glEnd();
            i+=character.width * multiplier;
        }

        GL11.glHint(3155, 4352);
        GL11.glPopMatrix();
    }

    public void drawStringWithDeadzone(String str, float x, float y, int color, float deadzoneTopY, float deadzoneBottomY) {
        x *= multiplier;
        y *= multiplier;
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(fontTexture.getGlTextureId());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, fontTexture.getGlTextureId());

        GlStateManager.scale(0.125, 0.125, 0.125);
        float r = ((color >> 16) & 0xff) / 255.0f;
        float g = ((color >>  8) & 0xff) / 255.0f;
        float b = ((color) & 0xff) / 255.0f;
        float a = ((color >> 24) & 0xff) / 255.0f;
        GlStateManager.color(r, g, b, a);

        int i = 0;
        for(char c : str.toCharArray()) {
            Character character = returnCharacter(c);
            glBegin(4);
            drawCharacterWithDeadzone(character, x+(i), y, deadzoneTopY, deadzoneBottomY);
            GL11.glEnd();
            i+=character.width * multiplier;
        }

        GL11.glHint(3155, 4352);
        GL11.glPopMatrix();
    }

    public float getStringWidth(String str) {
        if(FontManager.instance.mcFont) {
            return Minecraft.getMinecraft().fontRenderer.getStringWidth(str);
        }
        float width = 0;
        for(char c : str.toCharArray()) {
            Character character = returnCharacter(c);
            width += character.width;
        }
        return width;
    }

    public float getStringHeight(String str) {
        if(FontManager.instance.mcFont) {
            return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
        }
        float height = 0;

        for(char c : str.toCharArray()) {
            Character character = returnCharacter(c);
            if(character.height > height)
                height = character.height;
        }

        return height;
    }
}
