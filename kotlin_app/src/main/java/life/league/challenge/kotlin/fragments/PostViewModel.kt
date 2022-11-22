package life.league.challenge.kotlin.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.utils.SessionManger
import life.league.challenge.kotlin.api.Resource
import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Users
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository,
    private val sessionManger: SessionManger
) : ViewModel() {
    private   val TAG = "PostViewModel"
    val personalDetailsData: MutableLiveData<Resource<ArrayList<Users>>> =
        MutableLiveData<Resource<ArrayList<Users>>>()

    val loginData: MutableLiveData<Resource<Account>> = MutableLiveData<Resource<Account>>()

    fun getPersonalDetails() = viewModelScope.launch {
        val headerMap = HashMap<String, String>()
        headerMap["x-access-token"] = "${sessionManger.get(sessionManger.apiKey)}"
        headerMap["accept"] = "application/json"
        repository.getPostList(headerMap).let {
            if (it.isSuccessful) {
                personalDetailsData.postValue(Resource.success(it.body()))
            } else {
                personalDetailsData.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }


    fun login(username: String, password: String) = viewModelScope.launch {
        repository.login(username = username, password = password).let {
            Log.e(TAG, "login: $it")
            try {
                if (it.isSuccessful) {
                    Log.e(TAG, "login: isSuccessful", )
                    var data = it.body()
                    sessionManger.save(sessionManger.apiKey, data?.apiKey)
                    loginData.postValue(Resource.success(data))
                    Log.e(TAG, "login: $data", )
                } else {
                    Log.e(TAG, "login: error", )
                    loginData.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                Log.e(TAG, "login: ${e.message}")
                loginData.postValue(Resource.error(it.errorBody().toString(), null))

            }

        }

    }
}