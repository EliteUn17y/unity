package me.eliteun17y.unity.module.impl.movement;

import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.setting.impl.ModeValue;
import me.eliteun17y.unity.util.setting.impl.NumberValue;
import me.eliteun17y.unity.util.time.Timer;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class Catch extends Module {
    public ModeValue mode = new ModeValue(this, "Mode", "Vanilla", "Vanilla", "No Cheat Plus");
    public NumberValue distance = new NumberValue(this, "Distance", 2, 5, 1, 0.1);

    public BlockPos lastOnGroundPosition;
    public Timer timer = new Timer();

    public Catch() {
        super("Catch", "Teleports you back to the block you were standing on when you fall.", Category.MOVEMENT, Keyboard.KEY_NONE);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        lastOnGroundPosition = mc.player.getPosition();
        timer.reset();
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        switch(mode.getMode()) {
            case "Vanilla":
                if(!mc.player.onGround) {
                    if(lastOnGroundPosition.getY() - mc.player.posY >= distance.getDouble()) {
                        mc.player.setPosition(lastOnGroundPosition.getX(), lastOnGroundPosition.getY(), lastOnGroundPosition.getZ());
                    }
                }else {
                    lastOnGroundPosition = mc.player.getPosition();
                }
                break;
            case "No Cheat Plus":
                if(!mc.player.onGround) {
                    if(lastOnGroundPosition.getY() - mc.player.posY >= distance.getDouble()) {
                        if(timer.hasTimePassed(500))
                            mc.player.jump(); // Flag NCP so we get rubberbanded to the last on ground position
                        timer.reset();
                    }
                }else {
                    lastOnGroundPosition = mc.player.getPosition();
                }
                break;
        }
    }
}
