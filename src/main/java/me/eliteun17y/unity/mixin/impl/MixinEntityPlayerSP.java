package me.eliteun17y.unity.mixin.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.command.Command;
import me.eliteun17y.unity.event.Era;
import me.eliteun17y.unity.event.impl.EventBlockPush;
import me.eliteun17y.unity.event.impl.EventMove;
import me.eliteun17y.unity.event.impl.EventPlayerMotionUpdate;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.util.config.ConfigUtil;
import me.eliteun17y.unity.util.file.FileUtil;
import me.eliteun17y.unity.util.time.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.entity.MoverType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP {
    @Shadow public abstract void setPlayerSPHealth(float health);

    public Timer timer = new Timer();
    public boolean f = false;

    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void preUpdate(CallbackInfo ci) {
        EventUpdate event = new EventUpdate();
        Unity.EVENT_BUS.post(event);

        if(!Unity.instance.loaded) {
            ConfigUtil.load(new File(FileUtil.unity.getPath() + "/temp.json"));
            Unity.instance.loaded = true;
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

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    public void move(MoverType type, double x, double y, double z, CallbackInfo callbackInfo) {
        EventMove event = new EventMove(type, x, y, z);
        Unity.EVENT_BUS.post(event);
        if(event.isCancelled()) {
            callbackInfo.cancel();
        }
    }


    @Inject(method = "pushOutOfBlocks(DDD)Z", at = @At("HEAD"), cancellable = true)
    public void pushOutOfBlocks(double x, double y, double z, CallbackInfoReturnable<Boolean> callbackInfo)
    {
        EventBlockPush l_Event = new EventBlockPush();
        Unity.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled())
            callbackInfo.setReturnValue(false);
    }
}
