package me.eliteun17y.unity.module.impl.player;

import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.world.BlockUtil;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

public class Burrow extends Module {
    public Burrow() {
        super("Burrow", "Rubberbands you into a block.", Category.PLAYER, Keyboard.KEY_NONE);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        mc.getConnection().sendPacket(new CPacketPlayer.Position(Math.floor(mc.player.posX) + 0.5, Math.floor(mc.player.posY), Math.floor(mc.player.posZ) + 0.5, mc.player.onGround));
        mc.player.setPosition(Math.floor(mc.player.posX) + 0.5, Math.floor(mc.player.posY), Math.floor(mc.player.posZ) + 0.5);

        mc.getConnection().sendPacket(new CPacketPlayer.Position(Math.floor(mc.player.posX) + 0.5, Math.floor(mc.player.posY) + 2, Math.floor(mc.player.posZ) + 0.5, mc.player.onGround));
        BlockUtil.placeBlock(mc.player.getPosition(), EnumHand.MAIN_HAND);
        mc.getConnection().sendPacket(new CPacketPlayer.Position(Math.floor(mc.player.posX) + 0.5, Math.floor(mc.player.posY) - 5, Math.floor(mc.player.posZ) + 0.5, mc.player.onGround));
    }
}
