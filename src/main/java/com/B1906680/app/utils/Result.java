package com.B1906680.app.utils;

import com.B1906680.app.model.Question;
import com.google.gson.annotations.SerializedName;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public class Result {
    @SerializedName("results")
    final List<Question> questions;

    public Result(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions.stream().map(question ->
                question.withQuestionString(htmlToText(question.getQuestionString())).withCorrectAnswer(htmlToText(question.getCorrectAnswer())).withIncorrectAnswers(question.getIncorrectAnswers().stream().map(this::htmlToText).collect(Collectors.toList()))).collect(Collectors.toList());

    }

    private @NotNull String htmlToText(String html) {
        return Jsoup.parse(html).text();
    }
}
