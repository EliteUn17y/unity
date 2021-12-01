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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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

        mc.player.setPosition(Math.floor(mc.player.posX) + 0.5, Math.floor(mc.player.posY), Math.floor(mc.player.posZ) + 0.5);

        addPosition(new BlockPos(Math.floor(mc.player.posX) + 1, Math.floor(mc.player.posY), Math.floor(mc.player.posZ)));
        addPosition(new BlockPos(Math.floor(mc.player.posX) - 1, Math.floor(mc.player.posY), Math.floor(mc.player.posZ)));
        addPosition(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ) + 1));
        addPosition(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ) - 1));
        addPosition(new BlockPos(Math.floor(mc.player.posX) + 1, Math.floor(mc.player.posY) - 1, Math.floor(mc.player.posZ)));
        addPosition(new BlockPos(Math.floor(mc.player.posX) - 1, Math.floor(mc.player.posY) - 1, Math.floor(mc.player.posZ)));
        addPosition(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY) - 1, Math.floor(mc.player.posZ) + 1));
        addPosition(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY) - 1, Math.floor(mc.player.posZ) - 1));
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        for(int i = 0; i < blocksToPlace.size(); i++) {
            if(delay.getLong() == 0 || timer.hasTimePassed(1000 / delay.getLong())) {
                BlockPos blockPos = blocksToPlace.get(i);
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
            ESPUtils.drawBox(ESPUtils.BoxMode.valueOf(blockMode.getMode().toUpperCase(Locale.ROOT)), blockPos, blockColor.getObject(), blockFillColor.getObject());
        }
    }

    public void addPosition(BlockPos pos) {
        if(!mc.world.getBlockState(pos).getBlock().isFullBlock(mc.world.getBlockState(pos))) {
            blocksToPlace.add(pos);
        }
    }
}
