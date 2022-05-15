package com.B1906680.app.utils;

import com.B1906680.app.model.Question;
import com.B1906680.app.model.User;
import com.B1906680.app.presentation_model.GamePresentationModel;
import com.B1906680.app.presentation_model.GameResultPresentationModel;
import com.B1906680.app.presentation_model.LoginPresentationModel;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Module
public class AppModule {

    @Provides
    @Singleton
    public List<Question> provideQuestions() {
        return Question.questionList();
    }

    @Provides
    @Singleton
    public User provideUser() {
        return new User(null, null, null);
    }

    @Provides
    @Singleton
    public LoginPresentationModel provideLoginPresentationModel() {
        return new LoginPresentationModel(provideUser());
    }

    @Provides
    @Singleton
    public GamePresentationModel provideGamePresentationModel() {
        return new GamePresentationModel(provideQuestions());
    }

    @Provides
    @Singleton
    public GameResultPresentationModel provideGameResultPresentationModel(){
        return new GameResultPresentationModel(provideGamePresentationModel().getQuestionList());
    }
}
