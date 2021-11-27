package me.eliteun17y.unity.mixin.impl;

import me.eliteun17y.unity.ui.menu.MainMenu;
import me.eliteun17y.unity.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(method = "createDisplay", at=@At("RETURN"))
    public void createDisplay(CallbackInfo callbackInfo) {
        Display.setTitle(Reference.NAME + " " + Reference.VERSION);
    }

    @Inject(method = "displayGuiScreen", at = @At("HEAD"), cancellable = true)
    public void displayGuiScreen(GuiScreen guiScreen, CallbackInfo callbackInfo) {
        if(guiScreen instanceof GuiMainMenu || (guiScreen == null && Minecraft.getMinecraft().world == null)) {
            Minecraft.getMinecraft().displayGuiScreen(new MainMenu());
            callbackInfo.cancel();
        }
    }
}