package me.eliteun17y.unity.module.impl.player;

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
import me.eliteun17y.unity.util.world.BlockUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class Surround extends Module {
    public NumberValue delay = new NumberValue(this, "Delay", 5, 20, 0, 1);

    public BooleanValue renderBlocks = new BooleanValue(this, "Render Blocks", true);
    public ColorValue blockColor = new ColorValue("Block Color", new Color(65, 245, 197));
    public ColorValue blockFillColor = new ColorValue("Block Fill Color", new Color(65, 212, 245, 120));
    public ModeValue blockMode = new ModeValue(this, "Block Mode", "Both", "Both", "Fill", "Outline");

    public ArrayList<BlockPos> blocksToPlace;
    public Timer timer = new Timer();

    public Surround() {
        super("Surround", "Surrounds you with blocks.", Category.PLAYER, Keyboard.KEY_SEMICOLON);

        addValueOnValueChange(renderBlocks, blockColor, true);
        addValueOnValueChange(renderBlocks, blockMode, true);
        addValueOnValueChangeAndValueChange(renderBlocks, blockMode, blockFillColor, true, "Both");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        blocksToPlace = new ArrayList<>();
        timer.reset();

        mc.getConnection().sendPacket(new CPacketPlayer.Position(Math.floor(mc.player.posX) + 0.5, Math.floor(mc.player.posY), Math.floor(mc.player.posZ) + 0.5, mc.player.onGround));
        mc.player.setPosition(Math.floor(mc.player.posX) + 0.5, Math.floor(mc.player.posY), Math.floor(mc.player.posZ) + 0.5);

        addPosition(new BlockPos(Math.floor(mc.player.posX) + 1, Math.floor(mc.player.posY) - 1, Math.floor(mc.player.posZ)));
        addPosition(new BlockPos(Math.floor(mc.player.posX) - 1, Math.floor(mc.player.posY) - 1, Math.floor(mc.player.posZ)));
        addPosition(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY) - 1, Math.floor(mc.player.posZ) + 1));
        addPosition(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY) - 1, Math.floor(mc.player.posZ) - 1));
        addPosition(new BlockPos(Math.floor(mc.player.posX) + 1, Math.floor(mc.player.posY), Math.floor(mc.player.posZ)));
        addPosition(new BlockPos(Math.floor(mc.player.posX) - 1, Math.floor(mc.player.posY), Math.floor(mc.player.posZ)));
        addPosition(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ) + 1));
        addPosition(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ) - 1));
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        mc.player.motionX = 0;
        mc.player.motionZ = 0;
        // TODO: ROTATE
        // TODO: GET THIS TO WORK
        for(int i = 0; i < blocksToPlace.size(); i++) {
            long d = delay.getLong();
            if(d == 0 || timer.hasTimePassed(50 * d)) {
                BlockPos blockPos = blocksToPlace.get(i);
                rotate(blockPos);
                BlockUtil.placeBlock(blockPos, EnumHand.MAIN_HAND);
                timer.reset();
                blocksToPlace.remove(i);
            }
        }
        if(blocksToPlace.isEmpty()) {
            toggle();
        }
    }

    @Subscribe
    public void onRenderWorld(EventRenderWorld event) {
        for(BlockPos blockPos : blocksToPlace) {
            for(EnumFacing facing : EnumFacing.values()) {
                BlockPos neighbor = blockPos.offset(facing);
                EnumFacing oppositeFacing = facing.getOpposite();
                Vec3d vec = new Vec3d(neighbor.add(0.5, 0.5, 0.5)).add(new Vec3d(oppositeFacing.getDirectionVec()).scale(0.5));
                ESPUtils.drawBox(ESPUtils.BoxMode.valueOf(blockMode.getMode().toUpperCase(Locale.ROOT)), blockPos, blockColor.getObject(), blockFillColor.getObject());
                /*EnumActionResult result = mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor, oppositeFacing, vec, hand);
                if(result != EnumActionResult.FAIL)
                    mc.player.swingArm(EnumHand.MAIN_HAND);*/
            }
        }
        for(BlockPos blockPos : blocksToPlace) {
            ESPUtils.drawBox(ESPUtils.BoxMode.valueOf(blockMode.getMode().toUpperCase(Locale.ROOT)), blockPos, blockColor.getObject(), blockFillColor.getObject());
        }
    }

    public void addPosition(BlockPos pos) {
        if(!mc.world.getBlockState(pos).getBlock().isFullBlock(mc.world.getBlockState(pos))) {
            blocksToPlace.add(pos);
        }
    }

    public void rotate(BlockPos block) {
        double[] rotations = getBlockRotations(block);
        // mc.player.rotationYaw = (float) rotations[0];
        //mc.player.rotationPitch = (float) rotations[1];
        mc.getConnection().sendPacket(new CPacketPlayer.Rotation((float) rotations[0], (float) rotations[1], mc.player.onGround));
    }

    public double[] getBlockRotations(BlockPos block) {
        double xRange = block.getX() - mc.player.posX;
        double yRange = block.getY() - (mc.player.posY + mc.player.getEyeHeight());
        double zRange = block.getZ() - mc.player.posZ;
        double dist = MathHelper.sqrt(xRange * xRange + zRange * zRange);

        return new double[] {(MathHelper.atan2(zRange, xRange) * 180 / Math.PI) - 90, -(MathHelper.atan2(yRange, dist) * 180 / Math.PI)};
    }
}
