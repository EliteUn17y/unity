package me.eliteun17y.unity.module.impl.combat;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.world.TimerUtil;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class FastBow extends Module {
    public FastBow() {
        super("Fast Bow", "Allows you to shoot a bow faster than intended.", Category.COMBAT, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if((mc.player.getHeldItemMainhand().getItem() instanceof ItemBow || mc.player.getHeldItemOffhand().getItem() instanceof ItemBow) && mc.player.isHandActive() && 6 < mc.player.getItemInUseMaxCount()) {
            mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
            mc.getConnection().sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
            mc.player.stopActiveHand();
        }
    }
}
