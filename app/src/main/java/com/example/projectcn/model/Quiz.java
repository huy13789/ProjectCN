package com.example.projectcn.model;

import com.google.gson.annotations.SerializedName;

public class Quiz {
    @SerializedName("quizId")
    private Long quizId;
    @SerializedName("nameQuiz")
    private String nameQuiz;
    @SerializedName("category_id")
    private Category category;

    public Quiz() {
    }
    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getNameQuiz() {
        return nameQuiz;
    }

    public void setNameQuiz(String nameQuiz) {
        this.nameQuiz = nameQuiz;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getIdAsInt() {
        return quizId.intValue();
    }
}
