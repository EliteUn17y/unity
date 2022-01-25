package me.eliteun17y.unity.widgets.impl;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventRenderGame;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.widgets.Widget;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.Calendar;

public class Greeting extends Widget {
    public Greeting() {
        super("Greeting", "Gives you a nice message based on the time.", 0, 0, 0, 0);
    }

    @Subscribe
    public void onRenderGame(EventRenderGame event) {
        if(event.getElementType() == RenderGameOverlayEvent.ElementType.TEXT) {
            scale();

            String s = "";

            Calendar c = Calendar.getInstance();
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

            if(timeOfDay < 12) {
                s = "Good morning";
            }else if (timeOfDay < 16) {
                s = "Good afternoon";
            }else if(timeOfDay < 21) {
                s = "Good evening";
            }else {
                s = "Good night";
            }

            s = s + ", " + mc.player.getName() + ".";

            width = FontManager.instance.robotoMedium.getStringWidth(s) * scale;
            height = FontManager.instance.robotoMedium.getStringHeight(s) * scale;

            FontManager.instance.robotoMedium.drawString(s, x, y, UIUtil.getOppositeFontColor().getRGB());

            unscale();
        }
    }
}
