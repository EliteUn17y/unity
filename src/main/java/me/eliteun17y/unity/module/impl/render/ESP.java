package me.eliteun17y.unity.module.impl.render;

import com.google.gson.JsonSyntaxException;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventEntityRender;
import me.eliteun17y.unity.event.impl.EventRenderSheepWool;
import me.eliteun17y.unity.event.impl.EventRenderWorld;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.chat.ChatUtil;
import me.eliteun17y.unity.util.render.ESPUtils;
import me.eliteun17y.unity.util.render.NametagUtils;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.setting.impl.ColorValue;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import me.eliteun17y.unity.util.world.EntityUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.client.util.JsonException;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Locale;

import static org.lwjgl.opengl.GL11.*;

public class ESP extends Module {
    public ModeValue blockMode = new ModeValue(this, "Block Mode", "Both", "Both", "Fill", "Outline");
    public ModeValue entityMode = new ModeValue(this, "Entity Mode", "Outline", "Outline", "Visibility");

    public BooleanValue chests = new BooleanValue(this, "Chests", true);
    public ColorValue chestColor = new ColorValue("Chest Color", new Color(200, 90, 90));
    public ColorValue chestFillColor = new ColorValue("Chest Fill Color", new Color(200, 90, 90, 100));

    public BooleanValue enderChests = new BooleanValue(this, "Ender Chests", true);
    public ColorValue enderChestColor = new ColorValue("Ender Chest Color", new Color(50, 190, 245));
    public ColorValue enderChestFillColor = new ColorValue("Ender Chest Fill Color", new Color(50, 190, 245, 100));

    public BooleanValue shulkers = new BooleanValue(this, "Shulkers", true);
    public ColorValue shulkerColor = new ColorValue("Shulkers Color", new Color(255, 140, 30));
    public ColorValue shulkerFillColor = new ColorValue("Shulkers Fill Color", new Color(255, 140, 30, 100));

    public BooleanValue players = new BooleanValue(this, "Players", true);
    public ColorValue playerColor = new ColorValue("Player Color", new Color(131, 255, 90));
    public ColorValue playerHiddenColor = new ColorValue("Player Hidden Color", new Color(255, 90, 90));

    public BooleanValue nonHostile = new BooleanValue(this, "Non Hostile", true);
    public ColorValue nonHostileColor = new ColorValue("Non Hostile Color", new Color(90, 255, 240));
    public ColorValue nonHostileHiddenColor = new ColorValue("Non Hostile Hidden Color", new Color(255, 90, 216));

    public BooleanValue hostile = new BooleanValue(this, "Hostile", true);
    public ColorValue hostileColor = new ColorValue("Hostile Color", new Color(164, 90, 255));
    public ColorValue hostileHiddenColor = new ColorValue("Hostile Hidden Color", new Color(255, 222, 90));

    public BooleanValue other = new BooleanValue(this, "Other", false);
    public ColorValue otherColor = new ColorValue("Other Color", new Color(255, 255, 230));
    public ColorValue otherHiddenColor = new ColorValue("Other Hidden Color", new Color(124, 188, 231));

    public ESP() {
        super("ESP", "Extrasensory perception; allows you to see things through walls.", Category.RENDER, Keyboard.KEY_NONE, 1);
        addValueOnValueChange(chests, chestColor, true);
        addValueOnValueChangeAndValueChange(blockMode, chests, chestFillColor, "Both", true);

        addValueOnValueChange(enderChests, enderChestColor, true);
        addValueOnValueChangeAndValueChange(blockMode, enderChests, enderChestFillColor, "Both", true);

        addValueOnValueChange(shulkers, shulkerColor, true);
        addValueOnValueChangeAndValueChange(blockMode, shulkers, shulkerFillColor, "Both", true);

        addValueOnValueChange(players, playerColor, true);
        addValueOnValueChange(nonHostile, nonHostileColor, true);
        addValueOnValueChange(hostile, hostileColor, true);
        addValueOnValueChange(other, otherColor, true);

        addValueOnValueChangeAndValueChange(players, entityMode, playerHiddenColor, true, "Visibility");
        addValueOnValueChangeAndValueChange(nonHostile, entityMode, nonHostileHiddenColor, true, "Visibility");
        addValueOnValueChangeAndValueChange(hostile, entityMode, hostileHiddenColor, true, "Visibility");
        addValueOnValueChangeAndValueChange(other, entityMode, otherHiddenColor, true, "Visibility");
    }

    @Subscribe
    public void onRenderWorld(EventRenderWorld event) {
        GL11.glColor3d(1, 1, 1);
        for(TileEntity tileEntity : mc.world.loadedTileEntityList) {
            if(chests.getObject()) {
                if(tileEntity instanceof TileEntityChest) {
                    ESPUtils.drawBox(ESPUtils.BoxMode.valueOf(blockMode.getMode().toUpperCase(Locale.ROOT)), tileEntity.getPos(), chestColor.getObject(), chestFillColor.getObject());
                }
            }

            if(enderChests.getObject()) {
                if(tileEntity instanceof TileEntityEnderChest) {
                    ESPUtils.drawBox(ESPUtils.BoxMode.valueOf(blockMode.getMode().toUpperCase(Locale.ROOT)), tileEntity.getPos(), enderChestColor.getObject(), enderChestFillColor.getObject());
                }
            }

            if(shulkers.getObject()) {
                if(tileEntity instanceof TileEntityShulkerBox) {
                    ESPUtils.drawBox(ESPUtils.BoxMode.valueOf(blockMode.getMode().toUpperCase(Locale.ROOT)), tileEntity.getPos(), shulkerColor.getObject(), shulkerFillColor.getObject());
                }
            }
        }
        for(Entity entity : mc.world.loadedEntityList) {
            if(entity == mc.player) continue;

            if(players.getObject() && EntityUtil.isEntityPlayer(entity))
                ESPUtils.drawEntity(ESPUtils.EntityMode.valueOf(entityMode.getMode().toUpperCase(Locale.ROOT)), entity, playerColor.getObject(), playerHiddenColor.getObject(), event.getPartialTicks());
            else if(nonHostile.getObject() && EntityUtil.isEntityNonHostile(entity))
                ESPUtils.drawEntity(ESPUtils.EntityMode.valueOf(entityMode.getMode().toUpperCase(Locale.ROOT)), entity, nonHostileColor.getObject(), nonHostileHiddenColor.getObject(), event.getPartialTicks());
            else if(hostile.getObject() && EntityUtil.isEntityHostile(entity))
                ESPUtils.drawEntity(ESPUtils.EntityMode.valueOf(entityMode.getMode().toUpperCase(Locale.ROOT)), entity, hostileColor.getObject(), hostileHiddenColor.getObject(), event.getPartialTicks());
            else if(other.getObject())
                ESPUtils.drawEntity(ESPUtils.EntityMode.valueOf(entityMode.getMode().toUpperCase(Locale.ROOT)), entity, otherColor.getObject(), otherHiddenColor .getObject(), event.getPartialTicks());
        }
    }

    @Subscribe
    public void onRenderSheepWool(EventRenderSheepWool event) {
        if(nonHostile.getObject()) {
            // This is to prevent color issues
            event.setCancelled(true);

            event.getSheepModel().setModelAttributes(event.getModelBase().getMainModel());
            event.getSheepModel().setLivingAnimations(event.getEntitylivingbaseIn(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getPartialTicks());
            event.getSheepModel().render(event.getEntitylivingbaseIn(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScale());
        }
    }
}