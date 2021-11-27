package me.eliteun17y.unity.widgets.impl;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventRenderGame;
import me.eliteun17y.unity.widgets.Widget;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class Player extends Widget {
    public Player() {
        super("Player", "Shows you a view of yourself.", 0, 0, 0, 0);
    }

    @Subscribe
    public void onRenderGame(EventRenderGame event) {
        if (event.getElementType() == RenderGameOverlayEvent.ElementType.TEXT) {
            scale();

            GuiInventory.drawEntityOnScreen((int) (x + 12.5f), (int) y + 50, 25, 0, 0, mc.player);

            width = 25 * scale;
            height = 50 * scale;

            unscale();
        }
    }
}
