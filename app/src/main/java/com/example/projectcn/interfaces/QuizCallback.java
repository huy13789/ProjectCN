package com.example.projectcn.interfaces;

import com.example.projectcn.model.Quiz;

public interface QuizCallback {
    void onQuizReceived(Quiz quiz);
    void onError(Throwable throwable);
}
