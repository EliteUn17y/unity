package me.eliteun17y.unity.util.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class EntityUtil {
    public static boolean isEntityPlayer(Entity entity) {
        return entity instanceof EntityPlayer;
    }
    public static boolean isEntityHostile(Entity entity) {
        return entity instanceof EntityMob;
    }
    public static boolean isEntityNonHostile(Entity entity) {
        return entity instanceof EntityAnimal || entity instanceof EntityVillager;
    }
}
