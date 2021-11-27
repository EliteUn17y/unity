package me.eliteun17y.unity.command;

import net.minecraft.client.Minecraft;

public class Command {
    public String name;
    public String description;

    protected Minecraft mc = Minecraft.getMinecraft();

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void execute(String[] args) {}
}