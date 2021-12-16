package me.eliteun17y.unity.module;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventValueChange;
import me.eliteun17y.unity.module.impl.combat.AutoCrystal;
import me.eliteun17y.unity.module.impl.combat.KillAura;
import me.eliteun17y.unity.module.impl.exploits.Disabler;
import me.eliteun17y.unity.module.impl.exploits.PacketFlight;
import me.eliteun17y.unity.module.impl.exploits.QuickBreak;
import me.eliteun17y.unity.module.impl.hidden.HUDEditor;
import me.eliteun17y.unity.module.impl.hidden.Windows;
import me.eliteun17y.unity.module.impl.misc.MiddleClickFriend;
import me.eliteun17y.unity.module.impl.movement.*;
import me.eliteun17y.unity.module.impl.player.*;
import me.eliteun17y.unity.module.impl.render.*;
import me.eliteun17y.unity.util.math.Vector3;
import me.eliteun17y.unity.util.math.Vector5;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModuleManager {
    public ArrayList<Module> modules;

    public ModuleManager() {
        Unity.EVENT_BUS.register(this);
        modules = new ArrayList<>();

        // Combat

        modules.add(new AutoCrystal());
        modules.add(new KillAura());

        // Exploits

        modules.add(new Disabler());
        modules.add(new PacketFlight());
        modules.add(new QuickBreak());

        // Misc

        modules.add(new MiddleClickFriend());

        // Movement

        modules.add(new AntiKnockback());
        modules.add(new BetterBoat());
        modules.add(new BoatFlight());
        modules.add(new Catch());
        modules.add(new ElytraFlight());
        modules.add(new EntitySpeed());
        modules.add(new Flight());
        modules.add(new IronBoots());
        modules.add(new LongJump());
        modules.add(new NoSlowdown());
        modules.add(new Speed());
        modules.add(new Sprint());
        modules.add(new Step());

        // Render

        modules.add(new ClickGUI());
        modules.add(new ESP());
        modules.add(new Fullbright());
        modules.add(new HUD());
        modules.add(new Nametags());
        modules.add(new Time());
        modules.add(new Tracers());
        modules.add(new Weather());

        // Player

        modules.add(new AntiCrash());
        modules.add(new Burrow());
        modules.add(new FakePlayer());
        modules.add(new FastPlace());
        modules.add(new NoFall());
        modules.add(new Offhand());
        modules.add(new Scaffold());
        modules.add(new Surround());

        // Hidden

        modules.add(new HUDEditor());
        modules.add(new Windows());
    }

    public Module getModule(String name) {
        for(Module m : modules)
            if(m.getName().equalsIgnoreCase(name))
                return m;
        return null;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public ArrayList<Module> getModulesByCategory(Category category) {
        ArrayList<Module> modules = new ArrayList<>();
        for(Module m : this.modules)
            if(m.getCategory().equals(category))
                modules.add(m);
        return modules;
    }

    @Subscribe
    public void onValueChange(EventValueChange event) {
        if(event.isWidget()) return;
        if(event.getModule() == null) return;
        if(event.getModule().valueToAddOnChangeOfValue.isEmpty()) return;

        Module module = event.getModule();
        Object[] moduleVector3s = (module.valueToAddOnChangeOfValue.stream().filter(vector3 -> vector3.getA() == event.getValue()).toArray());
        boolean shouldRecalculate = false;
        for(Object modVector3 : moduleVector3s) {
            Vector3 moduleVector3 = (Vector3) modVector3;
            if(module.getValues().contains((Value) moduleVector3.getB())) {
                Object[] moduleVector3s1 = (module.valueToAddOnChangeOfValue.stream().filter(vector3 -> vector3.getA() == moduleVector3.getB()).toArray());
                for(Object modVector31 : moduleVector3s1) {
                    Vector3 moduleVector31 = (Vector3) modVector31;
                    module.getValues().remove((Value) moduleVector31.getB());
                }
                module.getValues().remove((Value) moduleVector3.getB());
                shouldRecalculate = true;
            }

            if(event.getValue() instanceof NumberValue) {
                if(((BigDecimal) event.getValue().getObject()).doubleValue() == ((double) moduleVector3.getC())) {
                    Value value = (Value) moduleVector3.getB();
                    value.module = module;
                    module.addValue(value);
                    shouldRecalculate = true;
                }
            }else if(event.getValue() instanceof ModeValue) {
                if(((ModeValue) event.getValue()).getMode() == moduleVector3.getC()) {
                    Value value = (Value) moduleVector3.getB();
                    value.module = module;
                    module.addValue(value);
                    shouldRecalculate = true;
                }
            }else {
                if(event.getValue().getObject() == moduleVector3.getC()) {
                    Value value = (Value) moduleVector3.getB();
                    value.module = module;
                    module.addValue(value);
                    shouldRecalculate = true;
                }
            }
        }

        Object[] moduleVector5s = (module.valueToAddOnChangeOfValueAndOtherValue.stream().filter(vector5 -> vector5.getA() == event.getValue() || vector5.getB() == event.getValue()).toArray());
        for(Object modVector5 : moduleVector5s) {

            boolean aPassed = false;
            boolean bPassed = false;
            Vector5 moduleVector5 = (Vector5) modVector5;
            //valueToAddOnChangeOfValueAndOtherValue.add(new Vector5(value, value2, valueToAdd, object, object2));
            if(moduleVector5.getA() instanceof NumberValue) {
                if(((NumberValue) moduleVector5.getA()).getObject().doubleValue() == (double) moduleVector5.getD()) {
                    aPassed = true;
                }
            }else if(moduleVector5.getA() instanceof ModeValue) {
                if(Objects.equals(((ModeValue) moduleVector5.getA()).getMode(), moduleVector5.getD())) {
                    aPassed = true;
                }
            }else {
                if(((Value) moduleVector5.getA()).getObject() == moduleVector5.getD()) {
                    aPassed = true;
                }
            }

            if(moduleVector5.getB() instanceof NumberValue) {
                if(((NumberValue) moduleVector5.getB()).getObject().doubleValue() == (double) moduleVector5.getE()) {
                    bPassed = true;
                }
            }else if(moduleVector5.getB() instanceof ModeValue) {
                if(Objects.equals(((ModeValue) moduleVector5.getB()).getMode(), moduleVector5.getE())) {
                    bPassed = true;
                }
            }else {
                if(((Value) moduleVector5.getB()).getObject() == moduleVector5.getE()) {
                    bPassed = true;
                }
            }

            if(aPassed && bPassed) {
                Value value = (Value) moduleVector5.getC();
                value.module = module;
                module.addValue(value);
                shouldRecalculate = true;
            }else{
                module.getValues().remove((Value) moduleVector5.getC());
                shouldRecalculate = true;
            }
        }

        if(shouldRecalculate && me.eliteun17y.unity.ui.clickgui.ClickGUI.getPanel() != null)
            me.eliteun17y.unity.ui.clickgui.ClickGUI.getPanel().calculateSettings();
    }
}
