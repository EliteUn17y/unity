package me.eliteun17y.unity.util.font.manager;

import me.eliteun17y.unity.util.font.CustomFontRenderer;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontManager {
    public static FontManager instance;

    public boolean mcFont;

    public FontManager getInstance() {
        return instance;
    }

    public CustomFontRenderer robotoThin;
    public CustomFontRenderer robotoLight;
    public CustomFontRenderer robotoRegular;
    public CustomFontRenderer robotoMedium;
    public CustomFontRenderer robotoBold;
    public CustomFontRenderer robotoBlack;

    public CustomFontRenderer robotoThinSmall;
    public CustomFontRenderer robotoLightSmall;
    public CustomFontRenderer robotoRegularSmall;
    public CustomFontRenderer robotoMediumSmall;
    public CustomFontRenderer robotoBoldSmall;
    public CustomFontRenderer robotoBlackSmall;

    public FontManager() {
        instance = this;
        try {
            Font f = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/fonts/roboto/Roboto-Thin.ttf"))).deriveFont(Font.PLAIN, 15.0f);
            robotoThin = new CustomFontRenderer(f, true, true);
            Font f2 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/fonts/roboto/Roboto-Light.ttf"))).deriveFont(Font.PLAIN, 15.0f);
            robotoLight = new CustomFontRenderer(f2, true, true);
            Font f3 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/fonts/roboto/Roboto-Regular.ttf"))).deriveFont(Font.PLAIN, 15.0f);
            robotoRegular = new CustomFontRenderer(f3, true, true);
            Font f4 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/fonts/roboto/Roboto-Medium.ttf"))).deriveFont(Font.PLAIN, 15.0f);
            robotoMedium = new CustomFontRenderer(f4, true, true);
            Font f5 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/fonts/roboto/Roboto-Bold.ttf"))).deriveFont(Font.PLAIN, 15.0f);
            robotoBold = new CustomFontRenderer(f5, true, true);
            Font f6 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/fonts/roboto/Roboto-Black.ttf"))).deriveFont(Font.PLAIN, 15.0f);
            robotoBlack = new CustomFontRenderer(f6, true, true);

            Font f7 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/fonts/roboto/Roboto-Thin.ttf"))).deriveFont(Font.PLAIN, 10.0f);
            robotoThinSmall = new CustomFontRenderer(f7, true, true);
            Font f8 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/fonts/roboto/Roboto-Light.ttf"))).deriveFont(Font.PLAIN, 10.0f);
            robotoLightSmall = new CustomFontRenderer(f8, true, true);
            Font f9 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/fonts/roboto/Roboto-Regular.ttf"))).deriveFont(Font.PLAIN, 10.0f);
            robotoRegularSmall = new CustomFontRenderer(f9, true, true);
            Font f10 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/fonts/roboto/Roboto-Medium.ttf"))).deriveFont(Font.PLAIN, 10.0f);
            robotoMediumSmall = new CustomFontRenderer(f10, true, true);
            Font f11 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/fonts/roboto/Roboto-Bold.ttf"))).deriveFont(Font.PLAIN, 10.0f);
            robotoBoldSmall = new CustomFontRenderer(f11, true, true);
            Font f12 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/fonts/roboto/Roboto-Black.ttf"))).deriveFont(Font.PLAIN, 10.0f);
            robotoBlackSmall = new CustomFontRenderer(f12, true, true);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
