package me.eliteun17y.unity.command.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.command.Command;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.chat.ChatUtil;
import me.eliteun17y.unity.util.config.ConfigUtil;
import me.eliteun17y.unity.util.file.FileUtil;
import me.eliteun17y.unity.util.setting.Value;
import me.eliteun17y.unity.util.setting.impl.*;
import me.eliteun17y.unity.widgets.Widget;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.File;

public class Config extends Command {
    public Config() {
        super("Config", "Allows you to save and load configs.");
    }

    @Override
    public void execute(String[] args) {
        if(args.length < 2) {
            ChatUtil.sendClientMessage("Incorrect syntax. Correct syntax: .config [save / remove / update / load] [name]");
            return;
        }

        File file = new File(FileUtil.configs.getPath() + "/" + args[1] + ".json");

        switch(args[0]) {
            case "save":
                if(ConfigUtil.save(file))
                    ChatUtil.sendClientMessage("Successfully saved config.");
                else
                    ChatUtil.sendClientMessage("Failed to saved config.");
                break;
            case "load":
                if(ConfigUtil.load(file))
                    ChatUtil.sendClientMessage("Successfully loaded config.");
                else
                    ChatUtil.sendClientMessage("Failed to load config.");
                break;
            case "remove":
                if(ConfigUtil.remove(file))
                    ChatUtil.sendClientMessage("Successfully removed config.");
                else
                    ChatUtil.sendClientMessage("Failed to remove config.");
                break;
            case "update":
                if(ConfigUtil.update(file))
                    ChatUtil.sendClientMessage("Successfully updated config.");
                else
                    ChatUtil.sendClientMessage("Failed to update config.");
                break;
        }
    }
}
