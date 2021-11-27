package me.eliteun17y.unity.util.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil {
    public static void sendClientMessage(String message) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.LIGHT_PURPLE + "[Unity] " + ChatFormatting.RESET + message));
    }
}
