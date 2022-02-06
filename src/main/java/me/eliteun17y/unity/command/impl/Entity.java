package me.eliteun17y.unity.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.eliteun17y.unity.command.Command;
import me.eliteun17y.unity.util.chat.ChatUtil;
import net.minecraft.command.CommandGive;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;

import java.util.Locale;

public class Entity extends Command {
    public net.minecraft.entity.Entity e;

    public Entity() {
        super("Entity", "Allows you to interact with entities.");
    }

    @Override
    public void execute(String[] args) {
        net.minecraft.entity.Entity entity = mc.player.getRidingEntity() != null ? mc.player.getRidingEntity() : mc.objectMouseOver.entityHit != null ? mc.objectMouseOver.entityHit : null;
        if(entity == null || !(entity instanceof EntityLivingBase)) {
            ChatUtil.sendClientMessage("You are not riding or looking at an entity.");
            return;
        }
        if(args.length == 0) {
            ChatUtil.sendClientMessage("Name: " + entity.getName() + "\nHealth: " + ((EntityLivingBase) entity).getHealth() + " / " + ((EntityLivingBase) entity).getMaxHealth() + "\nEntity ID: " + entity.getEntityId());
        }else {
            switch(args[0].toLowerCase(Locale.ROOT)) {
                case "demount":
                    e = mc.player.getRidingEntity();
                    mc.player.dismountRidingEntity();
                    break;
                case "remount":
                    e.isDead = false;
                    mc.player.startRiding(e);
                    break;
            }
        }
    }
}
