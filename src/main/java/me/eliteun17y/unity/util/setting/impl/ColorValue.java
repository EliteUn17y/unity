package me.eliteun17y.unity.util.setting.impl;

import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.widgets.Widget;

import java.awt.*;

public class ColorValue extends Value<Color> {
    public ColorValue(Module module, String name, Color defaultObj) {
        super(name, defaultObj, module);
    }
    public ColorValue(Widget widget, String name, Color defaultObj) {
        super(name, defaultObj, widget);
    }
    public ColorValue(String name, Color defaultObj) {
        super(name, defaultObj);
    }
}
