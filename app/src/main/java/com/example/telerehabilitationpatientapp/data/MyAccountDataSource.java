package com.example.telerehabilitationpatientapp.data;

import android.content.Context;
import android.content.SharedPreferences;
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


public class MyAccountDataSource {

    private SharedPreferences sharedPreferences;

    public void getUserInfo(Context context, final ServerCallback serverCallback) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        DataSource dataSource = new DataSource();
        String url = dataSource.getUrl(new String[]{"my-account"});
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        try {
                            editor.putString("username", response.getString("username"));
                            editor.putString("first_name", response.getString("first_name"));
                            editor.putString("last_name", response.getString("last_name"));
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
        ) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                String token = sharedPreferences.getString("TokenKey", "NO TOKEN");
                if (!token.equals("NO TOKEN")) {
                    headers.put("Authorization", " Token " + token);
                }
                Log.d("Token", token);
                Log.d("Header", headers.toString());
                return headers;
            }
        };

        requestQueue.add(request);
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
