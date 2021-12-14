package me.eliteun17y.unity.widgets;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventValueChange;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.math.Vector3;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import me.eliteun17y.unity.widgets.impl.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.math.BigDecimal;
import java.util.ArrayList;

public class WidgetManager {
    public ArrayList<Widget> widgets;

    public WidgetManager() {
        Unity.EVENT_BUS.register(this);
        widgets = new ArrayList<>();

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        widgets.add(new Watermark());
        widgets.add(new Greeting());
        widgets.add(new ModuleList());
        widgets.add(new Speed());
        widgets.add(new Player());
    }

    public Widget getWidget(String name) {
        for(Widget w : widgets)
            if(w.getName().equalsIgnoreCase(name))
                return w;
        return null;
    }

    public ArrayList<Widget> getWidgets() {
        return widgets;
    }

    @Subscribe
    public void onValueChange(EventValueChange event) {
        if(event.isModule()) return;
        if(event.getWidget() == null) return;
        if(event.getWidget().valueToAddOnChangeOfValue.isEmpty()) return;

        Widget widget = event.getWidget();
        Object[] moduleVector3s = (widget.valueToAddOnChangeOfValue.stream().filter(vector3 -> vector3.getA() == event.getValue()).toArray());
        boolean shouldRecalculate = false;
        for(Object modVector3 : moduleVector3s) {
            Vector3 moduleVector3 = (Vector3) modVector3;
            if(widget.getValues().contains((Value) moduleVector3.getB())) {
                widget.getValues().remove((Value) moduleVector3.getB());
                shouldRecalculate = true;
            }

            if(event.getValue() instanceof NumberValue) {
                if(((BigDecimal) event.getValue().getObject()).doubleValue() == ((double) moduleVector3.getC())) {
                    widget.addValue((Value) moduleVector3.getB());
                    shouldRecalculate = true;
                }
            }else if(event.getValue() instanceof ModeValue) {
                if(((ModeValue) event.getValue()).getMode() == moduleVector3.getC()) {
                    widget.addValue((Value) moduleVector3.getB());
                    shouldRecalculate = true;
                }
            }else {
                if(event.getValue().getObject() == moduleVector3.getC()) {
                    widget.addValue((Value) moduleVector3.getB());
                    shouldRecalculate = true;
                }
            }
        }

        if(shouldRecalculate && me.eliteun17y.unity.ui.hudeditor.HUDEditor.getPanel() != null)
            me.eliteun17y.unity.ui.hudeditor.HUDEditor.getPanel().calculateSettings();
    }
}
