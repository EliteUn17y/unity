package me.eliteun17y.unity.managers.alts;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class Alt {
    public String email;
    public String name;
    public String password;
    public UUID uuid;
    public BufferedImage image;

    public Alt(String name, String email, String password, UUID uuid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.uuid = uuid;

        if(uuid == null) return;

        String url = "https://crafatar.com/avatars/" + uuid;
        try {
            URL u = new URL(url);
            image = ImageIO.read(u);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
