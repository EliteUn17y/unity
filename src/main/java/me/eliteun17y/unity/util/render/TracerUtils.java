package me.eliteun17y.unity.util.render;

import me.eliteun17y.unity.util.font.CustomFontRenderer;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

public class TracerUtils {
    public static void drawTracerToEntity(Entity entity, float partialTicks, int lineWidth, Color tracerColor) {
        Minecraft mc = Minecraft.getMinecraft();

        float viewerYaw = mc.getRenderManager().playerViewY;
        float viewerPitch = mc.getRenderManager().playerViewX;
        boolean isThirdPersonFrontal = mc.getRenderManager().options.thirdPersonView == 2;

        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        double d1 = (entity.lastTickPosY + ((entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY)) / 2) + ((entity.posY + ((entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) / 2)) - (entity.lastTickPosY + ((entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) / 2))) * partialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
        double x = d0 - mc.getRenderManager().viewerPosX;
        double y = d1 - mc.getRenderManager().viewerPosY;
        double z = d2 - mc.getRenderManager().viewerPosZ;
        CustomFontRenderer fontRendererIn = FontManager.instance.robotoRegular;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);

        GlStateManager.disableDepth();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        RenderHelper.color(tracerColor.getRGB());

        GlStateManager.glLineWidth(lineWidth);

        GlStateManager.disableTexture2D();

        GL11.glBegin(GL11.GL_LINES);

        GL11.glVertex3d(0, 0, 0);
        Vec3d forward = new Vec3d(0, 0, 1).rotatePitch(-(float) Math.toRadians(mc.player.rotationPitch)).rotateYaw(-(float) Math.toRadians(mc.player.rotationYaw));
        GL11.glVertex3d(forward.x + (-x), forward.y + (-y) + mc.player.getEyeHeight(), forward.z + (-z));

        GL11.glEnd();

        GlStateManager.enableTexture2D();

        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
    }
}
