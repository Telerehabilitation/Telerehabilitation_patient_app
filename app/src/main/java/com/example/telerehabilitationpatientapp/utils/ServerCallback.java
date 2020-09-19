package com.example.telerehabilitationpatientapp.utils;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ServerCallback {
    void onSuccess(JSONObject response);
    void onError(VolleyError error);
    void onError();
}
