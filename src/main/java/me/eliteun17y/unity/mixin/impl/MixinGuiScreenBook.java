package me.eliteun17y.unity.mixin.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreenBook.class)
public class MixinGuiScreenBook {
    public GuiButton copyPage;
    public GuiButton pastePage;
    public NBTTagString copiedPage = new NBTTagString("");

    @Shadow
    private NBTTagList bookPages;

    @Shadow
    private int currPage;

    @Inject(method = "initGui", at = @At("HEAD"))
    public void constructor(CallbackInfo callbackInfo) {
        copyPage = new GuiButton(10, 4, 4, 98, 20, "Copy Page");
        pastePage = new GuiButton(11, 4, 28, 98, 20, "Paste Page");
    }

    @Inject(method = "drawScreen", at = @At("HEAD"))
    public void draw(int mouseX, int mouseY, float partialTicks, CallbackInfo callbackInfo) {
        copyPage.drawButton(Minecraft.getMinecraft(), mouseX, mouseY, partialTicks);
        pastePage.drawButton(Minecraft.getMinecraft(), mouseX, mouseY, partialTicks);
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"))
    public void draw(GuiButton button, CallbackInfo callbackInfo) {
        if(button.id == copyPage.id) {
            System.out.println((NBTTagString) bookPages.get(1));
            copiedPage = (NBTTagString) bookPages.get(currPage);
        }
        if(button.id == pastePage.id) {
            bookPages.set(currPage, copiedPage);
        }
    }
}