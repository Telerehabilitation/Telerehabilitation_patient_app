package com.example.telerehabilitationpatientapp.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.telerehabilitationpatientapp.data.LoginDataSource;
import com.example.telerehabilitationpatientapp.data.Result;
import com.example.telerehabilitationpatientapp.data.model.LoggedInUser;
import com.example.telerehabilitationpatientapp.R;
import com.example.telerehabilitationpatientapp.utils.ServerCallback;

import org.json.JSONObject;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginDataSource loginDataSource;

    LoginViewModel(LoginDataSource loginDataSource) {
        this.loginDataSource = loginDataSource;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password, Context context) {
        // can be launched in a separate asynchronous job
        loginDataSource.login(username, password, context, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                loginResult.setValue(new LoginResult(true));
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("error", error.toString());
                loginResult.setValue(new LoginResult(error.networkResponse.statusCode));
            }

            @Override
            public void onError() {
                loginResult.setValue(new LoginResult(0));
            }
        });
    }

    void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return !username.trim().isEmpty();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null;
    }
}
