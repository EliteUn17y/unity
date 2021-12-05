package me.eliteun17y.unity.mixin.impl;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.ui.authlogin.AuthLogin;
import me.eliteun17y.unity.ui.menu.MainMenu;
import me.eliteun17y.unity.util.Reference;
import me.eliteun17y.unity.util.file.FileUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(method = "createDisplay", at=@At("RETURN"))
    public void createDisplay(CallbackInfo callbackInfo) {
        Display.setTitle(Reference.NAME + " " + Reference.VERSION);
    }

    @Inject(method = "displayGuiScreen", at = @At("HEAD"), cancellable = true)
    public void displayGuiScreen(GuiScreen guiScreen, CallbackInfo callbackInfo) {
        if(guiScreen instanceof GuiMainMenu || (guiScreen == null && Minecraft.getMinecraft().world == null)) {
            if((!FileUtil.getContent(FileUtil.auth).contains("username")) || Unity.instance.user == null || Objects.equals(Unity.instance.user.username, ""))
                Minecraft.getMinecraft().displayGuiScreen(new AuthLogin());
            else
                Minecraft.getMinecraft().displayGuiScreen(new MainMenu());
            callbackInfo.cancel();
        }
    }
}