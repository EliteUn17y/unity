package me.eliteun17y.unity.widgets;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.impl.EventValueChange;
import me.eliteun17y.unity.util.math.Vector3;
import me.eliteun17y.unity.util.setting.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import java.util.ArrayList;

public class Widget {
    public float x;
    public float y;
    public float width;

    public float height;

    public String name;
    public String description;
    public boolean toggled;
    public boolean scaling;

    public boolean isScaling() {
        return scaling;
    }

    public void setScaling(boolean scaling) {
        this.scaling = scaling;
    }

    public float scale;

    public ArrayList<Value> values = new ArrayList<>();

    public ArrayList<Value> getValues() {
        return values;
    }

    public void setValues(ArrayList<Value> values) {
        this.values = values;
    }

    public ArrayList<Vector3> getValueToAddOnChangeOfValue() {
        return valueToAddOnChangeOfValue;
    }

    public void setValueToAddOnChangeOfValue(ArrayList<Vector3> valueToAddOnChangeOfValue) {
        this.valueToAddOnChangeOfValue = valueToAddOnChangeOfValue;
    }

    public ArrayList<Vector3> valueToAddOnChangeOfValue = new ArrayList<>();

    protected Minecraft mc = Minecraft.getMinecraft();

    public Widget(String name, String description, float x, float y, float width, float height) {
        this.name = name;
        this.description = description;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.toggled = false;
        scale = 1;
        scaling = false;
    }

    public void onEnable() {
        Unity.EVENT_BUS.register(this);
    }

    public void onDisable() {
        Unity.EVENT_BUS.unregister(this);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
        if(toggled)
            onEnable();
        else
            onDisable();
    }

    public void toggle() {
        this.toggled = !toggled;
        if(toggled)
            onEnable();
        else
            onDisable();
    }

    public void scale() {
        GlStateManager.scale(scale, scale, scale);
    }

    public void unscale() {
        GlStateManager.scale(1 / scale, 1 / scale, 1 / scale);
    }

    public void addValue(Value value) {
        this.values.add(value);
    }

    public void addValueOnValueChange(Value value, Value valueToAdd, Object object) {
        valueToAddOnChangeOfValue.add(new Vector3(value, valueToAdd, object));
        EventValueChange event = new EventValueChange(value, this);
        Unity.EVENT_BUS.post(event);
    }
}