package me.eliteun17y.unity.mixin.impl;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Era;
import me.eliteun17y.unity.event.impl.EventRenderLivingLabel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Render.class)
public class MixinRender {
    @Inject(method = "renderLivingLabel", at = @At("HEAD"), cancellable = true)
    public void renderLivingLabelPre(Entity entityIn, String str, double x, double y, double z, int maxDistance, CallbackInfo callbackInfo) {
        EventRenderLivingLabel event = new EventRenderLivingLabel(entityIn, str, x, y, z, maxDistance);
        event.setEra(Era.PRE);
        Unity.EVENT_BUS.post(event);

        if(event.isCancelled())
            callbackInfo.cancel();
    }

    @Inject(method = "renderLivingLabel", at = @At("RETURN"), cancellable = true)
    public void renderLivingLabelPost(Entity entityIn, String str, double x, double y, double z, int maxDistance, CallbackInfo callbackInfo) {
        EventRenderLivingLabel event = new EventRenderLivingLabel(entityIn, str, x, y, z, maxDistance);
        event.setEra(Era.POST);
        Unity.EVENT_BUS.post(event);

        if(event.isCancelled())
            callbackInfo.cancel();
    }
}
