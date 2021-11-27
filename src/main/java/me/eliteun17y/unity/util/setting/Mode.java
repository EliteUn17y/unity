package me.eliteun17y.unity.util.setting;

import java.util.ArrayList;
import java.util.List;

public class Mode {
    public int modeIndex;
    public String mode;
    public String defaultVal;
    public ArrayList<String> modes = new ArrayList<>();

    public Mode(String defaultVal, List<String> modes) {
        this.mode = defaultVal;
        this.defaultVal = defaultVal;
        this.modes.addAll(modes);
        this.modeIndex = modes.indexOf(mode);
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
        this.modeIndex = modes.indexOf(mode);
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public ArrayList<String> getModes() {
        return modes;
    }

    public int getModeIndex() {
        return modeIndex;
    }
}
