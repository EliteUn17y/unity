package me.eliteun17y.unity.util.setting.impl;

import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.widgets.Widget;

public class BooleanValue extends Value<Boolean> {
    public BooleanValue(Module module, String name, Boolean defaultObj) {
        super(name, defaultObj, module);
    }

    public BooleanValue(Widget widget, String name, Boolean defaultObj) {
        super(name, defaultObj, widget);
    }

    public BooleanValue(String name, Boolean defaultObj) {
        super(name, defaultObj);
    }
}
