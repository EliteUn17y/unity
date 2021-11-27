package me.eliteun17y.unity.module.impl.render;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import org.lwjgl.input.Keyboard;

public class Weather extends Module {
    public BooleanValue rain = new BooleanValue(this, "Rain", false);
    public BooleanValue thunder = new BooleanValue(this, "Thunder", false);

    public Weather() {
        super("Weather", "Allows you to change the weather (client sided).", Category.RENDER, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if(rain.getObject())
            mc.world.setRainStrength(1);
        else
            mc.world.setRainStrength(0);

        if(thunder.getObject())
            mc.world.setThunderStrength(1);
        else
            mc.world.setThunderStrength(0);
    }
}
