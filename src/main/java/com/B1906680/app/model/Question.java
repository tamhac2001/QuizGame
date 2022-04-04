package com.B1906680.app.model;

import com.B1906680.app.utils.Result;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Value
public class Question {
    @SerializedName("question")
    String question;
    @SerializedName("correct_answer")
    String correctAnswer;
    @SerializedName("incorrect_answers")
    List<String> incorrectAnswers;

    private static final String GET_URL = "https://opentdb.com/api.php?amount=2&type=multiple";


    public static @Nullable List<Question> questionList() throws IOException {
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
//        httpURLConnection.setRequestProperty();
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // print result
//            System.out.println(response);
            Gson gson = new Gson();
            Result result = gson.fromJson(response.toString(), Result.class);
//            System.out.println(result);

            return result.getQuestions();

        } else {
            System.out.println("GET request not worked");
            return null;
        }
    }


}
