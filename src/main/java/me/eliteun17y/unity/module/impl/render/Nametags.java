package me.eliteun17y.unity.module.impl.render;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventRenderLivingLabel;
import me.eliteun17y.unity.event.impl.EventRenderWorld;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.render.NametagUtils;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.setting.impl.ColorValue;
import me.eliteun17y.unity.util.world.EntityUtil;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Nametags extends Module {
    public BooleanValue armor = new BooleanValue(this, "Armor", true);
    public BooleanValue background = new BooleanValue(this, "Background (turning off will increase performance with a large amount of entities)", true);
    public BooleanValue health = new BooleanValue(this, "Health", true);
    public BooleanValue heldItem = new BooleanValue(this, "Held Item", true);
    public BooleanValue maxHealth = new BooleanValue(this, "Max Health", true);
    public BooleanValue ping = new BooleanValue(this, "Ping", true);
    public BooleanValue players = new BooleanValue(this, "Players", true);
    public BooleanValue nonHostile = new BooleanValue(this, "Non Hostile", true);
    public BooleanValue hostile = new BooleanValue(this, "Hostile", true);
    public BooleanValue other = new BooleanValue(this, "Other", false);
    public ColorValue textColor = new ColorValue(this, "Text Color", new Color(255, 255, 255));
    public ColorValue backgroundBorder = new ColorValue("Background Border Color", new Color(80, 80, 80));
    public ColorValue backgroundFill = new ColorValue("Background Fill Color", new Color(80, 80, 80, 100));


    public Nametags() {
        super("Nametags", "More detailed nametags on entities,", Category.RENDER, Keyboard.KEY_NONE);
        addValueOnValueChange(background, backgroundBorder, true);
        addValueOnValueChange(background, backgroundFill, true);
    }

    @Subscribe
    public void onRenderWorld(EventRenderWorld event) {
        for(Entity entity : mc.world.loadedEntityList) {
            if(entity == mc.player) continue;
            if(players.getObject() && EntityUtil.isEntityPlayer(entity))
                NametagUtils.drawNametag(entity, event.getPartialTicks(), armor.getObject(), background.getObject(), health.getObject(), heldItem.getObject(), maxHealth.getObject(), ping.getObject(), textColor.getObject(), backgroundBorder.getObject(), backgroundFill.getObject());
            else if(nonHostile.getObject() && EntityUtil.isEntityNonHostile(entity))
                NametagUtils.drawNametag(entity, event.getPartialTicks(), armor.getObject(), background.getObject(), health.getObject(), heldItem.getObject(), maxHealth.getObject(), ping.getObject(), textColor.getObject(), backgroundBorder.getObject(), backgroundFill.getObject());
            else if(hostile.getObject() && EntityUtil.isEntityHostile(entity))
                NametagUtils.drawNametag(entity, event.getPartialTicks(), armor.getObject(), background.getObject(), health.getObject(), heldItem.getObject(), maxHealth.getObject(), ping.getObject(), textColor.getObject(), backgroundBorder.getObject(), backgroundFill.getObject());
            else if(other.getObject())
                NametagUtils.drawNametag(entity, event.getPartialTicks(), armor.getObject(), background.getObject(), health.getObject(), heldItem.getObject(), maxHealth.getObject(), ping.getObject(), textColor.getObject(), backgroundBorder.getObject(), backgroundFill.getObject());
        }
    }

    @Subscribe
    public void onRenderLivingLabel(EventRenderLivingLabel event) {
        if(event.isPre())
            event.setCancelled(true);
    }
}
