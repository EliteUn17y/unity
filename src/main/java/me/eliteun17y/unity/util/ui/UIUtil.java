package me.eliteun17y.unity.util.ui;

import me.eliteun17y.unity.util.Reference;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class UIUtil {
    private final static ResourceLocation lightPanel = new ResourceLocation(Reference.ID,"textures/clickgui/light/panel.png");
    private final static ResourceLocation darkPanel = new ResourceLocation(Reference.ID,"textures/clickgui/dark/panel.png");

    private final static ResourceLocation lightModuleButton = new ResourceLocation(Reference.ID,"textures/clickgui/light/modulebutton.png");
    private final static ResourceLocation darkModuleButton = new ResourceLocation(Reference.ID,"textures/clickgui/dark/modulebutton.png");

    private final static ResourceLocation lightSettingsPanel = new ResourceLocation(Reference.ID,"textures/clickgui/light/settingspanel.png");
    private final static ResourceLocation darkSettingsPanel = new ResourceLocation(Reference.ID,"textures/clickgui/dark/settingspanel.png");

    private final static ResourceLocation lightDropdown = new ResourceLocation(Reference.ID,"textures/clickgui/light/dropdown.png");
    private final static ResourceLocation darkDropdown = new ResourceLocation(Reference.ID,"textures/clickgui/dark/dropdown.png");

    // Settings
    private final static ResourceLocation lightTickbox = new ResourceLocation(Reference.ID,"textures/clickgui/light/settings/tickbox.png");
    private final static ResourceLocation darkTickbox = new ResourceLocation(Reference.ID,"textures/clickgui/dark/settings/tickbox.png");

    private final static ResourceLocation lightMode = new ResourceLocation(Reference.ID,"textures/clickgui/light/settings/mode.png");
    private final static ResourceLocation darkMode = new ResourceLocation(Reference.ID,"textures/clickgui/dark/settings/mode.png");

    private final static ResourceLocation lightModeExtended = new ResourceLocation(Reference.ID,"textures/clickgui/light/settings/modeextended.png");
    private final static ResourceLocation darkModeExtended = new ResourceLocation(Reference.ID,"textures/clickgui/dark/settings/modeextended.png");

    private final static ResourceLocation lightSlider = new ResourceLocation(Reference.ID,"textures/clickgui/light/settings/slider.png");
    private final static ResourceLocation darkSlider = new ResourceLocation(Reference.ID,"textures/clickgui/dark/settings/slider.png");

    private final static ResourceLocation lightSliderRail = new ResourceLocation(Reference.ID,"textures/clickgui/light/settings/sliderrail.png");
    private final static ResourceLocation darkSliderRail = new ResourceLocation(Reference.ID,"textures/clickgui/dark/settings/sliderrail.png");

    private final static Color preferredColor = new Color(99, 164, 244);


    private static boolean isDark() {
        return false;
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

    public static ResourceLocation getDropdown() {
        return isDark() ? darkDropdown : lightDropdown;
    }

    public static ResourceLocation getTickbox() {
        return !isDark() ? darkTickbox : lightTickbox;
    }

    public static ResourceLocation getMode() {
        return !isDark() ? darkMode : lightMode;
    }

    public static ResourceLocation getModeExtended() {
        return !isDark() ? darkModeExtended : lightModeExtended;
    }

    public static ResourceLocation getSliderRail() {
        return !isDark() ? darkSliderRail : lightSliderRail;
    }

    public static ResourceLocation getSlider() {
        return !isDark() ? darkSlider : lightSlider;
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
