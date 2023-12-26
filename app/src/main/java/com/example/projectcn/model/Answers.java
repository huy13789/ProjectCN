package com.example.projectcn.model;

import com.google.gson.annotations.SerializedName;

public class Answers {
    @SerializedName("id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    @SerializedName("correct")
    private String correct;

    public int getIdAsInt() {
        return id.intValue();
    }

}
