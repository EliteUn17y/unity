package me.eliteun17y.unity.util.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.file.FileUtil;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.util.setting.impl.*;
import me.eliteun17y.unity.util.ui.UIUtil;
import me.eliteun17y.unity.widgets.Widget;
import net.minecraft.client.renderer.entity.RenderLivingBase;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ConfigUtil {
    public static boolean save(File file) {
        if(file.exists()) return false;
        try {
            file.createNewFile();
        } catch (IOException e) {
            return false;
        }

        JsonObject parent = new JsonObject();
        JsonObject modules = new JsonObject();
        JsonObject widgets = new JsonObject();
        JsonObject ui = new JsonObject();
        ui.addProperty("darkMode", UIUtil.darkMode);
        ui.addProperty("preferredColor", UIUtil.getPreferredColor().getRGB());

        for(Module module : Unity.instance.moduleManager.getModules()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("toggled", module.isToggled());
            obj.addProperty("bind", module.getKey());
            JsonObject values = new JsonObject();
            for(Value value : module.getValues()) {
                if(value instanceof BooleanValue) {
                    values.addProperty(value.getName().replace(" ", "-"), ((BooleanValue) value).getObject());
                }
                if(value instanceof ColorValue) {
                    values.addProperty(value.getName().replace(" ", "-"), ((Color) value.getObject()).getRGB());
                }
                if(value instanceof ModeValue) {
                    values.addProperty(value.getName().replace(" ", "-"), ((ModeValue) value).getMode());
                }
                if(value instanceof NumberValue) {
                    values.addProperty(value.getName().replace(" ", "-"), ((NumberValue) value).getObject());
                }
                if(value instanceof TextValue) {
                    values.addProperty(value.getName().replace(" ", "-"), ((TextValue) value).getObject());
                }
            }
            obj.add("values", values);
            modules.add(module.getName().replace(" ", "-"), obj);
        }

        for(Widget widget : Unity.instance.widgetManager.getWidgets()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("toggled", widget.isToggled());
            obj.addProperty("x", widget.getX());
            obj.addProperty("y", widget.getY());
            obj.addProperty("scale", widget.getScale());
            JsonObject values = new JsonObject();
            for(Value value : widget.getValues()) {
                if(value instanceof BooleanValue) {
                    values.addProperty(value.getName().replace(" ", "-"), ((BooleanValue) value).getObject());
                }
                if(value instanceof ColorValue) {
                    values.addProperty(value.getName().replace(" ", "-"), ((Color) value.getObject()).getRGB());
                }
                if(value instanceof ModeValue) {
                    values.addProperty(value.getName().replace(" ", "-"), ((ModeValue) value).getMode());
                }
                if(value instanceof NumberValue) {
                    values.addProperty(value.getName().replace(" ", "-"), ((NumberValue) value).getObject());
                }
                if(value instanceof TextValue) {
                    values.addProperty(value.getName().replace(" ", "-"), ((TextValue) value).getObject());
                }
            }
            obj.add("values", values);
            widgets.add(widget.getName().replace(" ", "-"), obj);
        }

        parent.add("modules", modules);
        parent.add("widgets", widgets);
        parent.add("ui", ui);
        FileUtil.writeContent(file, parent.toString());
        return true;
    }

    public static boolean loadUI(File file) {
        if(!file.exists()) {
            return false;
        }

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(FileUtil.getContent(file));
        JsonObject object = jsonElement.getAsJsonObject();
        JsonObject ui1 = object.getAsJsonObject("ui");
        UIUtil.darkMode = ui1.get("darkMode").getAsBoolean();
        float r1 = ((ui1.get("preferredColor").getAsInt() >> 16) & 0xff);
        float g1 = ((ui1.get("preferredColor").getAsInt( ) >>  8) & 0xff);
        float b1 = ((ui1.get("preferredColor").getAsInt()          ) & 0xff);
        float a1 = ((ui1.get("preferredColor").getAsInt() >> 24) & 0xff);
        UIUtil.preferredColor = new Color(r1 / 255, g1 / 255, b1 / 255, a1 / 255);
        return true;
    }

    public static boolean load(File file) {
        if(!file.exists()) {
            return false;
        }

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(FileUtil.getContent(file));
        JsonObject object = jsonElement.getAsJsonObject();

        JsonObject modules1 = object.getAsJsonObject("modules");
        JsonObject widgets1 = object.getAsJsonObject("widgets");

        for(int i1 = 0; i1 < Unity.instance.moduleManager.getModules().size(); i1++) {
            Module module = Unity.instance.moduleManager.getModules().get(i1);
            JsonObject m = modules1.getAsJsonObject(module.getName().replace(" ", "-"));
            if(m == null) continue;
            JsonObject values = m.getAsJsonObject("values");
            if(module.isToggled() != m.get("toggled").getAsBoolean())
                module.setToggled(m.get("toggled").getAsBoolean());
            module.key = m.get("bind").getAsInt();
            for(int i = 0; i < module.getValues().size(); i++) {
                Value value = module.getValues().get(i);
                JsonElement v = values.get(value.getName().replace(" ", "-"));
                if(v == null) continue;
                if(value instanceof BooleanValue) {
                    value.setObject(v.getAsBoolean());
                }
                if(value instanceof ColorValue) {
                    float r = ((v.getAsInt() >> 16) & 0xff);
                    float g = ((v.getAsInt() >>  8) & 0xff);
                    float b = ((v.getAsInt()      ) & 0xff);
                    float a = ((v.getAsInt() >> 24) & 0xff);
                    ((ColorValue) value).setObject(new Color((int) r, (int) g, (int) b, (int) a));
                }
                if(value instanceof ModeValue) {
                    ((ModeValue) value).setMode(v.getAsString());
                }
                if(value instanceof NumberValue) {
                    value.setObject(v.getAsNumber());
                }
                if(value instanceof TextValue) {
                    ((TextValue) value).setObject(v.getAsString());
                }
            }
        }

        for(int i1 = 0; i1 < Unity.instance.widgetManager.getWidgets().size(); i1++) {
            Widget widget = Unity.instance.widgetManager.getWidgets().get(i1);
            JsonObject w = widgets1.getAsJsonObject(widget.getName().replace(" ", "-"));
            JsonObject values = w.getAsJsonObject("values");
            if(widget.isToggled() != w.get("toggled").getAsBoolean())
                widget.setToggled(w.get("toggled").getAsBoolean());
            widget.setX(w.get("x").getAsFloat());
            widget.setY(w.get("y").getAsFloat());
            widget.setScale(w.get("scale").getAsFloat());
            for(int i = 0; i < widget.getValues().size(); i++) {
                Value value = widget.getValues().get(i);
                JsonElement v = values.get(value.getName().replace(" ", "-"));
                if(value instanceof BooleanValue) {
                    System.out.println("Name: " + widget.getName() + " Value Name: " + value.getName() + " Value: " + v.getAsBoolean());
                    ((BooleanValue) value).setObject(v.getAsBoolean());
                }
                if(value instanceof ColorValue) {
                    System.out.println("Name: " + widget.getName() + " Value Name: " + value.getName());
                    float r = ((v.getAsInt() >> 16) & 0xff);
                    float g = ((v.getAsInt() >>  8) & 0xff);
                    float b = ((v.getAsInt()      ) & 0xff);
                    float a = ((v.getAsInt() >> 24) & 0xff);
                    ((ColorValue) value).setObject(new Color((int) r, (int) g, (int) b, (int) a));
                }
                if(value instanceof ModeValue) {
                    System.out.println("Name: " + widget.getName() + " Value Name: " + value.getName());
                    ((ModeValue) value).setMode(v.getAsString());
                }
                if(value instanceof NumberValue) {
                    System.out.println("Name: " + widget.getName() + " Value Name: " + value.getName());
                    ((NumberValue) value).setObject(v.getAsNumber());
                }
                if(value instanceof TextValue) {
                    System.out.println("Name: " + widget.getName() + " Value Name: " + value.getName());
                    ((TextValue) value).setObject(v.getAsString());
                }
            }
        }
        return true;
    }

    public static boolean remove(File file) {
        if(!file.exists()) {
            return false;
        }
        return file.delete();
    }

    public static boolean update(File file) {
        if(!file.exists()) return false;
        remove(file);

        return save(file);
    }
}
