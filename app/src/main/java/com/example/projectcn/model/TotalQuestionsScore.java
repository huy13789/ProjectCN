package com.example.projectcn.model;

import com.google.gson.annotations.SerializedName;

public class TotalQuestionsScore {

    public TotalQuestionsScore(){

    }
    public TotalQuestionsScore(User user, Quiz quizResponse, Double totalScore) {
        this.user = user;
        this.quiz = quizResponse;
        this.totalScore = totalScore;
    }

    public Long getId() {
        return totalScoreId;
    }

    public void setId(Long id) {
        this.totalScoreId = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    @SerializedName("totalScoreId")
    private Long totalScoreId;

    @SerializedName("user")
    private User user;

    @SerializedName("quiz")
    private Quiz quiz;

    @SerializedName("totalScore")
    private double totalScore;
}
