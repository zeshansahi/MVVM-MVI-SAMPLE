package life.league.challenge.kotlin.fragments

import life.league.challenge.kotlin.api.ApiService
import life.league.challenge.kotlin.utils.NetworkHelper
import java.util.HashMap
import javax.inject.Inject

class PostRepository@Inject constructor(val apiService: ApiService,val networkHelper: NetworkHelper){
    suspend fun login(username: String, password: String) = apiService.login("Basic " + android.util.Base64.encodeToString("$username:$password".toByteArray(), android.util.Base64.NO_WRAP))
    suspend fun getPostList(headerMap: HashMap<String, String>) = apiService.getPostList(headerMap)

    fun testFunToCheckNetwork() {
        if (networkHelper.isNetworkConnected()){

        }else{

        }
    }
}