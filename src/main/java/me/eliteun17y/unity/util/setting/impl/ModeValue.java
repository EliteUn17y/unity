package me.eliteun17y.unity.util.setting.impl;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.impl.EventValueChange;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.Mode;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.widgets.Widget;

import java.util.ArrayList;
import java.util.Arrays;

public class ModeValue extends Value<Mode> {
    public ModeValue(Module module, String name, String defaultMode, String... modes) {
        super(name, new Mode(defaultMode, Arrays.asList(modes)), module);
    }
    public ModeValue(Widget widget, String name, String defaultMode, String... modes) {
        super(name, new Mode(defaultMode, Arrays.asList(modes)), widget);
    }

    public ModeValue(Module module, String name, Mode defaultObj) {
        super(name, defaultObj, module);
    }

    public ModeValue(Widget widget, String name, Mode defaultObj) {
        super(name, defaultObj, widget);
    }

    public ModeValue(String name, String defaultMode, String... modes) {
        super(name, new Mode(defaultMode, Arrays.asList(modes)));
    }

    public String getMode() {
        return getObject().getMode();
    }

    public int getModeIndex() {
        return getObject().getModeIndex();
    }

    public ArrayList<String> getModes() {
        return getObject().getModes();
    }

    public void setMode(String mode) {
        getObject().setMode(mode);

        EventValueChange e = new EventValueChange(this, module);
        Unity.EVENT_BUS.post(e);
    }

    public void setMode(int index) {
        getObject().setMode(getObject().getModes().get(index));

        EventValueChange e = new EventValueChange(this, module);
        Unity.EVENT_BUS.post(e);
    }
}
