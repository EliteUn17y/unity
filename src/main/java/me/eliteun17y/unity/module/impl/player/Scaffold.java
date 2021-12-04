package me.eliteun17y.unity.module.impl.player;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import me.eliteun17y.unity.util.time.Timer;
import me.eliteun17y.unity.util.world.BlockUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class Scaffold extends Module {
    public NumberValue delay = new NumberValue(this, "Delay", 0, 20, 0, 1);

    public Timer timer = new Timer();

    public Scaffold() {
        super("Scaffold", "Automatically places blocks beneath you.", Category.PLAYER, Keyboard.KEY_X);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        timer.reset();
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        BlockPos pos = new BlockPos(Math.floor(mc.player.posX) + 0.5, Math.floor(mc.player.posY) - 1, Math.floor(mc.player.posZ) + 0.5);
        if(timer.hasTimePassed(50 * delay.getLong())) {
            BlockPos[] array = { pos.north(), pos.south(), pos.east(), pos.west() };
            float minDistance = 100;
            BlockPos minDistanceBlock = null;
            if(mc.world.getBlockState(pos).getBlock() != Blocks.AIR) {
                for(BlockPos blockPos : array) {
                    if(blockPos.getDistance((int) mc.player.posX, (int) mc.player.posY, (int) mc.player.posZ) < minDistance) {
                        minDistance = (float) blockPos.getDistance((int) mc.player.posX, (int) mc.player.posY, (int) mc.player.posZ);
                        minDistanceBlock = blockPos;
                    }
                }
            }
            if(minDistanceBlock == null)
                BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND);
            else
                BlockUtil.placeBlock(minDistanceBlock, EnumHand.MAIN_HAND);
            timer.reset();
        }
    }
}
