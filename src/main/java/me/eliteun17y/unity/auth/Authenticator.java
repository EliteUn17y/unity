package me.eliteun17y.unity.auth;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Authenticator {
    public static String authToken = "aHR0cDovL2xvY2FsaG9zdDozMDAwL2F1dGgvbG9naW4=";

    public static AuthenticatedUser getUser(String username, String password, String hwid) {
        AuthenticatedUser user = new AuthenticatedUser("", "", "");
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("authtoken", authToken));
        data.add(new BasicNameValuePair("username", username));
        data.add(new BasicNameValuePair("password", password));
        data.add(new BasicNameValuePair("hwid", hwid));
        try {
            HttpResponse httpResponse = WebUtil.post(data, "https://ath.unityclient.net");
            String d = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent())).lines().collect(Collectors.joining());
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(d);
            JsonObject object = jsonElement.getAsJsonObject();
            user.username = object.get("name").getAsString();
            user.password = object.get("password").getAsString();
            user.hwid = object.get("hwid").getAsString();
        } catch (Exception e) {

        }
        return user;
    }

    public static AuthenticatedUser auth(String username, String password, String hwid) {
        AuthenticatedUser user = new AuthenticatedUser("", "", "");
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("authtoken", authToken));
        data.add(new BasicNameValuePair("username", username));
        data.add(new BasicNameValuePair("password", password));
        data.add(new BasicNameValuePair("hwid", hwid));
        try {
            HttpResponse httpResponse = WebUtil.post(data, "https://ath.unityclient.net");
            String d = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent())).lines().collect(Collectors.joining());
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(d);
            JsonObject object = jsonElement.getAsJsonObject();
            user.username = object.get("name").getAsString();
            user.password = object.get("password").getAsString();
            user.hwid = object.get("hwid").getAsString();
        } catch (Exception e) {
            System.out.println("ERR 0x01");
            FMLCommonHandler.instance().exitJava(-1, true);
        }
        return user;
    }
}
