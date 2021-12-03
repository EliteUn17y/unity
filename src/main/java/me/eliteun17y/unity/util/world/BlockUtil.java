package me.eliteun17y.unity.util.world;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BlockUtil {
    public static void placeBlock(BlockPos blockPos, EnumHand hand) {
        Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayerTryUseItemOnBlock(blockPos, EnumFacing.getDirectionFromEntityLiving(blockPos, Minecraft.getMinecraft().player).getOpposite(), hand, 90, -45, 0));
    }
}
