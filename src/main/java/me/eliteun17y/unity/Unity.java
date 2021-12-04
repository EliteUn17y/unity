package me.eliteun17y.unity;

import me.eliteun17y.unity.auth.AuthenticatedUser;
import me.eliteun17y.unity.auth.Authenticator;
import me.eliteun17y.unity.command.CommandManager;
import me.eliteun17y.unity.event.EventBus;
import me.eliteun17y.unity.event.EventProcessor;
import me.eliteun17y.unity.managers.alts.Alt;
import me.eliteun17y.unity.managers.alts.AltManager;
import me.eliteun17y.unity.managers.friend.FriendManager;
import me.eliteun17y.unity.module.ModuleManager;
import me.eliteun17y.unity.proxy.CommonProxy;
import me.eliteun17y.unity.ui.clickgui.ClickGUI;
import me.eliteun17y.unity.util.Reference;
import me.eliteun17y.unity.util.font.manager.FontManager;
import me.eliteun17y.unity.widgets.WidgetManager;
import me.eliteun17y.unity.window.WindowManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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

    public AuthenticatedUser user;

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(instance);
        EVENT_BUS.register(this);

        new FontManager();

        user = Authenticator.auth("test", "test2", "demohwid");
        if(Objects.equals(user.getUsername(), ""))
            System.out.println("failed antipiracy check");

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
