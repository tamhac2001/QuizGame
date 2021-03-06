package com.B1906680.app.presentation_model;

import com.B1906680.app.model.Question;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GamePresentationModel {
    @Getter
    @Named(value = "question_list")
    private final List<Question> questionList;

    @Getter
    private int currentQuestionIndex = 0;

    @Getter
    private int correctAnswerCounter = 0;

    @Getter
    @Setter
    private int userAnswerIndex = 0;

    @Getter
    private int correctAnswerIndex;

    @Inject
    public GamePresentationModel(List<Question> questionList) {
        this.questionList = questionList;
    }

    public void toNextQuestion() {
        currentQuestionIndex++;
    }

    public String question() {
        return currentQuestion().getQuestionString();
    }

    public String correctAnswer() {
        return currentQuestion().getCorrectAnswer();
    }

    public List<String> incorrectAnswer() {
        return currentQuestion().getIncorrectAnswers();
    }

    private int randomCorrectAnswerIndex() {
        return ThreadLocalRandom.current().nextInt(1, 4 + 1);
    }

    public void updateCorrectAnswerIndex() {
        correctAnswerIndex = randomCorrectAnswerIndex();
    }

    private Question currentQuestion() {
        return questionList.get(currentQuestionIndex);
    }

    public boolean checkUserAnswer() {
        return userAnswerIndex == correctAnswerIndex;
    }

    public void increaseCorrectAnswerCounter() {
        correctAnswerCounter++;
    }

    public void updateQuestion(boolean userDidAnswerCorrect) {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        questionList.set(currentQuestionIndex, currentQuestion.withUserDidAnswerCorrect(userDidAnswerCorrect));
    }
}
