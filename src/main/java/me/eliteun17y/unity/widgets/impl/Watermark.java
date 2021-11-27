package me.eliteun17y.unity.widgets.impl;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventRenderGame;
import me.eliteun17y.unity.util.Reference;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.widgets.Widget;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class Watermark extends Widget {
    public BooleanValue showName = new BooleanValue(this, "Show Name", true);
    public BooleanValue showVersion = new BooleanValue(this, "Show Version", true);

    public Watermark() {
        super("Watermark", "Displays a watermark of the client name.", 0, 0, 0, 0);
    }

    @Subscribe
    public void onRenderGame(EventRenderGame event) {
        if(event.getElementType() == RenderGameOverlayEvent.ElementType.TEXT) {
            scale();

            String s = "";

            if(showName.getObject())
                s += Reference.NAME + " ";

            if(showVersion.getObject())
                s += Reference.VERSION;

            width = FontManager.instance.robotoMedium.getStringWidth(s) * scale;
            height = FontManager.instance.robotoMedium.getStringHeight(s) * scale;

            FontManager.instance.robotoMedium.drawString(s, x, y, UIUtil.getOppositeFontColor().getRGB());

            unscale();
        }
    }
}
