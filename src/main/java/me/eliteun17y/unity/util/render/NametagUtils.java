package me.eliteun17y.unity.util.render;

import me.eliteun17y.unity.util.font.CustomFontRenderer;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class NametagUtils {
    public static void drawNametag(Entity entity, double partialTicks, boolean armor, boolean background, boolean health, boolean heldItem, boolean maxHealth, boolean ping, Color textColor, Color color, Color fillColor) {

        Minecraft mc = Minecraft.getMinecraft();

        float viewerYaw = mc.getRenderManager().playerViewY;
        float viewerPitch = mc.getRenderManager().playerViewX;
        boolean isThirdPersonFrontal = mc.getRenderManager().options.thirdPersonView == 2;

        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        double d1 = (entity.lastTickPosY + ((entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) + 1)) + ((entity.posY + ((entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) + 1)) - (entity.lastTickPosY + ((entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) + 1))) * partialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
        double x = d0 - mc.getRenderManager().viewerPosX;
        double y = d1 - mc.getRenderManager().viewerPosY;
        double z = d2 - mc.getRenderManager().viewerPosZ;
        CustomFontRenderer fontRendererIn = FontManager.instance.robotoRegular;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.025F, -0.025F, 0);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);

        GlStateManager.disableDepth();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        StringBuilder stringBuilder = new StringBuilder(entity.getDisplayName().getUnformattedText());
        if(entity instanceof EntityPlayer)
            if(ping)
                stringBuilder.append(" ").append(Objects.requireNonNull(mc.getConnection()).getPlayerInfo(entity.getUniqueID()).getResponseTime()).append("MS");
        if(entity instanceof EntityLivingBase) {
            if(health) {
                stringBuilder.append(" ").append(Math.round(((EntityLivingBase) entity).getHealth() + ((EntityLivingBase) entity).getAbsorptionAmount()));
                if(maxHealth)
                    stringBuilder.append(" / ").append(Math.round(((EntityLivingBase) entity).getMaxHealth() + ((EntityLivingBase) entity).getAbsorptionAmount()));
            }
        }
        String str = stringBuilder.toString();
        int i = (int) (fontRendererIn.getStringWidth(str) / 2);


        GlStateManager.enableTexture2D();


        if(background)
            RenderHelper.drawFilledRoundedRectangleWithSeparateFillColor((int) (-fontRendererIn.getStringWidth(str) / 2 - 3), -3, (int) ((-fontRendererIn.getStringWidth(str) / 2) + fontRendererIn.getStringWidth(str) + 3), (int) fontRendererIn.getStringHeight(str), 3, color.getRGB(), fillColor.getRGB());

        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, 0, textColor.getRGB());
        NonNullList<ItemStack> items = null;

        if(armor)
            if(entity instanceof EntityPlayer)
                items = ((EntityPlayer) entity).inventory.armorInventory;

        if(items != null) {
            ArrayList<ItemStack> itemList = new ArrayList<>(items);
            if(heldItem)
                itemList.add(((EntityPlayer) entity).getHeldItemMainhand());
            int xPos = (int) -(((itemList.size() - itemList.stream().filter(ItemStack::isEmpty).count()) * 16) / 2);
            for(ItemStack itemStack : itemList) {
                if(itemStack.getItem() == Items.AIR) continue;

                String s = itemStack.getCount() > 1 ? String.valueOf(itemStack.getCount()) : "";
                mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, xPos, (int) -fontRendererIn.getStringHeight(str) + 3);
                mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, xPos, (int) -fontRendererIn.getStringHeight(str) + 3, "");

                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);

                GlStateManager.disableDepth();

                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                FontManager.instance.robotoRegularSmall.drawString(s, (float)(xPos + 19 - 2 - FontManager.instance.robotoRegularSmall.getStringWidth(s)), (float)(-fontRendererIn.getStringHeight(str) + 3 + 6), textColor.getRGB());


                xPos += 16;
            }
        }

        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);

    }

    public static void drawItem(Entity entity, double partialTicks, boolean armor, boolean background, boolean health, boolean maxHealth, boolean ping, Color textColor, Color color, Color fillColor) {

        Minecraft mc = Minecraft.getMinecraft();

        float viewerYaw = mc.getRenderManager().playerViewY;
        float viewerPitch = mc.getRenderManager().playerViewX;
        boolean isThirdPersonFrontal = mc.getRenderManager().options.thirdPersonView == 2;

        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
        double x = d0 - mc.getRenderManager().viewerPosX;
        double y = d1 - mc.getRenderManager().viewerPosY;
        double z = d2 - mc.getRenderManager().viewerPosZ;
        CustomFontRenderer fontRendererIn = FontManager.instance.robotoRegular;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
        //GlStateManager.rotate(new Quaternion(0.707f, 0, 0, 0.707f));
        GlStateManager.scale(-0.025F, -0.025F, 0); // Z is 0 to prevent weirdness
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);

        GlStateManager.disableDepth();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);


        GlStateManager.enableTexture2D();

        mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.WOODEN_AXE), 0, 0);

        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);

    }
}
