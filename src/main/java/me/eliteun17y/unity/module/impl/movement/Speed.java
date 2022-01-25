package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Direction;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventPacket;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.chat.ChatUtil;
import me.eliteun17y.unity.util.player.PlayerUtil;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import me.eliteun17y.unity.util.world.TimerUtil;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Normal", "Normal", "Anti Cheat Reloaded", "Anti Cheat Reloaded Insane", "No Cheat Plus");
    public NumberValue speed = new NumberValue(this, "Speed", 0.5, 5, 0, 0.1);
    public BooleanValue autoJump = new BooleanValue(this, "Auto Jump", true);
    public NumberValue height = new NumberValue("Height", 0.42, 1, 0.01f, 0.01);

    public Speed() {
        super("Speed", "Allows for faster movement.", Category.MOVEMENT, Keyboard.KEY_B);
        addValueOnValueChange(autoJump, height, true);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        switch(mode.getMode()) {
            case "Normal":
                if(mc.player.onGround) {
                    if(autoJump.getObject()) {
                        mc.player.jump();
                        mc.player.motionY = height.getFloat();
                    }
                }

                PlayerUtil.strafe(speed.getDouble());
                break;
            case "Anti Cheat Reloaded Insane":
                TimerUtil.setSpeed(mc.player.ticksExisted % 4 == 0 ? 4 : 8f);
                if(mc.player.ticksExisted % 12 == 0)
                    TimerUtil.setSpeed(10);
                double x = PlayerUtil.getDirection()[0] * 0.125;
                double z = PlayerUtil.getDirection()[1] * 0.125;
                mc.player.setPosition(mc.player.posX + x, mc.player.posY, mc.player.posZ + z);
                break;
            case "Anti Cheat Reloaded":
                if(mc.player.onGround) {
                    if(autoJump.getObject()) {
                        mc.player.jump();
                        mc.player.motionY = height.getFloat();
                        PlayerUtil.strafe(-1);
                    }
                }


                /*TimerUtil.setSpeed(mc.player.ticksExisted % 4 == 0 ? 4 : 8f);
                if(mc.player.ticksExisted % 16 == 0)
                    TimerUtil.setSpeed(12);*/


                /*double x1 = PlayerUtil.getDirection()[0] * 0.125;
                double z1 = PlayerUtil.getDirection()[1] * 0.125;
                mc.player.setPosition(mc.player.posX + x1, mc.player.posY, mc.player.posZ + z1);*/
                PlayerUtil.strafe();
                break;
            case "No Cheat Plus":
                if(event.isPre()) {
                    if(mc.player.onGround) {
                        if(autoJump.getObject()) {
                            mc.player.jump();
                        }

                        mc.player.jumpMovementFactor = 1;
                    }else{
                        if(mc.player.ticksExisted % 2 == 0) {
                            TimerUtil.setSpeed(1.18f);
                        }else{
                            TimerUtil.setSpeed(1.04f);
                        }

                        mc.player.jumpMovementFactor = 0.025f;
                    }

                    if(mc.player.fallDistance > 0.6f) {
                        mc.player.motionY = -60f;
                    }

                    PlayerUtil.strafe();
                }
                break;

        }
    }

    @Subscribe
    public void onPacket(EventPacket event) {
    }

    @Override
    public void onDisable() {
        super.onDisable();
        TimerUtil.setSpeed(1);
    }
}