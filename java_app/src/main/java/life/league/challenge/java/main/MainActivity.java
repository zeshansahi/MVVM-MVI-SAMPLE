package life.league.challenge.java.main;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import life.league.challenge.java.R;
import life.league.challenge.java.api.OnRequestCallback;
import life.league.challenge.java.api.Service;
import life.league.challenge.java.model.Account;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();


        Service.login("Hello", "World", new OnRequestCallback<Account>() {
            @Override
            public void onSuccess(Account response) {
                Log.v(TAG, response.getApiKey());
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });
    }

}
