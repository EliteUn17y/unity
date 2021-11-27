package me.eliteun17y.unity.util.setting.impl;

import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.widgets.Widget;

public class NumberValue extends Value<Number> {
    public double max;
    public double min;
    public double increment;

    public NumberValue(Module module, String name, Number defaultObj, float max, float min, double increment) {
        super(name, defaultObj, module);
        this.max = max;
        this.min = min;
        this.increment = increment;
    }

    public NumberValue(Widget widget, String name, Number defaultObj, float max, float min, double increment) {
        super(name, defaultObj, widget);
        this.max = max;
        this.min = min;
        this.increment = increment;
    }

    public NumberValue(String name, Number defaultObj, float max, float min, double increment) {
        super(name, defaultObj);
        this.max = max;
        this.min = min;
        this.increment = increment;
    }

    public float getFloat() {
        return getObject().floatValue();
    }

    public double getDouble() {
        return getObject().doubleValue();
    }

    public int getInt() {
        return getObject().intValue();
    }

    public long getLong() {
        return getObject().longValue();
    }

    public short getShort() {
        return getObject().shortValue();
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getIncrement() {
        return increment;
    }
}
