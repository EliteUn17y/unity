package me.eliteun17y.unity.mixin.impl;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Era;
import me.eliteun17y.unity.event.impl.EventHandleWaterMovement;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow private int entityId;

    @Shadow public abstract void move(MoverType type, double x, double y, double z);

    @Shadow public double motionX;

    @Shadow public double motionY;

    @Shadow public double motionZ;

    @Inject(method = "handleWaterMovement", at = @At("HEAD"), cancellable = true)
    public void onHandleWaterMovementPre(CallbackInfoReturnable<Boolean> callbackInfo) {
        if(Minecraft.getMinecraft().player != null)
            if(entityId == Minecraft.getMinecraft().player.getEntityId()) {
                EventHandleWaterMovement event = new EventHandleWaterMovement();
                event.setEra(Era.PRE);
                Unity.EVENT_BUS.post(event);

                if(event.isCancelled()) {
                    callbackInfo.cancel();
                }
            }
    }
}
