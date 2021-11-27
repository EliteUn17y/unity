package me.eliteun17y.unity.module;

public enum Category {
    MOVEMENT("Movement"),
    RENDER("Render"),
    PLAYER("Player"),
    EXPLOITS("Exploits"),
    COMBAT("Combat"),
    MISC("Misc"),
    HIDDEN("Hidden");

    public String name;
    Category(String name) {
        this.name = name;
    }
}