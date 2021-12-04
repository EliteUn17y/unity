package me.eliteun17y.unity.mixin.impl;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.auth.AuthenticatedUser;
import me.eliteun17y.unity.command.Command;
import me.eliteun17y.unity.event.Era;
import me.eliteun17y.unity.event.impl.EventPlayerMotionUpdate;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.util.time.Timer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    public Timer timer = new Timer();

    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void preUpdate(CallbackInfo ci) {
        EventUpdate event = new EventUpdate();
        Unity.EVENT_BUS.post(event);
        if(timer.hasTimePassed(180)) {
            try {
                Field fieldlist[]
                        = Unity.class.getDeclaredFields();
                for (int i = 0; i < fieldlist.length; i++) {
                    Field fld = fieldlist[i];
                    if(fld.getName().equals("user")) {
                        fld.setAccessible(true);
                        AuthenticatedUser u = (AuthenticatedUser) fld.get(Unity.instance.user);
                        if(u.username == "") {
                            System.out.println("piracy");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            timer.reset();
        }
    }

    @Inject(method = "onUpdate", at = @At("RETURN"))
    public void postUpdate(CallbackInfo ci) {
        EventUpdate event = new EventUpdate();
        event.setEra(Era.POST);
        Unity.EVENT_BUS.post(event);
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"))
    public void preUpdateWalkingPlayer(CallbackInfo callbackInfo) {
        EventPlayerMotionUpdate event = new EventPlayerMotionUpdate();
        event.setEra(Era.PRE);
        Unity.EVENT_BUS.post(event);
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"))
    public void postUpdateWalkingPlayer(CallbackInfo callbackInfo) {
        EventPlayerMotionUpdate event = new EventPlayerMotionUpdate();
        event.setEra(Era.POST);
        Unity.EVENT_BUS.post(event);
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void onSendChatMessage(String message, CallbackInfo callbackInfo) {
        if(message.startsWith(Unity.instance.commandManager.prefix)) {
            for(Command command : Unity.instance.commandManager.commands) {
                String[] str = message.substring(1).split(" ");
                if(command.name.equalsIgnoreCase(str[0])) {
                    String[] args = new String[str.length - 1];
                    for(int i = 1; i < str.length; i++) {
                        args[i - 1] = str[i];
                    }
                    command.execute(args);
                    callbackInfo.cancel();
                }
            }
        }
    }
}
