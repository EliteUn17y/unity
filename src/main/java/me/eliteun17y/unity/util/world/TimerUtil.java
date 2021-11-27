package me.eliteun17y.unity.util.world;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class TimerUtil {
    private static float speed = 1;

    public static void setSpeed(float s) {
        speed = s;
        ObfuscationReflectionHelper.setPrivateValue(Timer.class, ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_71428_T"), 1000 / (speed * 20), "field_194149_e"); // field_194149_e = tickLength
    }

    public static float getSpeed() {
        return speed;
    }
}
