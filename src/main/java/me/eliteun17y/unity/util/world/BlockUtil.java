package me.eliteun17y.unity.util.world;

import me.eliteun17y.unity.util.chat.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BlockUtil {
    public static void placeBlock(BlockPos blockPos, EnumHand hand) {
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR) return;
        for(EnumFacing facing : EnumFacing.values()) {
            BlockPos neighbor = blockPos.offset(facing);
            EnumFacing oppositeFacing = facing.getOpposite();
            if(mc.world.getBlockState(neighbor).getBlock().canCollideCheck(mc.world.getBlockState(neighbor), false)) {
                Vec3d vec = new Vec3d(neighbor.add(0.5, 0.5, 0.5)).add(new Vec3d(oppositeFacing.getDirectionVec()).scale(0.5));
                EnumActionResult result = mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor, oppositeFacing, vec, hand);
                if(result != EnumActionResult.FAIL)
                    mc.player.swingArm(hand);
            }
        }
    }
}
