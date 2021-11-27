package me.eliteun17y.unity.util.ui;

import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class RenderHelper {
    public static void drawCircle(int x, int y, int r, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        color(color);
        GlStateManager.enableBlend();
        glBegin(GL_POINTS);
        for(double k=0;k<=360;k+=0.1){
            glVertex2f((float)(x+r*Math.cos(Math.toRadians(k))),(float)(y-r*Math.sin(Math.toRadians(k))));
        }
        glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawFilledCircle(float x, float y, int r, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        color(color);
        GlStateManager.enableBlend();
        glBegin(GL_TRIANGLE_FAN);
        for(double k=0;k<=360;k+=0.1){
            glVertex2f((float)(x+r*Math.cos(Math.toRadians(k))),(float)(y-r*Math.sin(Math.toRadians(k))));
        }
        glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawColorCircle(int x, int y, int r, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        glLineWidth(3.5f);
        glEnable(GL_LINE_SMOOTH);
        glShadeModel(GL_SMOOTH);

        glBegin(GL_LINE_STRIP);
        for(int i = 0; i < 360; i++) {
            color(-1);
            glVertex2d(x, y);
            color(Color.HSBtoRGB(i / 360f, 1, 1));
            glVertex2d(
                    x + Math.sin(Math.toRadians(i)) * r,
                    y + Math.cos(Math.toRadians(i)) * r
            );
        }
        glEnd();
        glShadeModel(GL_FLAT);
        glDisable(GL_LINE_SMOOTH);


        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawTriangle(int x, int y, int radius, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        color(color);

        glBegin(GL_TRIANGLE_FAN);
        glVertex2f(x + radius / 2, y + radius);
        glVertex2f(x+radius, y);
        glVertex2f(x, y);
        glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRoundedRectangle(int x, int y, int endX, int endY, int radius, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        x += radius;
        y += radius;
        endX -= radius;
        endY -= radius;
        drawRoundedRectanglePart(x, y, radius, 1,color);
        drawRoundedRectanglePart(x, endY, radius, 2,color);
        drawRoundedRectanglePart(endX, endY, radius, 3,color);
        drawRoundedRectanglePart(endX, y, radius, 4,color);
        drawLineY(x - radius, y, endY, color);
        drawLineY(endX + radius, y, endY, color);
        drawLineX(y - radius, x, endX, color);
        drawLineX(endY + radius, x, endX, color);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawFilledRoundedRectangle(float x, float y, float endX, float endY, int radius, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        x += radius;
        y += radius;
        endX -= radius;
        endY -= radius;
        glBegin(GL_TRIANGLE_FAN);
        drawFilledRoundedRectanglePart(x, y, radius, 1, color);
        drawFilledRoundedRectanglePart(x, endY, radius, 2, color);
        drawFilledRoundedRectanglePart(endX, endY, radius, 3, color);
        drawFilledRoundedRectanglePart(endX, y, radius, 4, color);
        glEnd();
        drawLineY(x - radius, y, endY, color);
        drawLineY(endX + radius, y, endY, color);
        drawLineX(y - radius, x, endX, color);
        drawLineX(endY + radius, x, endX, color);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawFilledRoundedRectangleWithDeadzones(float x, float y, float endX, float endY, int radius, int color, float topY, float bottomY) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        x += radius;
        y += radius;
        endX -= radius;
        endY -= radius;
        glBegin(GL_TRIANGLE_FAN);
        drawFilledRoundedRectanglePartWithDeadzones(x, y, radius, 1, color, topY, bottomY);
        drawFilledRoundedRectanglePartWithDeadzones(x, endY, radius, 2, color, topY, bottomY);
        drawFilledRoundedRectanglePartWithDeadzones(endX, endY, radius, 3, color, topY, bottomY);
        drawFilledRoundedRectanglePartWithDeadzones(endX, y, radius, 4, color, topY, bottomY);
        glEnd();
        drawLineYWithDeadzones(x - radius, y, endY, color, topY, bottomY);
        drawLineYWithDeadzones(endX + radius, y, endY, color, topY, bottomY);
        drawLineXWithDeadzones(y - radius, x, endX, color, topY, bottomY);
        drawLineXWithDeadzones(endY + radius, x, endX, color, topY, bottomY);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawFilledRoundedRectangleWithSeparateFillColor(int x, int y, int endX, int endY, int radius, int color, int fillColor) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        x += radius;
        y += radius;
        endX -= radius;
        endY -= radius;
        glBegin(GL_TRIANGLE_FAN);
        drawFilledRoundedRectanglePart(x, y, radius, 1, fillColor);
        drawFilledRoundedRectanglePart(x, endY, radius, 2, fillColor);
        drawFilledRoundedRectanglePart(endX, endY, radius, 3, fillColor);
        drawFilledRoundedRectanglePart(endX, y, radius, 4, fillColor);
        glEnd();

        drawRoundedRectanglePart(x, y, radius, 1, color);
        drawRoundedRectanglePart(x, endY, radius, 2, color);
        drawRoundedRectanglePart(endX, endY, radius, 3, color);
        drawRoundedRectanglePart(endX, y, radius, 4, color);

        drawLineY(x - radius, y, endY, color);
        drawLineY(endX + radius, y, endY, color);
        drawLineX(y - radius, x, endX, color);
        drawLineX(endY + radius, x, endX, color);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();

        //drawRoundedRectangle(originalX, originalY, originalEndX, originalEndY, radius, color);
    }

    public static void drawRoundedRectanglePart(int x, int y, int r, int part, int color) {
        color(color);
        GlStateManager.enableBlend();
        glBegin(GL_POINTS);
        for(double k=(90 * part);k<=(90 * (part + 1));k+=0.1){
            glVertex2f((float)(x+r*Math.cos(Math.toRadians(k))),(float)(y-r*Math.sin(Math.toRadians(k))));
        }
        glEnd();
    }

    public static void drawFilledRoundedRectanglePart(float x, float y, float r, int part, int color) {
        color(color);
        for(double k=(90 * part);k<=(90 * (part + 1));k+=0.1){
            glVertex2f((float)(x+r*Math.cos(Math.toRadians(k))),(float)(y-r*Math.sin(Math.toRadians(k))));
        }
    }

    public static void drawFilledRoundedRectanglePartWithDeadzones(float x, float y, float r, int part, int color, float topY, float bottomY) {
        color(color);
        for(double k=(90 * part);k<=(90 * (part + 1));k+=0.1){
            double v = r * Math.sin(Math.toRadians(k));
            if(y - v > bottomY || y - v < topY) continue;
            glVertex2f((float)(x+r*Math.cos(Math.toRadians(k))),(float)(y - v));
        }
    }

    public static void drawBox(int startX, int startY, int endX, int endY, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        color(color);
        GlStateManager.enableBlend();
        glBegin(GL_TRIANGLE_FAN);
        glVertex2f((float) startX, (float) endY);
        glVertex2f((float) endX, (float) endY);
        glVertex2f((float) endX, (float) startY);
        glVertex2f((float) startX, (float) startY);
        glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawLineX(float y, float startX, float endX, int color) {
        color(color);
        GlStateManager.enableBlend();
        glBegin(GL_POINTS);
        for(double x = startX; x < endX; x += 0.1) {
            glVertex2f((float) x, (float) y);
        }
        glEnd();
    }

    public static void drawLineXWithDeadzones(float y, float startX, float endX, int color, float topY, float bottomY) {
        color(color);
        GlStateManager.enableBlend();
        glBegin(GL_POINTS);
        for(double x = startX; x < endX; x += 0.1) {
            if(y > bottomY || y < topY) continue;
            glVertex2f((float) x, (float) y);
        }
        glEnd();
    }

    public static void drawLineY(float x, float startY, float endY, int color) {
        color(color);
        GlStateManager.enableBlend();
        glBegin(GL_POINTS);
        for(double y = startY; y < endY; y += 0.1) {
            glVertex2f((float) x, (float) y);
        }
        glEnd();
    }

    public static void drawLineYWithDeadzones(float x, float startY, float endY, int color, float topY, float bottomY) {
        color(color);
        GlStateManager.enableBlend();
        glBegin(GL_POINTS);
        for(double y = startY; y < endY; y += 0.1) {
            if(y > bottomY || y < topY) continue;
            glVertex2f((float) x, (float) y);
        }
        glEnd();
    }

    public static void drawBoxWithFade(int startX, int startY, int endX, int endY, int color, int color2) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        color(color2);
        glShadeModel(GL_SMOOTH);
        GlStateManager.enableBlend();
        glBegin(GL_TRIANGLE_FAN);
        glVertex2f((float) startX, (float) endY);
        glVertex2f((float) endX, (float) endY);
        color(color);
        glVertex2f((float) endX, (float) startY);
        glVertex2f((float) startX, (float) startY);
        glEnd();
        glShadeModel(GL_FLAT);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawMinimise(float x, float y, float endX, int color) {
        color(color);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        glBegin(GL_LINES);
        glVertex2d(x, y);
        glVertex2d(endX, y);
        glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawMaximise(float x, float y, float endX, float endY, int color) {
        color(color);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        glBegin(GL_LINES);
        glVertex2d(x, y);
        glVertex2d(endX, y);
        glVertex2d(endX, y);
        glVertex2d(endX, endY);
        glVertex2d(endX, endY);
        glVertex2d(x, endY);
        glVertex2d(x, endY);
        glVertex2d(x, y);
        glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void color(int argb) {
        float a = (float)(argb >> 24 & 255) / 255.0F;
        float r = (float)(argb >> 16 & 255) / 255.0F;
        float g = (float)(argb >> 8 & 255) / 255.0F;
        float b = (float)(argb & 255) / 255.0F;
        GlStateManager.color(r, g, b, a);
    }
}
