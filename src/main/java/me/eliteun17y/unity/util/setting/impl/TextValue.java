package me.eliteun17y.unity.util.setting.impl;

import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.widgets.Widget;

public class TextValue extends Value<String> {
    public TextValue(Module module, String name, String defaultObj) {
        super(name, defaultObj, module);
    }

    public TextValue(Widget widget, String name, String defaultObj) {
        super(name, defaultObj, widget);
    }

    public TextValue(String name, String defaultObj) {
        super(name, defaultObj);
    }
}
