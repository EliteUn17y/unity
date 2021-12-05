package me.eliteun17y.unity.window;

import me.eliteun17y.unity.window.impl.Configs;
import me.eliteun17y.unity.window.impl.Demo;
import me.eliteun17y.unity.window.impl.Friends;

import java.util.ArrayList;

public class WindowManager {
    public ArrayList<Window> windows;

    public WindowManager() {
        windows = new ArrayList<>();

        windows.add(new Configs());
        windows.add(new Demo());
        windows.add(new Friends());

        float x = 5;
        for(Window window : windows) {
            window.setX(x);
            x += window.getWidth() + 10;
        }
    }

    public ArrayList<Window> getWindows() {
        return windows;
    }
}
