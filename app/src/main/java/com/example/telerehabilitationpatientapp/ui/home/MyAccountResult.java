package com.example.telerehabilitationpatientapp.ui.home;

import androidx.annotation.Nullable;

import com.example.telerehabilitationpatientapp.ui.RequestResult;

public class MyAccountResult extends RequestResult {
    MyAccountResult(@Nullable Integer error) {
        super(error);
    }

    MyAccountResult(@Nullable Boolean success) {
        super(success);
    }
}

