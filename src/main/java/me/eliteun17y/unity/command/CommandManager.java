package me.eliteun17y.unity.command;

import me.eliteun17y.unity.command.impl.Book;
import me.eliteun17y.unity.command.impl.Config;

import java.util.ArrayList;

public class CommandManager {
    public String prefix = "?";

    public ArrayList<Command> commands;

    public CommandManager() {
        commands = new ArrayList<>();

        commands.add(new Book());
        commands.add(new Config());
    }
}
