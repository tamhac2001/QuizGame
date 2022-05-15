package com.B1906680.app.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Value;
import lombok.With;

import java.util.List;

@Value()
@With
public class User {
    @SerializedName("localId")
    @Expose(deserialize = false)
    String id;
    @SerializedName("email")
    String email;
    @SerializedName("gameRecords")
    List<GameRecord> gameRecords;

    public User fromJson(String json) {
        return new Gson().fromJson(json, this.getClass()).withGameRecords(null);
    }


}
