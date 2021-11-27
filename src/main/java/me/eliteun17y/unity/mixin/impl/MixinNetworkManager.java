package me.eliteun17y.unity.mixin.impl;

import io.netty.channel.ChannelHandlerContext;
import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Direction;
import me.eliteun17y.unity.event.Era;
import me.eliteun17y.unity.event.impl.EventPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void onSendPacket(Packet packet, CallbackInfo callbackInfo) {
        EventPacket eventPacket = new EventPacket(packet);
        eventPacket.setDirection(Direction.OUTGOING);
        eventPacket.setEra(Era.PRE);
        Unity.EVENT_BUS.post(eventPacket);

        if(eventPacket.isCancelled())
            callbackInfo.cancel();
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("RETURN"), cancellable = true)
    public void onSendPacketPost(Packet packet, CallbackInfo callbackInfo) {
        EventPacket eventPacket = new EventPacket(packet);
        eventPacket.setDirection(Direction.OUTGOING);
        eventPacket.setEra(Era.POST);
        Unity.EVENT_BUS.post(eventPacket);

        if(eventPacket.isCancelled())
            callbackInfo.cancel();
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void onReceivePacket(ChannelHandlerContext channelHandlerContext, Packet packet, CallbackInfo callbackInfo) {
        EventPacket eventPacket = new EventPacket(packet);
        eventPacket.setDirection(Direction.INCOMING);
        eventPacket.setEra(Era.PRE);
        Unity.EVENT_BUS.post(eventPacket);

        if(eventPacket.isCancelled())
            callbackInfo.cancel();
    }

    @Inject(method = "channelRead0", at = @At("RETURN"), cancellable = true)
    public void onReceivePacketPost(ChannelHandlerContext channelHandlerContext, Packet packet, CallbackInfo callbackInfo) {
        EventPacket eventPacket = new EventPacket(packet);
        eventPacket.setDirection(Direction.INCOMING);
        eventPacket.setEra(Era.POST);
        Unity.EVENT_BUS.post(eventPacket);

        if(eventPacket.isCancelled())
            callbackInfo.cancel();
    }
}
