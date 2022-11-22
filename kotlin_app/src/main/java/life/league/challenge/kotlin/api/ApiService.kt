package life.league.challenge.kotlin.api

import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import java.util.HashMap

/**
 * Retrofit API interface definition using coroutines. Feel free to change this implementation to
 * suit your chosen architecture pattern and concurrency tools
 */
interface ApiService {

    @GET("login")
    suspend fun login(@Header("Authorization") credentials: String?): Response<Account>

    @GET("users")
    suspend fun getPostList(
        @HeaderMap header: HashMap<String, String>
    ): Response<ArrayList<Users>>
}

/**
 * Overloaded Login API extension function to handle authorization header encoding
 */
//suspend fun ApiService.login(username: String, password: String)= login()