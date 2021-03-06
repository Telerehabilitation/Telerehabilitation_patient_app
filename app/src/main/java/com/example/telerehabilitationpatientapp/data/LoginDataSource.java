package com.example.telerehabilitationpatientapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.telerehabilitationpatientapp.data.model.DataSource;
import com.example.telerehabilitationpatientapp.utils.ServerCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginDataSource {

    private SharedPreferences sharedPreferences;

    public void login(String username, String password, Context context, final ServerCallback serverCallback) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final Editor editor = sharedPreferences.edit();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        DataSource dataSource = new DataSource();
        String url = dataSource.getUrl(new String[]{"api-token"});
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", username);
        requestBody.put("password", password);
        JSONObject JSONRequestBody = new JSONObject(requestBody);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, JSONRequestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        try {
                            editor.putString("TokenKey", response.getString("token"));
                            editor.apply();
                            serverCallback.onSuccess(response);
                        } catch (JSONException e) {
                            serverCallback.onError();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        serverCallback.onError(error);
                    }
                }
        );

        requestQueue.add(request);
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
