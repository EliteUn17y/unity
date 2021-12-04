package me.eliteun17y.unity.auth;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class WebUtil {
    public static HttpResponse post(List<NameValuePair> data, String a) {
        String aa = "";
        try {
            aa = new String(Base64.getDecoder().decode(data.get(0).getValue()));
            System.out.println("Set auth token");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(aa);

            //httppost.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));
            httppost.addHeader("content-type", "application/json");
            StringBuilder sb = new StringBuilder("{\n");
            for(NameValuePair nameValuePair : data) {
                sb.append("\"").append(nameValuePair.getName()).append("\": \"").append(nameValuePair.getValue()).append(data.indexOf(nameValuePair) == data.size()-1 ? "\"" : "\",\n");
            }
            sb.append("\n}");
            System.out.println(sb.toString());
            httppost.setEntity(new StringEntity(sb.toString(), StandardCharsets.UTF_8));

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
