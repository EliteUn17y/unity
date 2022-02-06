package me.eliteun17y.unity.command.impl;

import me.eliteun17y.unity.command.Command;
import me.eliteun17y.unity.util.chat.ChatUtil;

public class VClip extends Command {
    public VClip() {
        super("VClip", "Allows you to teleport up and down.");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            ChatUtil.sendClientMessage("Incorrect syntax. Correct syntax: .vclip [(-)amount]");
            return;
        }
        try {
            mc.player.setPosition(mc.player.posX, mc.player.posY + Double.parseDouble(args[0]), mc.player.posZ);
            if(mc.player.getRidingEntity() != null) mc.player.getRidingEntity().setPosition(mc.player.posX, mc.player.posY + Double.parseDouble(args[0]), mc.player.posZ);

        }catch (NumberFormatException e) {
            ChatUtil.sendClientMessage("Incorrect number.");
        }
    }
}
