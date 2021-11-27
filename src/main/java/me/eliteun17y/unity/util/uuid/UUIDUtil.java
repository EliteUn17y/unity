package me.eliteun17y.unity.util.uuid;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class UUIDUtil {
    public static UUID getUUID(String username) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            StringBuilder stringBuilder = new StringBuilder();

            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();

            bufferedReader.close();

            return uuidFromStringWithoutDashes(jsonObject.get("id").getAsString());
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static UUID uuidFromStringWithoutDashes(String uuid) {
        StringBuilder stringBuilder = new StringBuilder(uuid);
        return UUID.fromString(stringBuilder.insert(8, "-").insert(13, "-").insert(18, "-").insert(23, "-").toString());
    }
}
