package com.B1906680.app;

import com.B1906680.app.presentation.LoginForm;
import com.B1906680.app.utils.AppComponent;
import com.B1906680.app.utils.DaggerAppComponent;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class QuizGame {
    public static void main(String[] args) throws IOException {

        FileInputStream serviceAccount =
                new FileInputStream("quiz-app-c0af8-firebase-adminsdk-gacdk-7e57bdc915.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);



        AppComponent appComponent = DaggerAppComponent.create();
        LoginForm loginForm = new LoginForm(appComponent.buildLoginPresentationModel(), appComponent);
        loginForm.initState();


    }
}
