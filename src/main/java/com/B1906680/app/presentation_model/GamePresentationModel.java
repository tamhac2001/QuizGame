package com.B1906680.app.presentation_model;

import com.B1906680.app.model.Question;
import lombok.Getter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GamePresentationModel {
    @Getter
    private final List<Question> questionList;

    @Getter
    private int currentQuestionIndex = 0;

    public GamePresentationModel(List<Question> questionList) throws IOException {
        this.questionList = questionList;
    }

    public void toNextQuestion() {
        currentQuestionIndex++;
    }

    public String question() {
        return currentQuestion().getQuestion();
    }

    public String correctAnswer() {
        return currentQuestion().getCorrectAnswer();
    }

    public List<String> incorrectAnswer() {
        return currentQuestion().getIncorrectAnswers();
    }

    public int correctAnswerNumber() {
        return ThreadLocalRandom.current().nextInt(1, 4 + 1);

    }

    private Question currentQuestion() {
        return questionList.get(currentQuestionIndex);
    }


}
