package me.eliteun17y.unity.mixin.impl;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreenBook.class)
public class MixinGuiScreenBook extends GuiScreen {
    public GuiButton copyPage;
    public GuiButton pastePage;
    public GuiButton removePage;

    public NBTTagString copiedPage = new NBTTagString("");

    @Shadow
    private NBTTagList bookPages;

    @Shadow
    private int currPage;

    @Shadow private int bookTotalPages;

    @Shadow @Final private boolean bookIsUnsigned;

    @Inject(method = "initGui", at = @At("RETURN"))
    public void constructor(CallbackInfo callbackInfo) {
        copyPage = addButton(new GuiButton(10, 4, 4, 98, 20, "Copy Page"));
        pastePage = addButton(new GuiButton(11, 4, 28, 98, 20, "Paste Page"));
        removePage = addButton(new GuiButton(12, 4, 52, 98, 20, "Remove Page"));
    }

    @Inject(method = "drawScreen", at = @At("HEAD"))
    public void draw(int mouseX, int mouseY, float partialTicks, CallbackInfo callbackInfo) {
        copyPage.visible = bookIsUnsigned;
        pastePage.visible = bookIsUnsigned;
        removePage.visible = bookIsUnsigned;
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"))
    public void action(GuiButton button, CallbackInfo callbackInfo) {
        if(button.id == copyPage.id) {
            copiedPage = (NBTTagString) bookPages.get(currPage);
        }
        if(button.id == pastePage.id) {
            if(copiedPage.getString().equals("")) return;
            bookPages.set(currPage, copiedPage);
        }
        if(button.id == removePage.id) {
            bookPages.removeTag(currPage);
            bookTotalPages -= 1;
            if(currPage + 1 > bookTotalPages)
                currPage = bookTotalPages - 1;
        }
    }
}