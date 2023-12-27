package com.example.projectcn.model;

import com.google.gson.annotations.SerializedName;

public class QuestionsRespone {



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public Answers getAnswers() {
        return answers;
    }

    public void setAnswers(Answers answers) {
        this.answers = answers;
    }

    @SerializedName("id")
    private Long id;

    @SerializedName("questions")
    private String question;

    @SerializedName("answersA")
    private String answerA;

    @SerializedName("answersB")
    private String answerB;

    @SerializedName("answersC")
    private String answerC;

    @SerializedName("answersD")
    private String answerD;

    @SerializedName("answers")
    private Answers answers;

    // Trường mới để lưu trữ ID RadioButton được chọn bởi người dùng
    private int selectedAnswerId = 5;

    public int getSelectedAnswerId() {
        return selectedAnswerId;
    }

    public void setSelectedAnswerId(int selectedAnswerId) {
        this.selectedAnswerId = selectedAnswerId;
    }


}
