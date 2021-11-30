package me.eliteun17y.unity.module.impl.combat;

import com.mojang.authlib.GameProfile;
import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventRenderWorld;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.chat.ChatUtil;
import me.eliteun17y.unity.util.render.ESPUtils;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.setting.impl.ColorValue;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import me.eliteun17y.unity.util.time.Timer;
import me.eliteun17y.unity.util.world.EntityUtil;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.*;

public class AutoCrystal extends Module {
    public ModeValue blockPriority = new ModeValue(this, "Block Priority", "Health", "Health", "Distance");
    public ModeValue entityPriority = new ModeValue(this, "Entity Priority", "Distance", "Distance");

    public BooleanValue placeCrystals = new BooleanValue(this, "Place Crystals", true);
    public BooleanValue breakCrystals = new BooleanValue(this, "Break Crystals", true);

    public NumberValue entityRange = new NumberValue("Entity Range", 12, 12, 1, 0.1);

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
    public ColorValue crystalBlockFillColor = new ColorValue("Crystal Block Fill Color", new Color(245, 150, 65));
    public ModeValue crystalBlockMode = new ModeValue(this, "Crystal Block Mode", "Both", "Both", "Fill", "Outline");

    public BooleanValue players = new BooleanValue(this, "Players", true);
    public BooleanValue nonHostile = new BooleanValue(this, "Non Hostile", false);
    public BooleanValue hostile = new BooleanValue(this, "Hostile", false);
    public BooleanValue other = new BooleanValue(this, "Other", false);

    public int ticks = 0;
    public Timer placeTimer = new Timer();
    public Timer breakTimer = new Timer();

    public BlockPos currentBlockPos;


    public AutoCrystal() {
        super("Auto Crystal", "Automatically places and breaks crystals", Category.COMBAT, Keyboard.KEY_C, 1);
        addValueOnValueChange(placeCrystals, placeRange, true);
        addValueOnValueChange(breakCrystals, breakRange, true);

        addValueOnValueChange(placeCrystals, placeSpeed, true);
        addValueOnValueChange(breakCrystals, breakSpeed, true);

        addValueOnValueChange(rotation, rotationMode, true);

        addValueOnValueChange(renderCrystalBlock, crystalBlockColor, true);
        addValueOnValueChange(renderCrystalBlock, crystalBlockMode, true);

        addValueOnValueChangeAndValueChange(renderCrystalBlock, crystalBlockMode, crystalBlockFillColor, true, "Both");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ticks = 0;
        placeTimer.reset();
        breakTimer.reset();
        currentBlockPos = null;
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        ArrayList<Entity> entities = new ArrayList(Arrays.asList(mc.world.loadedEntityList.stream().filter(entity -> entity != mc.player && mc.player.getDistance(entity) <= entityRange.getFloat() && ((EntityUtil.isEntityPlayer(entity) && !Unity.instance.friendManager.getRegistry().stream().anyMatch(friend -> friend.name.equals(entity.getName())) && players.getObject()) || (EntityUtil.isEntityNonHostile(entity) && nonHostile.getObject()) || (EntityUtil.isEntityHostile(entity) && hostile.getObject()) || other.getObject() && !(entity instanceof EntityEnderCrystal))).toArray()));

        if (!entities.isEmpty()) {
            Entity entity = getEntity(entities);

            // Place
            if(placeCrystals.getObject()) {
                if(placeTimer.hasTimePassed(1000 / placeSpeed.getInt())) {
                    ArrayList<BlockPos> blocks = getApplicableBlocks((int) mc.player.posX, (int) mc.player.posY, (int) mc.player.posZ, placeRange.getInt());
                    BlockPos block = getBlock(blocks, entity);
                    if(getDamage(block.getX(), block.getY(), block.getZ(), entity) >= minimumEnemyDamage.getFloat()) {
                        currentBlockPos = block;
                        // TODO: Make this use the right hand and and also add antisuicide and better break mode and then maximum self damage
                        mc.getConnection().sendPacket(new CPacketPlayerTryUseItemOnBlock(block, EnumFacing.getDirectionFromEntityLiving(block, mc.player), EnumHand.MAIN_HAND, 0, 0, 0));
                    }
                    placeTimer.reset();
                }
            }

            // Break

            if(breakCrystals.getObject()) {
                if(breakTimer.hasTimePassed(1000 / breakSpeed.getInt())) {
                    ArrayList<EntityEnderCrystal> enderCrystals = new ArrayList<>();

                    for(Entity e : mc.world.loadedEntityList) {
                        if(mc.player.getDistance(e) > breakRange.getFloat()) continue;
                        if(e instanceof EntityEnderCrystal)
                            enderCrystals.add((EntityEnderCrystal) e);
                    }

                    if(!enderCrystals.isEmpty()) {
                        EntityEnderCrystal enderCrystal = getCrystal(enderCrystals, entity);
                        mc.playerController.attackEntity(mc.player, enderCrystal);
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                    }

                    breakTimer.reset();
                }
            }
        }else {
            currentBlockPos = null;
        }


    }

    @Subscribe
    public void onRenderWorld(EventRenderWorld event) {
        if(currentBlockPos != null) {
            ESPUtils.drawBox(ESPUtils.BoxMode.valueOf(crystalBlockMode.getMode().toUpperCase(Locale.ROOT)), currentBlockPos, crystalBlockColor.getObject(), crystalBlockFillColor.getObject());
        }
    }

    public ArrayList<BlockPos> getApplicableBlocks(int x, int y, int z, int radius) {
        ArrayList<BlockPos> blocks = new ArrayList<>();

        for(int x1 = (int) (mc.player.posX - radius); x1 < mc.player.posX + radius; x1++) {
            for(int y1 = (int) (mc.player.posY - radius); y1 < mc.player.posY + radius; y1++) {
                for(int z1 = (int) (mc.player.posZ - radius); z1 < mc.player.posZ + radius; z1++) {
                    Block block = mc.world.getBlockState(new BlockPos(x1, y1, z1)).getBlock();
                    if(block == Blocks.BEDROCK || block == Blocks.OBSIDIAN) {
                        if(mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(new BlockPos(x1, y1, z1).add(0, 1, 0))).isEmpty() && mc.world.getBlockState(new BlockPos(x1, y1, z1).add(0, 1, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(new BlockPos(x1, y1, z1).add(0, 2, 0)).getBlock() == Blocks.AIR) {
                            blocks.add(new BlockPos(x1, y1, z1));
                        }
                    }
                }
            }
        }

        return blocks;
    }

    public float getDamage(double x, double y, double z, Entity entity) {
        if(entity instanceof EntityLivingBase) {
            Vec3d vec3d = new Vec3d(x + 0.5, y + 1, z + 0.5);
            float f3 = 6 * 2.0F;
            double d12 = entity.getDistance(x + 0.5, y + 1, z + 0.5) / (double)f3;
            double d14 = mc.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
            double d10 = (1.0D - d12) * d14;
            float damage = (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f3 + 1.0D));

            DamageSource damageSource = DamageSource.causeExplosionDamage(new Explosion(mc.world, null, x + 0.5, y + 1, z + 0.5, 6, false, true));

            damage = CombatRules.getDamageAfterAbsorb(damage, ((EntityLivingBase) entity).getTotalArmorValue(), (float) ((EntityLivingBase) entity).getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k = EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), damageSource);
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;

            return damage;
        }else {
            return 0;
        }
    }

    public float getDamage(EntityEnderCrystal crystal, Entity entity) {
        if(entity instanceof EntityLivingBase) {
            Vec3d vec3d = new Vec3d(crystal.posX, crystal.posY, crystal.posZ);
            float f3 = 6 * 2.0F;
            double d12 = entity.getDistance(crystal.posX, crystal.posY, crystal.posZ) / (double)f3;
            double d14 = mc.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
            double d10 = (1.0D - d12) * d14;
            float damage = (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f3 + 1.0D));

            damage = CombatRules.getDamageAfterAbsorb(damage, ((EntityLivingBase) entity).getTotalArmorValue(), (float) ((EntityLivingBase) entity).getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());


            DamageSource damageSource = DamageSource.causeExplosionDamage(new Explosion(mc.world, null, crystal.posX, crystal.posY, crystal.posZ, 6, false, true));

            damage = CombatRules.getDamageAfterAbsorb(damage, ((EntityLivingBase) entity).getTotalArmorValue(), (float) ((EntityLivingBase) entity).getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k = EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), damageSource);
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;

            return damage;
        }else {
            return 0;
        }
    }

    public Entity getEntity(ArrayList<Entity> entities) {
        switch(entityPriority.getMode()) {
            case "Distance":
                return entities.stream().min(Comparator.comparing(entity -> mc.player.getDistance(entity))).get();
        }

        return null;
    }

    public BlockPos getBlock(ArrayList<BlockPos> blocks, Entity entity) {
        switch(blockPriority.getMode()) {
            case "Health":
                return blocks.stream().max(Comparator.comparing(blockPos -> getDamage(blockPos.getX(), blockPos.getY(), blockPos.getZ(), entity))).get();
        }

        return null;
    }

    public EntityEnderCrystal getCrystal(ArrayList<EntityEnderCrystal> entities, Entity entity) {
        switch(blockPriority.getMode()) {
            case "Health":
                return entities.stream().max(Comparator.comparing(e -> getDamage(e, entity))).get();
        }

        return null;
    }
}