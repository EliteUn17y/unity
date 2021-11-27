package me.eliteun17y.unity.event.impl;

import me.eliteun17y.unity.event.Event;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.widgets.Widget;

public class EventValueChange extends Event {
    public Value value;

    public Module module; // For a module value change
    public Widget widget; // For a widget value change

    public boolean isModule;
    public boolean isWidget;

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Module module) {
        this.module = module;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public boolean isModule() {
        return isModule;
    }

    public boolean isWidget() {
        return isWidget;
    }

    public EventValueChange(Value value, Module module) {
        this.value = value;
        this.module = module;

        isWidget = false;
        isModule = true;
    }

    public EventValueChange(Value value, Widget widget) {
        this.value = value;
        this.widget = widget;

        isWidget = true;
        isModule = false;
    }
}
