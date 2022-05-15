package com.B1906680.app.presentation_model;

import com.B1906680.app.model.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

@Singleton
public class LoginPresentationModel implements IAuthFacade {
    private static final String API_KEY = "AIzaSyBljP-K1488QV6163KDK7iT7lZGL9Q3zHI";


    private User user;

    @Inject
    public LoginPresentationModel(User user) {
        this.user = user;
    }

    @SneakyThrows
    @Override
    public String signUpWithEmailAndPassword(String email, String password) {
        HttpClient httpclient = HttpClients.createDefault();
        URI uri = new URI("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + API_KEY);
        HttpPost httpPost = new HttpPost(uri);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("returnSecureToken", true);
        StringEntity stringEntity = new StringEntity(jsonObject.toString());
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Content-Type", "application/json");
        //Execute and get the response.
        HttpResponse httpResponse = httpclient.execute(httpPost);
        StatusLine statusLine = httpResponse.getStatusLine();
        HttpEntity entity = httpResponse.getEntity();
        BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response);
        if (statusLine.getStatusCode() == 400) {
            return String.valueOf(response);
        } else {
            return "success";
        }
    }

    @SneakyThrows
    @Override
    public String singInWithEmailAndPassword(String email, String password) {
        HttpClient httpclient = HttpClients.createDefault();
        URI uri = new URI("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY);
        HttpPost httpPost = new HttpPost(uri);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("returnSecureToken", true);
        StringEntity stringEntity = new StringEntity(jsonObject.toString());
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Content-Type", "application/json");
        //Execute and get the response.
        HttpResponse httpResponse = httpclient.execute(httpPost);
        StatusLine statusLine = httpResponse.getStatusLine();
        System.out.println("status code:" + statusLine.getStatusCode());
        HttpEntity entity = httpResponse.getEntity();
        BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response);
        if (statusLine.getStatusCode() == 400) {
            return String.valueOf(response);
        } else {
            // convert response to JSON
            jsonObject = JsonParser.parseString(String.valueOf(response)).getAsJsonObject();
            user = new User(jsonObject.get("localId").getAsString(), jsonObject.get("email").getAsString(), null);
            return "success";
        }

    }
}
