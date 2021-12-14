package me.eliteun17y.unity.widgets.impl;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventRenderGame;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.math.ConversionUtil;
import me.eliteun17y.unity.util.player.PlayerUtil;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.TextValue;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.util.world.TimerUtil;
import me.eliteun17y.unity.widgets.Widget;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class Speed extends Widget {
    public ModeValue mode = new ModeValue(this, "Mode", "Miles per hour", "Meters per second", "Miles per hour", "Kilometers per hour");
    public BooleanValue prefix = new BooleanValue(this, "Prefix", false);
    public BooleanValue suffix = new BooleanValue(this, "Suffix", true);
    public TextValue prefixText = new TextValue("Prefix Text", "Speed: ");
    public TextValue suffixText = new TextValue("Suffix Text", " MPH");

    public float lastWidth = 0;

    public Speed() {
        super("Speed", "Shows how fast you're going.", 0, 0, 0, 0);
        addValueOnValueChange(prefix, prefixText, true);
        addValueOnValueChange(suffix, suffixText, true);
    }

    @Subscribe
    public void onRenderGame(EventRenderGame event) {
        if (event.getElementType() == RenderGameOverlayEvent.ElementType.TEXT) {
            scale();

            String s = "";

            if(prefix.getObject())
                s = prefixText.getObject();

            double speed = PlayerUtil.getSpeed() * TimerUtil.getSpeed();

            s = mode.getMode().equalsIgnoreCase("Miles per hour") ? Double.toString(ConversionUtil.metersPerSecondToMilesPerHour(speed * 20)) : mode.getMode().equalsIgnoreCase("Kilometers per hour") ? Double.toString(ConversionUtil.metersPerSecondToKilometersPerHour(speed * 20)) : Double.toString(ConversionUtil.round(speed * 20, 2));

            if(suffix.getObject())
                s += suffixText.getObject();

            ScaledResolution sr = new ScaledResolution(mc);

            if(width > FontManager.instance.robotoMedium.getStringWidth(s) * scale && (x * scale + width / 2) > sr.getScaledWidth() / 2.0f && !scaling) {
                x = x + width - FontManager.instance.robotoMedium.getStringWidth(s) * scale;
            }else if(lastWidth < FontManager.instance.robotoMedium.getStringWidth(s) * scale && (x * scale + width / 2) > sr.getScaledWidth() / 2.0f && !scaling){
                x = x + width - FontManager.instance.robotoMedium.getStringWidth(s) * scale;
            }

            FontManager.instance.robotoMedium.drawString(s, x, y, UIUtil.getOppositeFontColor().getRGB());

            width = FontManager.instance.robotoMedium.getStringWidth(s) * scale;
            height = FontManager.instance.robotoMedium.getStringHeight(s) * scale;

            unscale();
        }
    }
}
