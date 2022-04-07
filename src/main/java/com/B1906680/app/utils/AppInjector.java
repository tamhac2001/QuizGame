package com.B1906680.app.utils;

import com.B1906680.app.model.User;
import com.B1906680.app.presentation_model.LoginPresentationModel;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class AppInjector extends AbstractModule {

    @Provides
    @Singleton
    User userProvider() {
        return new User("haha", "haha", null);
    }

    @Provides
    @Singleton
    LoginPresentationModel loginPresentationModelProvider() {
        return new LoginPresentationModel(userProvider());
    }


}
