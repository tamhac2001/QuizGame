package com.B1906680.app.utils;


import com.B1906680.app.model.User;
import com.B1906680.app.presentation_model.GamePresentationModel;
import com.B1906680.app.presentation_model.GameResultPresentationModel;
import com.B1906680.app.presentation_model.LoginPresentationModel;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    LoginPresentationModel buildLoginPresentationModel();

    GamePresentationModel buildGamePresentationModel();

    GameResultPresentationModel buildGameResultPresentationModel();

    User buildUser();
}
