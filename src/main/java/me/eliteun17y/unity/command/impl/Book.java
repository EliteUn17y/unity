package me.eliteun17y.unity.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.netty.buffer.Unpooled;
import me.eliteun17y.unity.command.Command;
import me.eliteun17y.unity.util.chat.ChatUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.util.EnumHand;

import java.util.Locale;

public class Book extends Command {
    public Book() {
        super("Book", "Allows you to do certain things with a book.");
    }

    @Override
    public void execute(String[] args) {
        ItemStack book = null;
        if(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.WRITABLE_BOOK) {
            book = mc.player.getHeldItem(EnumHand.MAIN_HAND);
        }
        if(mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() == Items.WRITABLE_BOOK) {
            book = mc.player.getHeldItem(EnumHand.OFF_HAND);
        }
        if(book == null) {
            ChatUtil.sendClientMessage("You must have a book in your hand!");
            return;
        }

        ChatUtil.sendClientMessage(args[0]);
        switch(args[0].toLowerCase(Locale.ROOT)) {
            case "sign":
                if(args.length > 1) {
                    if(book.hasTagCompound()) {
                        NBTTagCompound nbtTagCompound = book.getTagCompound();
                        book.setTagInfo("author", new NBTTagString(mc.player.getName()));
                        book.setTagInfo("title", new NBTTagString(ChatFormatting.LIGHT_PURPLE + "[Unity] " + ChatFormatting.RESET + args[1]));

                        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                        packetBuffer.writeItemStack(book);
                        mc.getConnection().sendPacket(new CPacketCustomPayload("MC|BSign", packetBuffer));

                        ChatUtil.sendClientMessage("Signed book.");
                    }
                }
                return;
            case "write":
                if(args.length > 1) {
                    if(book.hasTagCompound()) {
                        NBTTagList bookPages = new NBTTagList();

                        StringBuilder str = new StringBuilder();

                        for(int i = 1; i < args.length; i++) {
                            str.append(args[i]).append(" ");
                        }

                        String[] pages = str.toString().split(";");
                        for(String s : pages) {
                            bookPages.appendTag(new NBTTagString(s));
                        }

                        book.setTagInfo("pages", bookPages);

                        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                        packetBuffer.writeItemStack(book);
                        mc.getConnection().sendPacket(new CPacketCustomPayload("MC|BEdit", packetBuffer));

                        ChatUtil.sendClientMessage("Wrote to book.");
                    }
                }
                return;
        }
    }
}
