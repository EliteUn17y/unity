package me.eliteun17y.unity.mixin.impl;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.ui.menu.MainMenu;
import me.eliteun17y.unity.util.Reference;
import me.eliteun17y.unity.util.config.ConfigUtil;
import me.eliteun17y.unity.util.file.FileUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
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
            callbackInfo.cancel();
            Minecraft.getMinecraft().displayGuiScreen(new MainMenu());
        }
    }

    @Inject(method = "shutdown", at = @At("HEAD"), cancellable = true)
    public void shutdown(CallbackInfo callbackInfo) {
        ConfigUtil.remove(new File(FileUtil.unity.getPath() + "/temp.json"));
        ConfigUtil.save(new File(FileUtil.unity.getPath() + "/temp.json"));
    }
}