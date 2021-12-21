package me.eliteun17y.unity.mixin.impl;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Era;
import me.eliteun17y.unity.event.impl.EventHandleWaterMovement;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow private int entityId;

    @Shadow public abstract void move(MoverType type, double x, double y, double z);

    @Shadow public double motionX;

    @Shadow public double motionY;

    @Shadow public double motionZ;

    public Method methodGetFlag = null;

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

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSneaking()Z"))
    public boolean isSneaking(final Entity entity) {
        if(methodGetFlag == null)
            methodGetFlag = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70083_f", boolean.class, int.class);
        boolean sneaking = false;
        try {
            sneaking = (boolean) methodGetFlag.invoke(entity, 1);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return Unity.instance.moduleManager.getModule("Safe Walk").isToggled() || sneaking;
    }
}
