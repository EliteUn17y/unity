package me.eliteun17y.unity.module.impl.combat;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.math.RandomUtil;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import me.eliteun17y.unity.util.time.Timer;
import me.eliteun17y.unity.util.world.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;

import javax.vecmath.Matrix3d;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class KillAura extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Closest", "Closest", "Multiple", "Switch");
    public BooleanValue rotation = new BooleanValue(this, "Rotation", true);
    public ModeValue rotationMode = new ModeValue("Rotation Mode", "Instant", "Instant");
    public NumberValue aps = new NumberValue("Attacks per second", 16, 20, 1, 1);
    public NumberValue range = new NumberValue(this, "Range", 4, 6, 1, 1);
    public BooleanValue randomAPS = new BooleanValue(this, "Random attacks per second", false);
    public NumberValue minAPS = new NumberValue("Minimum attacks per second", 14, 20, 1, 1);
    public NumberValue maxAPS = new NumberValue("Maximum attacks per second", 18, 20, 1, 1);


    public BooleanValue players = new BooleanValue(this, "Players", true);
    public BooleanValue nonHostile = new BooleanValue(this, "Non Hostile", true);
    public BooleanValue hostile = new BooleanValue(this, "Hostile", true);
    public BooleanValue other = new BooleanValue(this, "Other", false);

    public Timer timer = new Timer();

    public int index;

    public KillAura() {
        super("Kill Aura", "Automatically attacks for you.", Category.COMBAT, Keyboard.KEY_R);

        addValueOnValueChange(randomAPS, aps, false);
        addValueOnValueChange(randomAPS, minAPS, true);
        addValueOnValueChange(randomAPS, maxAPS, true);

        addValueOnValueChange(rotation, rotationMode, true);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        timer.reset();
        index = 0;
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if(event.isPre()) {
            ArrayList<Entity> entities = new ArrayList(Arrays.asList(mc.world.loadedEntityList.stream().filter(entity -> entity != mc.player && mc.player.getDistance(entity) <= range.getFloat() && ((EntityUtil.isEntityPlayer(entity) && !Unity.instance.friendManager.getRegistry().stream().anyMatch(friend -> friend.uuid.equals(entity.getUniqueID())) && players.getObject()) || (EntityUtil.isEntityNonHostile(entity) && nonHostile.getObject()) || (EntityUtil.isEntityHostile(entity) && hostile.getObject()) || other.getObject())).toArray()));
            if(entities.isEmpty()) return;

            if(timer.hasTimePassed(1000 / (randomAPS.getObject() ? RandomUtil.randomInt(minAPS.getInt(), maxAPS.getInt()) : aps.getInt()))) {
                switch(mode.getMode()) {
                    case "Closest":
                        if(getClosestEntity(entities) == null) return;
                        if(rotation.getObject())
                            rotate(getClosestEntity(entities));
                        mc.playerController.attackEntity(mc.player, getClosestEntity(entities));
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                        timer.reset();
                        break;
                    case "Multiple":
                        for(int i = 0; i < entities.size(); i++) {
                            if(!entities.isEmpty()) {
                                Entity entity = entities.get(i);

                                if(rotation.getObject())
                                    rotate(getClosestEntity(entities));

                                mc.playerController.attackEntity(mc.player, entity);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                            }
                        }
                        timer.reset();
                        break;
                    case "Switch":
                        if(index >= entities.size()) {
                            index = 0;
                        }
                        if(entities.get(index) != null) {
                            if(rotation.getObject())
                                rotate(getClosestEntity(entities));

                            mc.playerController.attackEntity(mc.player, entities.get(index));
                            mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                        else {
                            index = 0;
                        }

                        if(index + 1 < entities.size())
                            index += 1;
                        else
                            index = 0;

                        timer.reset();
                        break;
                }
            }
        }
    }

    public void rotate(Entity entity) {
        double[] rotations = getEntityRotations(entity);
        mc.player.rotationYaw = (float) rotations[0];
        mc.player.rotationPitch = (float) rotations[1];
    }

    public double[] getEntityRotations(Entity entity) {
        switch(rotationMode.getMode()) {
            case "Instant":
                double xRange = entity.posX - mc.player.posX;
                double yRange = entity.posY + entity.getEyeHeight() / 2 - (mc.player.posY + mc.player.getEyeHeight());
                double zRange = entity.posZ - mc.player.posZ;
                return new double[] {(Math.atan2(zRange, xRange) * 180 / Math.PI) - 90, Math.atan2(yRange, MathHelper.sqrt(xRange * xRange + zRange * zRange )) * 180 / Math.PI};
        }

        return new double[] {0, 0};
    }

    public Entity getClosestEntity(ArrayList<Entity> entities) {
        return entities.stream().min(Comparator.comparing(entity -> mc.player.getDistance(entity))).get();
    }
}
