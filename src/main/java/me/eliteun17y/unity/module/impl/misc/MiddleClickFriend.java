package me.eliteun17y.unity.module.impl.misc;

import me.eliteun17y.unity.Unity;
import me.eliteun17y.unity.event.Subscribe;
import me.eliteun17y.unity.event.impl.EventUpdate;
import me.eliteun17y.unity.managers.friend.Friend;
import me.eliteun17y.unity.module.Category;
import me.eliteun17y.unity.module.Module;
import me.eliteun17y.unity.util.chat.ChatUtil;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class MiddleClickFriend extends Module {
    public boolean isClicked = false;

    public MiddleClickFriend() {
        super("Middle Click Friend", "Allows you to middle click over a player to add them as your friend.", Category.MISC, Keyboard.KEY_NONE);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if(!Mouse.isButtonDown(2))
            isClicked = false;
        if(mc.objectMouseOver.entityHit != null) {
            if(mc.objectMouseOver.entityHit instanceof EntityPlayer) {
                if (Mouse.isButtonDown(2) && !isClicked) {
                    if(Unity.instance.friendManager.getRegistry().stream().anyMatch(friend -> friend.uuid.equals(mc.objectMouseOver.entityHit.getUniqueID()))) {
                        ChatUtil.sendClientMessage(mc.objectMouseOver.entityHit.getName() + " has been removed from the friends list.");
                        Unity.instance.friendManager.remove(Unity.instance.friendManager.getRegistry().stream().filter(friend -> friend.uuid.equals(mc.objectMouseOver.entityHit.getUniqueID())).findFirst().get());
                    }else{
                        ChatUtil.sendClientMessage(mc.objectMouseOver.entityHit.getName() + " has been added to the friends list.");
                        Unity.instance.friendManager.add(new Friend(mc.objectMouseOver.entityHit.getName(), mc.objectMouseOver.entityHit.getUniqueID()));
                    }
                    isClicked = true;
                }
            }
        }
    }
}
