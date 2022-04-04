package com.B1906680.app.utils;

import com.B1906680.app.model.Question;
import com.google.gson.annotations.SerializedName;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@ToString
public class Result {
    @SerializedName("results")
    final List<Question> questions;

    public Result(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }


}
