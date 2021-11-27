package me.eliteun17y.unity.util.player;

import me.eliteun17y.unity.util.world.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;

public class PlayerUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void strafe() {
        strafe(mc.player, getSpeed());
    }

    public static void strafe(double speed) {
        strafe(mc.player, speed);
    }

    public static void strafe(Entity entity, double speed) {
        entity.motionX = getDirection()[0] * speed;
        entity.motionZ = getDirection()[1] * speed;
    }

    public static double[] getDirection() {
        double yaw = mc.player.rotationYaw;
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        if(forward != 0) {
            if(strafe > 0)
                yaw += ((forward > 0) ? -45 : 45);
            if(strafe < 0)
                yaw += ((forward > 0) ? 45 : -45);
            strafe = 0;
        }
        double x;
        double z;
        if(strafe == 0) {
            x = forward * Math.cos(Math.toRadians(yaw + 90));
            z = forward * Math.sin(Math.toRadians(yaw + 90));
        }else{
            x = strafe * Math.cos(Math.toRadians(yaw));
            z = strafe * Math.sin(Math.toRadians(yaw));
        }
        return new double[] {x, z};
    }

    public static double getYaw() {
        double yaw = mc.player.rotationYaw;
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        if(forward != 0) {
            if(strafe > 0)
                yaw += ((forward > 0) ? -45 : 45);
            if(strafe < 0)
                yaw += ((forward > 0) ? 45 : -45);
        }
        return yaw;
    }

    public static double getSpeed() {
        return Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
    }

    public static void damage(int damage) {
        double x = mc.player.posX;
        double y = mc.player.posY;
        double z = mc.player.posZ;

        mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y+4, z, false));
        mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, false));
        mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, true));
        mc.player.onGround = true;
    }

    public static int getDistanceToGround() {
        int distance = 0;

        for(int i = (int) mc.player.posY - 1; i > -1; i--) {
            if(mc.world.getBlockState(new BlockPos(mc.player.posX, i, mc.player.posZ)).getBlock() == Blocks.AIR) {
                distance++;
            }else{
                return distance;
            }
        }

        return distance;
    }
}
