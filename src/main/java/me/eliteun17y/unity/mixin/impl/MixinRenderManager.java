package me.eliteun17y.unity.mixin.impl;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Era;
import me.eliteun17y.unity.event.impl.EventEntityRender;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderManager.class)
public class MixinRenderManager {
    @Inject(method = "renderEntity", at = @At("HEAD"), cancellable = true)
    public void renderEntityPre(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_, CallbackInfo ci) {
        EventEntityRender eventEntityRender = new EventEntityRender(entityIn, x, y, z, yaw, partialTicks);
        eventEntityRender.setEra(Era.PRE);
        Unity.EVENT_BUS.post(eventEntityRender);

        if(eventEntityRender.isCancelled())
            ci.cancel();
    }

    @Inject(method = "renderEntity", at = @At("RETURN"), cancellable = true)
    public void renderEntityPost(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_, CallbackInfo ci) {
        EventEntityRender eventEntityRender = new EventEntityRender(entityIn, x, y, z, yaw, partialTicks);
        eventEntityRender.setEra(Era.POST);
        Unity.EVENT_BUS.post(eventEntityRender);

        if(eventEntityRender.isCancelled())
            ci.cancel();
    }
}
