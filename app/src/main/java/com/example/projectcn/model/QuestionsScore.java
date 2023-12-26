package com.example.projectcn.model;

import com.google.gson.annotations.SerializedName;

public class QuestionsScore {
    @SerializedName("score_id")
    private Long id;

    @SerializedName("question_id")
    private QuestionsRespone question;

    @SerializedName("user_answer_id")
    private Answers userAnswer;

    @SerializedName("is_correct")
    private boolean isCorrect;
}
