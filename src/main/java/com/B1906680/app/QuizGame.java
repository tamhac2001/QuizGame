package com.B1906680.app;

import com.B1906680.app.model.User;
import com.B1906680.app.presentation.LoginForm;
import com.B1906680.app.presentation_model.LoginPresentationModel;
import com.B1906680.app.utils.AppInjector;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.inject.Guice;
import com.google.inject.Injector;

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


        Injector injector = Guice.createInjector(new AppInjector());
        User user = injector.getProvider(User.class).get();
        System.out.println(user);
        LoginForm loginForm = new LoginForm(injector.getProvider(LoginPresentationModel.class).get());
        loginForm.initState();

    }
}
