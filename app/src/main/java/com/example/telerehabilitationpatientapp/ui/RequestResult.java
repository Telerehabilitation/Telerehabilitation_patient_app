package com.example.telerehabilitationpatientapp.ui;

import androidx.annotation.Nullable;

public class RequestResult {
    @Nullable
    private Boolean success;
    @Nullable
    private Integer error;

    public RequestResult(@Nullable Integer error) {
        this.error = error;
    }

    public RequestResult(@Nullable Boolean success) {
        this.success = success;
    }

    @Nullable
    public Boolean getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
