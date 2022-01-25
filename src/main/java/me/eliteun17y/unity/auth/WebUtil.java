package me.eliteun17y.unity.auth;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;

public class WebUtil {
    public static String post(List<NameValuePair> data, String a) {
        String aa = "";
        try {
            aa = new String(Base64.getDecoder().decode(data.get(0).getValue()));
            System.out.println("Set auth token");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            /*HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(aa);

            //httppost.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));
            httppost.addHeader("content-type", "application/json");
            StringBuilder sb = new StringBuilder("{\n");
            for(NameValuePair nameValuePair : data) {
                sb.append("\"").append(nameValuePair.getName()).append("\": \"").append(nameValuePair.getValue()).append(data.indexOf(nameValuePair) == data.size()-1 ? "\"" : "\",\n");
            }
            sb.append("\n}");
            httppost.setEntity(new StringEntity(sb.toString(), StandardCharsets.UTF_8));

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();*/
            StringBuilder sb = new StringBuilder("{\n");
            for(NameValuePair nameValuePair : data) {
                sb.append("\"").append(nameValuePair.getName()).append("\": \"").append(nameValuePair.getValue()).append(data.indexOf(nameValuePair) == data.size()-1 ? "\"" : "\",\n");
            }
            sb.append("\n}");

            HttpsURLConnection postConnection = (HttpsURLConnection) new URL(aa).openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);
            OutputStream os = postConnection.getOutputStream();
            os.write(sb.toString().getBytes());
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            } in.close();

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
