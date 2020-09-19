package com.example.telerehabilitationpatientapp.ui.login;

import androidx.annotation.Nullable;

import com.example.telerehabilitationpatientapp.ui.RequestResult;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult extends RequestResult {
    LoginResult(@Nullable Integer error) {
        super(error);
    }

    LoginResult(@Nullable Boolean success) {
        super(success);
    }
}
