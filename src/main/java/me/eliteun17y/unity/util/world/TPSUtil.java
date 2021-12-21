package me.eliteun17y.unity.util.world;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Direction;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventPacket;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.util.time.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketTimeUpdate;

public class TPSUtil {
    private static int tps;
    public int ticks;
    public Timer timer = new Timer();

    public TPSUtil() {
        Unity.EVENT_BUS.register(this);
        ticks = 0;
    }

    public static int getTPS() {
        return tps;
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if(Minecraft.getMinecraft().player.ticksExisted == 0) {
            timer.reset();
        }
        if(timer.hasTimePassed(10000)) {
            tps = ticks;
            ticks = 0;
            timer.reset();
        }
    }

    @Subscribe
    public void onPacket(EventPacket event) {
        if(event.getDirection() == Direction.INCOMING) {
            if(event.getPacket() instanceof SPacketTimeUpdate) {
                ticks++;
            }
        }
    }
}
