package com.B1906680.app.model;

import lombok.Value;
import org.joda.time.DateTime;

@Value
public class GameRecord {
    DateTime dateTime;
    int correctAnswerCounter;
}
