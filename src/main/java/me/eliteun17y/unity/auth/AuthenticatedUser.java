package me.eliteun17y.unity.auth;

public class AuthenticatedUser {
    public String username;
    public String password;
    public String hwid;

    public AuthenticatedUser(String username, String password, String hwid) {
        this.username = username;
        this.password = password;
        this.hwid = hwid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHwid() {
        return hwid;
    }

    public void setHwid(String hwid) {
        this.hwid = hwid;
    }
}