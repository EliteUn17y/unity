package me.eliteun17y.unity.module.impl.player;

import com.mojang.authlib.GameProfile;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.lwjgl.input.Keyboard;

import java.util.UUID;

public class FakePlayer extends Module {
    public EntityOtherPlayerMP player;

    public FakePlayer() {
        super("Fake Player", "Generates a fake client sided player.", Category.PLAYER, Keyboard.KEY_NONE);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if(mc.world == null) return;
        player = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("b56f1867-8385-4afc-8930-9b67d528a8d9"), "Fake Player"));
        player.copyLocationAndAnglesFrom(mc.player);
        mc.world.addEntityToWorld(-100, player);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if(player == null) {
            player = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("b56f1867-8385-4afc-8930-9b67d528a8d9"), "Fake Player"));
            player.copyLocationAndAnglesFrom(mc.player);
            mc.world.addEntityToWorld(-100, player);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if(player != null) {
            mc.world.removeEntity(player);
        }
        player = null;
    }
}
