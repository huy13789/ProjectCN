package com.example.projectcn.model;

import com.google.gson.annotations.SerializedName;

public class QuestionsScore {
    @SerializedName("score_id")
    private Long id;

    @SerializedName("question")
    private QuestionsRespone question;

    @SerializedName("userAnswer")
    private Answers userAnswer;

    @SerializedName("isCorrect")
    private boolean isCorrect;

    @SerializedName("totalQuestionsScore")
    private TotalQuestionsScore totalQuestionsScore;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionsRespone getQuestion() {
        return question;
    }

    public void setQuestion(QuestionsRespone question) {
        this.question = question;
    }

    public Answers getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Answers userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public TotalQuestionsScore getTotalQuestionsScore() {
        return totalQuestionsScore;
    }


    public QuestionsScore(QuestionsRespone question, Answers userAnswer, boolean isCorrect) {
        this.question = question;
        this.userAnswer = userAnswer;
        this.isCorrect = isCorrect;
    }
    public void setTotalQuestionsScore(TotalQuestionsScore totalQuestionsScore) {
        this.totalQuestionsScore = totalQuestionsScore;
    }
}
