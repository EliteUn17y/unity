package me.eliteun17y.unity.module.impl.render;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.impl.BooleanValue;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Gamma", "Gamma", "Potion");
    public BooleanValue shouldAnimate = new BooleanValue("Should Animate", true);
    public NumberValue animateSpeed = new NumberValue("Animation Speed", 0.3, 1, 0.1f, 0.1);

    public float anim;
    public float oldGamma;
    public boolean disableAnim;

    public Fullbright() {
        super("Fullbright", "Allows you to see in the dark.", Category.RENDER, Keyboard.KEY_NONE);
        addValueOnValueChange(mode, shouldAnimate, "Gamma");
        addValueOnValueChange(shouldAnimate, animateSpeed, true);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        anim = 0;
        oldGamma = mc.gameSettings.gammaSetting;
        disableAnim = false;
    }

    @Override
    public void onDisable() {
        disableAnim = true;
        if(mode.getMode().equalsIgnoreCase("Potion"))
            mc.player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
        mc.gameSettings.gammaSetting = oldGamma;
        super.onDisable();
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        switch(mode.getMode()) {
            case "Gamma":
                if(shouldAnimate.getObject() && anim < 100)
                    anim += animateSpeed.getFloat();

                mc.gameSettings.gammaSetting = anim == 0 ? 100 : anim;
                break;
            case "Potion":
                mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 1000000, 255));
                break;
        }
    }
}
