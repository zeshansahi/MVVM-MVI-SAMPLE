package life.league.challenge.java.api;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import life.league.challenge.java.model.Account;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static final String HOST = "https://engineering.league.dev/challenge/api/";
    private static final String TAG = "Service";

    private static Api api;


    private static void initApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }
    /// There is no requirement to use threads You can use any library which can make life easier
    public static void login(String username, String password, final OnRequestCallback<Account> callback) {
        if (api == null)
            initApi();

        final String credentials = username + ":" + password;
        final String auth = "Basic " + Base64.encodeToString(credentials.getBytes(),
                Base64.NO_WRAP);

        new Thread(() -> {
            try {
                final Response<Account> response = api.login(auth).execute();
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure("Failed call to login");
                    }
                });
            } catch (IOException e) {
                Log.e(TAG, "Login failed", e);
                callback.onFailure(e.getMessage());
            }
        }).start();
    }

}
