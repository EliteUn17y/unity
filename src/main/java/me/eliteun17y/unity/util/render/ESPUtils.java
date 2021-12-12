package me.eliteun17y.unity.util.render;

import me.eliteun17y.unity.util.ui.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class ESPUtils {

    public static void drawBox(BoxMode mode, BlockPos blockPos, Color color, Color fillColor) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO, GL11.GL_ONE);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(1);

        AxisAlignedBB aabb = new AxisAlignedBB(blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX, blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY, blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ, blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX + 1, blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY + 1, blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ + 1);

        GlStateManager.color(1, 1, 1, 1);

        switch(mode) {
            case FILL:
                RenderGlobal.renderFilledBox(aabb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
                break;
            case OUTLINE:
                RenderGlobal.drawSelectionBoundingBox(aabb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
                break;
            case BOTH:
                RenderGlobal.renderFilledBox(aabb, fillColor.getRed() / 255.0f, fillColor.getGreen() / 255.0f, fillColor.getBlue() / 255.0f, fillColor.getAlpha() / 255.0f);
                RenderGlobal.drawSelectionBoundingBox(aabb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
                break;
        }

        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBox(BoxMode mode, AxisAlignedBB aabb, Color color, Color fillColor) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO, GL11.GL_ONE);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(1);

        aabb = new AxisAlignedBB(aabb.minX - Minecraft.getMinecraft().getRenderManager().viewerPosX, aabb.minY - Minecraft.getMinecraft().getRenderManager().viewerPosY, aabb.minZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ, aabb.maxX - Minecraft.getMinecraft().getRenderManager().viewerPosX, aabb.maxY - Minecraft.getMinecraft().getRenderManager().viewerPosY, aabb.maxZ - Minecraft.getMinecraft().getRenderManager().viewerPosZ);

        GlStateManager.color(1, 1, 1, 1);

        switch(mode) {
            case FILL:
                RenderGlobal.renderFilledBox(aabb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
                break;
            case OUTLINE:
                RenderGlobal.drawSelectionBoundingBox(aabb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
                break;
            case BOTH:
                RenderGlobal.renderFilledBox(aabb, fillColor.getRed() / 255.0f, fillColor.getGreen() / 255.0f, fillColor.getBlue() / 255.0f, fillColor.getAlpha() / 255.0f);
                RenderGlobal.drawSelectionBoundingBox(aabb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
                break;
        }

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawEntity(EntityMode entityMode, Entity entity, Color color, Color hiddenColor, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        switch(entityMode) {
            case VISIBILITY:
                glPushAttrib( GL_ALL_ATTRIB_BITS );
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                double a0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
                double a1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
                double a2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
                double x1 = a0 - mc.getRenderManager().viewerPosX;
                double y1 = a1 - mc.getRenderManager().viewerPosY;
                double z1 = a2 - mc.getRenderManager().viewerPosZ;

                float yaw1 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;

                GlStateManager.disableTexture2D();

                GlStateManager.disableDepth();

                color(hiddenColor.getRGB());

                mc.getRenderManager().getEntityRenderObject(entity).doRender(entity, x1, y1, z1, yaw1, partialTicks);

                GlStateManager.enableDepth();


                color(0);

                color(color.getRGB());

                mc.getRenderManager().getEntityRenderObject(entity).doRender(entity, x1, y1, z1, yaw1, partialTicks);

                GlStateManager.enableTexture2D();
                glPopAttrib();
                break;
            case OUTLINE:
                // Push the GL attribute bits so that we don't wreck any settings
                glPushAttrib( GL_ALL_ATTRIB_BITS );
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                // Enable polygon offsets, and offset filled polygons forward by 2.5
                glEnable( GL_POLYGON_OFFSET_FILL );
                glPolygonOffset( -2.5f, -2.5f );
                // Set the render mode to be line rendering with a thick line width
                glPolygonMode( GL_FRONT_AND_BACK, GL_LINE  );
                glLineWidth( 1.0f );
                // Set the colour to be white
                color(color.getRGB());
                // Render the object
                double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
                double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
                double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
                double x = d0 - mc.getRenderManager().viewerPosX;
                double y = d1 - mc.getRenderManager().viewerPosY;
                double z = d2 - mc.getRenderManager().viewerPosZ;

                float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;

                glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );
                mc.getRenderManager().getEntityRenderObject(entity).doRender(entity, x, y, z, yaw, partialTicks);
                // Set the polygon mode to be filled triangles
                glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
                glEnable( GL_LIGHTING );
                // Set the colour to the background
                glColor4f( 0.0f, 0.0f, 0.0f, 1.0f );
                // Render the object
                //mc.getRenderManager().getEntityRenderObject(entity).doRender(entity, x, y, z, yaw, partialTicks);
                // Pop the state changes off the attribute stack
                // to set things back how they were
                glPopAttrib();
                GlStateManager.enableTexture2D();
                GlStateManager.enableDepth();
                break;
        }
    }

    public static void color(int argb) {
        float a = (float)(argb >> 24 & 255) / 255.0F;
        float r = (float)(argb >> 16 & 255) / 255.0F;
        float g = (float)(argb >> 8 & 255) / 255.0F;
        float b = (float)(argb & 255) / 255.0F;
        glColor4f(r, g, b, a);
    }

    public enum BoxMode {
        FILL,
        OUTLINE,
        BOTH
    }

    public enum EntityMode {
        VISIBILITY,
        OUTLINE,
    }
}
