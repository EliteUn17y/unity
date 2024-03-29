package me.eliteun17y.unity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.eliteun17y.unity.command.CommandManager;
import me.eliteun17y.unity.event.EventBus;
import me.eliteun17y.unity.event.EventProcessor;
import me.eliteun17y.unity.managers.alts.Alt;
import me.eliteun17y.unity.managers.alts.AltManager;
import me.eliteun17y.unity.managers.friend.FriendManager;
import me.eliteun17y.unity.module.ModuleManager;
import me.eliteun17y.unity.plugin.PluginClassLoader;
import me.eliteun17y.unity.plugin.PluginLoader;
import me.eliteun17y.unity.proxy.CommonProxy;
import me.eliteun17y.unity.ui.clickgui.ClickGUI;
import me.eliteun17y.unity.util.Reference;
import me.eliteun17y.unity.util.config.ConfigUtil;
import me.eliteun17y.unity.util.file.FileUtil;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.util.world.TPSUtil;
import me.eliteun17y.unity.widgets.WidgetManager;
import me.eliteun17y.unity.window.WindowManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION)
public class Unity {
    @Instance
    public static Unity instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final EventBus EVENT_BUS = new EventBus();

    // Managers

    public CommandManager commandManager;
    public WindowManager windowManager;
    public ModuleManager moduleManager;
    public WidgetManager widgetManager;
    public FriendManager friendManager;
    public AltManager altManager;

    public boolean loaded;
    public boolean firstRun;

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        loaded = false;
        MinecraftForge.EVENT_BUS.register(instance);
        EVENT_BUS.register(this);

        new FontManager();
        new FileUtil();
        new TPSUtil();

        ConfigUtil.loadUI(new File(FileUtil.unity.getPath() + "/temp.json"));

        moduleManager = new ModuleManager();
        windowManager = new WindowManager();
        commandManager = new CommandManager();
        widgetManager = new WidgetManager();
        friendManager = new FriendManager();
        altManager = new AltManager();

        new EventProcessor();
    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event) {

    }
}
