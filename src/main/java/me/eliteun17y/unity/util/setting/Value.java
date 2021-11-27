package me.eliteun17y.unity.util.setting;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.impl.EventValueChange;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.widgets.Widget;

public class Value<T> {
    public String name;
    public T obj;
    public T defaultObj;
    public Module module;
    public Widget widget;

    public Value(String name, T defaultObj, Module module) {
        this.name = name;
        this.defaultObj = defaultObj;
        this.obj = defaultObj;
        this.module = module;

        module.addValue(this); // Adds the value to the module so we don't have to deal with this in EVERY module class and it just does it
    }

    public Value(String name, T defaultObj, Widget widget) {
        this.name = name;
        this.defaultObj = defaultObj;
        this.obj = defaultObj;
        this.widget = widget;

        widget.addValue(this); // Adds the value to the widget so we don't have to deal with this in EVERY widget class and it just does it
    }

    public Value(String name, T defaultObj) {
        this.name = name;
        this.defaultObj = defaultObj;
        this.obj = defaultObj;
    }

    public String getName() {
        return this.name;
    }

    public T getObject() {
        return this.obj;
    }

    public void setObject(T object) {
        this.obj = object;
        EventValueChange e;
        if(module != null)
            e = new EventValueChange(this, module);
        else
            e = new EventValueChange(this, widget);
        Unity.EVENT_BUS.post(e);
    }

    public T getDefaultObject() {
        return this.obj;
    }
}
