package me.eliteun17y.unity.mixin.impl;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.impl.EventRenderSheepWool;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.passive.EntitySheep;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerSheepWool.class)
public class MixinLayerSheepWool {
    @Final
    @Shadow
    private ModelSheep1 sheepModel;

    @Final
    @Shadow
    private RenderSheep sheepRenderer;

    @Inject(method = "doRenderLayer", at = @At("HEAD"), cancellable = true)
    public void renderLayer(EntitySheep entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci) {
        EventRenderSheepWool event = new EventRenderSheepWool(sheepModel, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, sheepRenderer);
        Unity.EVENT_BUS.post(event);

        if(event.isCancelled())
            ci.cancel();
    }
}
