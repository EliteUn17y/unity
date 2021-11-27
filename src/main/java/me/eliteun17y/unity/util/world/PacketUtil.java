package me.eliteun17y.unity.util.world;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class PacketUtil {
    public static void sendSilentPacket(Packet packet) {
        Minecraft.getMinecraft().getConnection().getNetworkManager().sendPacket(packet, null);
    }
}
