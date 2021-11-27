package me.eliteun17y.unity.util.ui;

import me.eliteun17y.unity.util.Reference;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class UIUtil {
    public static Color preferredColor = new Color(99, 164, 244);

    public static boolean darkMode = false;

    private static boolean isDark() {
        return darkMode;
    }

    public static Color getPreferredColor() {
        return preferredColor;
    }

    public static Color getNormalColor() {
        return isDark() ? new Color(90, 90, 90) : new Color(210, 210, 210);
    }

    public static Color getOppositeNormalColor() {
        return !isDark() ? new Color(90, 90, 90) : new Color(210, 210, 210);
    }

    public static Color getNormalColor2() {
        return isDark() ? new Color(70, 70, 70) : new Color(230, 230, 230);
    }

    public static Color getOppositeNormalColor2() {
        return !isDark() ? new Color(70, 70, 70) : new Color(230, 230, 230);
    }


    public static Color getFontColor() {
        Color light = new Color(230, 230, 230);
        Color dark = new Color(80, 80, 80);

        return isDark() ? light : dark;
    }

    public static Color getOppositeFontColor() {
        Color light = new Color(230, 230, 230);
        Color dark = new Color(80, 80, 80);

        return !isDark() ? light : dark;
    }
}
