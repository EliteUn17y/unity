package me.eliteun17y.unity.util.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.file.FileUtil;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.util.setting.impl.*;
import me.eliteun17y.unity.widgets.Widget;

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
        for(Module module : Unity.instance.moduleManager.getModules()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("toggled", module.isToggled());
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
        FileUtil.writeContent(file, parent.toString());
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

        for(Module module : Unity.instance.moduleManager.getModules()) {
            JsonObject m = modules1.getAsJsonObject(module.getName().replace(" ", "-"));
            JsonObject values = m.getAsJsonObject("values");
            if(module.isToggled() != m.get("toggled").getAsBoolean())
                module.setToggled(m.get("toggled").getAsBoolean());
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

        for(Widget widget : Unity.instance.widgetManager.getWidgets()) {
            JsonObject w = widgets1.getAsJsonObject(widget.getName().replace(" ", "-"));
            JsonObject values = w.getAsJsonObject("values");
            if(widget.isToggled() != w.get("toggled").getAsBoolean())
                widget.setToggled(w.get("toggled").getAsBoolean());
            widget.setX(w.get("x").getAsFloat());
            widget.setY(w.get("y").getAsFloat());
            for(int i = 0; i < widget.getValues().size(); i++) {
                Value value = widget.getValues().get(i);
                JsonElement v = values.get(value.getName().replace(" ", "-"));
                assert v != null;
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
