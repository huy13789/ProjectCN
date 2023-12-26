package com.example.projectcn.interfaces;

import com.example.projectcn.model.User;

public interface UserCallback {
    void onUserReceived(User user);
    void onError(Throwable throwable);
}
