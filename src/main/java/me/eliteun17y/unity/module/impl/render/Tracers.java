package me.eliteun17y.unity.module.impl.render;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventRenderWorld;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.render.TracerUtils;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.setting.impl.ColorValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import me.eliteun17y.unity.util.world.EntityUtil;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Tracers extends Module {
    public BooleanValue players = new BooleanValue(this, "Players", true);
    public ColorValue playerColor = new ColorValue("Player Color", new Color(255, 90, 90));
    public BooleanValue nonHostile = new BooleanValue(this, "Non Hostile", true);
    public ColorValue nonHostileColor = new ColorValue("Non Hostile Color", new Color(90, 255, 240));
    public BooleanValue hostile = new BooleanValue(this, "Hostile", true);
    public ColorValue hostileColor = new ColorValue("Hostile Color", new Color(164, 90, 255));
    public BooleanValue other = new BooleanValue(this, "Other", false);
    public ColorValue otherColor = new ColorValue("Other Color", new Color(255, 255, 230));
    public NumberValue lineWidth = new NumberValue(this, "Line Width", 2, 5, 1, 1);

    public Method method;

    public Tracers() {
        super("Tracers", "Draws tracers to entities.", Category.RENDER, Keyboard.KEY_NONE);
        method = ObfuscationReflectionHelper.findMethod(EntityRenderer.class, "func_78479_a", void.class, float.class, int.class);
        method.setAccessible(true);
        addValueOnValueChange(players, playerColor, true);
        addValueOnValueChange(nonHostile, nonHostileColor, true);
        addValueOnValueChange(hostile, hostileColor, true);
        addValueOnValueChange(other, otherColor, true);
    }

    @Subscribe
    public void onRenderWorld(EventRenderWorld event) {
        if(mc.player.ticksExisted <= 10) return; // This is to prevent a weird rendering issue
        boolean oldBobbing = mc.gameSettings.viewBobbing;
        mc.gameSettings.viewBobbing = false;

        fixBobbing(event.getPartialTicks());

        for(Entity entity : mc.world.loadedEntityList) {
            if(entity == mc.player) continue;
            if(players.getObject() && EntityUtil.isEntityPlayer(entity))
                TracerUtils.drawTracerToEntity(entity, event.getPartialTicks(), lineWidth.getObject().intValue(), playerColor.getObject());
            else if(nonHostile.getObject() && EntityUtil.isEntityNonHostile(entity))
                TracerUtils.drawTracerToEntity(entity, event.getPartialTicks(), lineWidth.getObject().intValue(), nonHostileColor.getObject());
            else if(hostile.getObject() && EntityUtil.isEntityHostile(entity))
                TracerUtils.drawTracerToEntity(entity, event.getPartialTicks(), lineWidth.getObject().intValue(), hostileColor.getObject());
            else if(other.getObject())
                TracerUtils.drawTracerToEntity(entity, event.getPartialTicks(), lineWidth.getObject().intValue(), otherColor.getObject());
        }

        mc.gameSettings.viewBobbing = oldBobbing;
        fixBobbing(event.getPartialTicks());
    }

    public void fixBobbing(float partialTicks) {
        try {
            method.invoke(mc.entityRenderer, partialTicks, 0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
