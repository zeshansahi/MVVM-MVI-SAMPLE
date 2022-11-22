package life.league.challenge.java.api;


import life.league.challenge.java.model.Account;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Api {

    @GET("login")
    Call<Account> login(@Header("Authorization") String credentials);

}
