package com.B1906680.app.presentation_model;

import com.B1906680.app.model.Question;
import lombok.Getter;

import javax.inject.Inject;
import java.util.List;

public class GameResultPresentationModel {

    @Getter
    private final List<Question> questionList;

//    @Inject
    public GameResultPresentationModel(List<Question> questionList) {
        this.questionList = questionList;
    }
}
