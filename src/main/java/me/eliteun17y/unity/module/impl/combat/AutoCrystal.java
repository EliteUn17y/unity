package me.eliteun17y.unity.module.impl.combat;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.chat.ChatUtil;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.setting.impl.ColorValue;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import me.eliteun17y.unity.util.time.Timer;
import me.eliteun17y.unity.util.world.EntityUtil;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class AutoCrystal extends Module {
    public ModeValue priority = new ModeValue(this, "Priority", "Health", "Health", "Distance");

    public BooleanValue placeCrystals = new BooleanValue(this, "Place Crystals", true);
    public BooleanValue breakCrystals = new BooleanValue(this, "Break Crystals", true);

    public NumberValue placeRange = new NumberValue("Place Range", 6, 6, 1, 0.1);
    public NumberValue breakRange = new NumberValue("Break Range", 6, 6, 1, 0.1);

    public NumberValue placeSpeed = new NumberValue("Place Speed", 10, 20, 1, 1);
    public NumberValue breakSpeed = new NumberValue("Break Speed", 20, 20, 1, 1);

    public NumberValue maximumSelfDamage = new NumberValue(this, "Maximum Self Damage", 10, 20, 1, 1);
    public NumberValue minimumEnemyDamage = new NumberValue(this, "Minimum Enemy Damage", 5, 20, 1, 1);

    public BooleanValue rotation = new BooleanValue(this, "Rotate", true);
    public ModeValue rotationMode = new ModeValue("Rotation Mode", "Instant", "Instant");

    public BooleanValue renderCrystalBlock = new BooleanValue(this, "Render Crystal Block", true);
    public ColorValue crystalBlockColor = new ColorValue("Crystal Block Color", new Color(245, 150, 65));

    public BooleanValue players = new BooleanValue(this, "Players", true);
    public BooleanValue nonHostile = new BooleanValue(this, "Non Hostile", false);
    public BooleanValue hostile = new BooleanValue(this, "Hostile", false);
    public BooleanValue other = new BooleanValue(this, "Other", false);
    public int ticks = 0;
    public Timer timer = new Timer();

    public AutoCrystal() {
        super("Auto Crystal", "Automatically places and breaks crystals", Category.COMBAT, Keyboard.KEY_C);
        addValueOnValueChange(placeCrystals, placeRange, true);
        addValueOnValueChange(breakCrystals, breakRange, true);

        addValueOnValueChange(placeCrystals, placeSpeed, true);
        addValueOnValueChange(breakCrystals, breakSpeed, true);

        addValueOnValueChange(rotation, rotationMode, true);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ticks = 0;
        timer.reset();
        EntityPlayer entityPlayer = new EntityPlayerSP(mc, mc.world, null, null, null);
        entityPlayer.posX = mc.player.posX;
        entityPlayer.posY = mc.player.posY;
        entityPlayer.posZ = mc.player.posZ;
        mc.world.addEntityToWorld(99999, entityPlayer);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        ArrayList<Entity> placeEntities = new ArrayList(Arrays.asList(mc.world.loadedEntityList.stream().filter(entity -> entity != mc.player && mc.player.getDistance(entity) <= placeRange.getFloat() && ((EntityUtil.isEntityPlayer(entity) && !Unity.instance.friendManager.getRegistry().stream().anyMatch(friend -> friend.uuid.equals(entity.getUniqueID())) && players.getObject()) || (EntityUtil.isEntityNonHostile(entity) && nonHostile.getObject()) || (EntityUtil.isEntityHostile(entity) && hostile.getObject()) || other.getObject() && !(entity instanceof EntityEnderCrystal))).toArray()));
        ArrayList<Entity> breakEntities = new ArrayList(Arrays.asList(mc.world.loadedEntityList.stream().filter(entity -> entity != mc.player && mc.player.getDistance(entity) <= breakRange.getFloat() && ((EntityUtil.isEntityPlayer(entity) && !Unity.instance.friendManager.getRegistry().stream().anyMatch(friend -> friend.uuid.equals(entity.getUniqueID())) && players.getObject()) || (EntityUtil.isEntityNonHostile(entity) && nonHostile.getObject()) || (EntityUtil.isEntityHostile(entity) && hostile.getObject()) || other.getObject() && !(entity instanceof EntityEnderCrystal))).toArray()));

        // Place
        for(Entity entity : placeEntities) {
            System.out.println("entities");
            ArrayList<BlockPos> blocks = getApplicableBlocks((int) entity.posX, (int) entity.posY, (int) entity.posZ, placeRange.getInt());
            for(BlockPos block : blocks) {
                System.out.println("block");
                ChatUtil.sendClientMessage("Damage: " + getDamage(block.getX(), block.getY(), block.getZ(), entity));
            }
        }
    }

    public ArrayList<BlockPos> getApplicableBlocks(int x, int y, int z, int radius) {
        ArrayList<BlockPos> blocks = new ArrayList<>();

        for(int x1 = x - radius / 2; x1 < x + radius / 2; x1++) {
            for(int y1 = y - radius / 2; y1 < y + radius / 2; y1++) {
                for(int z1 = z - radius / 2; z1 < z + radius / 2; z1++) {
                    Block block = mc.world.getBlockState(new BlockPos(x1, y1, z1)).getBlock();
                    if(block == Blocks.BEDROCK || block == Blocks.OBSIDIAN) {
                        for(Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(new BlockPos(x1, y1, z1).add(0, 1, 0)))) {
                            if(entity instanceof EntityEnderCrystal || entity instanceof EntityLivingBase) {
                                break;
                            }
                        }
                        blocks.add(new BlockPos(x1, y1, z1));
                    }
                }
            }
        }

        return blocks;
    }

    public float getDamage(EntityEnderCrystal crystal, Entity entity) {
        DamageSource damageSource = DamageSource.causeExplosionDamage(new Explosion(mc.world, null, crystal.posX, crystal.posY, crystal.posZ, 6, false, true));
        //entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f3 + 1.0D)));
        Vec3d vec3d = new Vec3d(crystal.posX, crystal.posY, crystal.posZ);
        float f3 = 6 * 2.0F;
        double d12 = entity.getDistance(crystal.posX, crystal.posY, crystal.posZ) / (double)f3;
        double d14 = (double)mc.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double d10 = (1.0D - d12) * d14;
        float damage = (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f3 + 1.0D));
        if(entity instanceof EntityPlayer) {
            damage = CombatRules.getDamageAfterAbsorb(damage, ((EntityPlayer) entity).getTotalArmorValue(), (float) ((EntityPlayer) entity).getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        }else {
            damage = CombatRules.getDamageAfterAbsorb(damage, 0, 0);
        }

        return damage;
    }

    public float getDamage(double x, double y, double z, Entity entity) {
        DamageSource damageSource = DamageSource.causeExplosionDamage(new Explosion(mc.world, null, x, y, z, 6, false, true));
        //entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f3 + 1.0D)));
        Vec3d vec3d = new Vec3d(x, y, z);
        float f3 = 6 * 2.0F;
        double d12 = entity.getDistance(x, y, z) / (double)f3;
        double d14 = (double)mc.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double d10 = (1.0D - d12) * d14;
        float damage = (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f3 + 1.0D));
        if(entity instanceof EntityPlayer) {
            damage = CombatRules.getDamageAfterAbsorb(damage, ((EntityPlayer) entity).getTotalArmorValue(), (float) ((EntityPlayer) entity).getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        }else {
            damage = CombatRules.getDamageAfterAbsorb(damage, 0, 0);
        }

        return damage;
    }

    public Entity getClosestEntity(ArrayList<Entity> entities) {
        return entities.stream().min(Comparator.comparing(entity -> mc.player.getDistance(entity))).get();
    }
}