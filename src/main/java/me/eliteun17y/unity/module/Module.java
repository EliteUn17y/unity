package me.eliteun17y.unity.module;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.impl.EventValueChange;
import me.eliteun17y.unity.util.math.Vector3;
import me.eliteun17y.unity.util.math.Vector5;
import me.eliteun17y.unity.util.setting.Value;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class Module {
    public String name;
    public String displayName;
    public String description;
    public Category category;
    public int key;
    public int priority;
    public boolean toggled;

    public float animationValue;
    public boolean animationPlaying;

    public ArrayList<Value> values = new ArrayList<>();
    public ArrayList<Vector3> valueToAddOnChangeOfValue = new ArrayList<>();
    public ArrayList<Vector5> valueToAddOnChangeOfValueAndOtherValue = new ArrayList<>();

    protected Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String description, Category category, int key) {
        this.name = name;
        this.displayName = name;
        this.description = description;
        this.category = category;
        this.key = key;
        this.priority = 0;
        this.toggled = false;
    }

    public Module(String name, String description, Category category, int key, int priority) {
        this.name = name;
        this.displayName = name;
        this.description = description;
        this.category = category;
        this.key = key;
        this.priority = priority;
        this.toggled = false;
    }

    public void onEnable() {
        Unity.EVENT_BUS.register(this, priority);
        animationPlaying = true;
        animationValue = 0.1f;
    }
    public void onDisable() {
        Unity.EVENT_BUS.unregister(this);
        animationPlaying = true;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
        if(toggled)
            onEnable();
        else
            onDisable();
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public int getKey() {
        return key;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void toggle() {
        toggled = !toggled;
        if(toggled)
            onEnable();
        else
            onDisable();
    }

    public void addValue(Value value) {
        this.values.add(value);
        EventValueChange event = new EventValueChange(value, value.module);
        Unity.EVENT_BUS.post(event);
    }

    public ArrayList<Value> getValues() {
        return values;
    }

    public void addValueOnValueChange(Value value, Value valueToAdd, Object object) {
        valueToAddOnChangeOfValue.add(new Vector3(value, valueToAdd, object));
        EventValueChange event = new EventValueChange(value, value.module);
        Unity.EVENT_BUS.post(event);
    }

    public void addValueOnValueChangeAndValueChange(Value value, Value value2, Value valueToAdd, Object object, Object object2) {
        valueToAddOnChangeOfValueAndOtherValue.add(new Vector5(value, value2, valueToAdd, object, object2));
        EventValueChange event = new EventValueChange(value, value.module);
        Unity.EVENT_BUS.post(event);
    }
}
