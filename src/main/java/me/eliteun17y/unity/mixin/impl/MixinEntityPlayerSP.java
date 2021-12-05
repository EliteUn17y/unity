package me.eliteun17y.unity.mixin.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.auth.AuthenticatedUser;
import me.eliteun17y.unity.auth.Authenticator;
import me.eliteun17y.unity.auth.HWID;
import me.eliteun17y.unity.auth.WebUtil;
import me.eliteun17y.unity.command.Command;
import me.eliteun17y.unity.event.Era;
import me.eliteun17y.unity.event.impl.EventPlayerMotionUpdate;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.ui.ap.AP;
import me.eliteun17y.unity.util.file.FileUtil;
import me.eliteun17y.unity.util.time.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.BufferedReader;
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
public class MixinEntityPlayerSP {
    public Timer timer = new Timer();
    public boolean f = false;

    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void preUpdate(CallbackInfo ci) {
        EventUpdate event = new EventUpdate();
        Unity.EVENT_BUS.post(event);
        if(timer.hasTimePassed(120000)) {
            try {
                String a = "user";
                Field field = Unity.class.getDeclaredField(a);
            } catch (Exception e) {
                f = true;
            }

            String toEncrypt =  System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            md.update(toEncrypt.getBytes());
            StringBuffer hexString = new StringBuffer();

            byte byteData[] = md.digest();

            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            JsonParser j = new JsonParser();
            JsonElement e = j.parse(FileUtil.getContent(FileUtil.auth));
            JsonObject o = e.getAsJsonObject();

            List<NameValuePair> data = new ArrayList<>();
            data.add(new BasicNameValuePair("username", o.get("username").getAsString()));
            data.add(new BasicNameValuePair("password", o.get("password").getAsString()));
            data.add(new BasicNameValuePair("hwid", hexString.toString()));

            HttpResponse httpResponse = null;

            try {
                HttpClient httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost("http://localhost:3000/auth/login");

                //httppost.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));
                httppost.addHeader("content-type", "application/json");
                StringBuilder sb = new StringBuilder("{\n");
                for(NameValuePair nameValuePair : data) {
                    sb.append("\"").append(nameValuePair.getName()).append("\": \"").append(nameValuePair.getValue()).append(data.indexOf(nameValuePair) == data.size()-1 ? "\"" : "\",\n");
                }
                sb.append("\n}");
                httppost.setEntity(new StringEntity(sb.toString(), StandardCharsets.UTF_8));

                httpResponse = httpclient.execute(httppost);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                String d = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent())).lines().collect(Collectors.joining());
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(d);
                JsonObject object = jsonElement.getAsJsonObject();
                String n = object.get("name").getAsString();
                String p = object.get("password").getAsString();
                String h = object.get("hwid").getAsString();
            } catch (Exception e1) {
                e1.printStackTrace();

                f = true;
            }
            timer.reset();
        }
        if(f) {
            Minecraft.getMinecraft().displayGuiScreen(new AP());
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
