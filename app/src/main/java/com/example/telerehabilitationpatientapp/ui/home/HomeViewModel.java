package com.example.telerehabilitationpatientapp.ui.home;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.VolleyError;
import com.example.telerehabilitationpatientapp.R;
import com.example.telerehabilitationpatientapp.data.LoginDataSource;
import com.example.telerehabilitationpatientapp.data.MyAccountDataSource;
import com.example.telerehabilitationpatientapp.utils.ServerCallback;

import org.json.JSONObject;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<MyAccountResult> myAccountResult = new MutableLiveData<>();
    private MyAccountDataSource myAccountDataSource;

    HomeViewModel(MyAccountDataSource myAccountDataSource) {
        this.myAccountDataSource = myAccountDataSource;
    }

    public LiveData<MyAccountResult> getMyAccountResult() {
        return myAccountResult;
    }

    public void myAccount(Context context) {
        // can be launched in a separate asynchronous job
        myAccountDataSource.getUserInfo(context, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                myAccountResult.setValue(new MyAccountResult(true));
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("error", error.toString());
                myAccountResult.setValue(new MyAccountResult(error.networkResponse.statusCode));
            }

            @Override
            public void onError() {
                myAccountResult.setValue(new MyAccountResult(0));
            }
        });
    }
}

