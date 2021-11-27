package me.eliteun17y.unity.module.impl.player;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Offhand extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Totem of Undying", "Totem of Undying", "Golden Apple");

    public Offhand() {
        super("Offhand", "Automatically puts items in your offhand.", Category.PLAYER, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        Item item = null;
        switch(mode.getMode()) {
            case "Totem of Undying":
                item = Items.TOTEM_OF_UNDYING;
                break;
            case "Golden Apple":
                item = Items.GOLDEN_APPLE;
                break;
        }

        assert item != null;

        if(mc.player.getHeldItemOffhand().getItem() != item) {
            for(int i = 0; i < mc.player.inventoryContainer.getInventory().size(); i++) {
                ItemStack itemStack = mc.player.inventoryContainer.getInventory().get(i);
                if(itemStack.getItem() == item) {
                    // TODO: Make this work
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i, 0, ClickType.PICKUP, mc.player);

                    mc.playerController.updateController();
                }
            }
        }
    }
}
