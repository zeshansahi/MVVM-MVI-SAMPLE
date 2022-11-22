package life.league.challenge.kotlin.fragments


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import life.league.challenge.kotlin.utils.SessionManger
import life.league.challenge.kotlin.api.Resource
import life.league.challenge.kotlin.fragments.intent.PostIntent
import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Users
import life.league.challenge.kotlin.states.PostState
import java.util.HashMap
import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository,
    private val sessionManger: SessionManger
) : ViewModel() {
    private val TAG = "PostViewModel"

    /*  val personalDetailsData: MutableLiveData<Resource<ArrayList<Users>>> =
          MutableLiveData<Resource<ArrayList<Users>>>()

      val loginData: MutableLiveData<Resource<Account>> = MutableLiveData<Resource<Account>>()

  */
    val postIntent = Channel<PostIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<PostState>(PostState.Idle)
    val state: StateFlow<PostState>
        get() = _state

    init {
        handleIntent()
    }


    private fun handleIntent() {
        viewModelScope.launch {
            postIntent.consumeAsFlow().collect {
                when (it) {
                    is PostIntent.LoginIntent -> login(it.name, it.password)
                    is PostIntent.FetchPosts -> fetchUser()
                }
            }
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            val headerMap = HashMap<String, String>()
            headerMap["x-access-token"] = "${sessionManger.get(sessionManger.apiKey)}"
            headerMap["accept"] = "application/json"
            _state.value = PostState.Loading
            _state.value = try {
                repository.getPostList(headerMap).let {
                    if (it.isSuccessful) {
                        PostState.Posts(it.body()!!)
                    } else {
                        PostState.Error(it.errorBody().toString())
                    }
                }
            } catch (e: Exception) {
                PostState.Error(e.localizedMessage)
            }

        }
    }


    private fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.value = PostState.Loading
            _state.value = try {
                repository.login(username, password).let {
                    if (it.isSuccessful) {
                        var data = it.body()
                        sessionManger.save(sessionManger.apiKey, data?.apiKey)
                        Log.e(TAG, "login: $data")
                        PostState.LoginSuccess(it.body()!!)
                    } else {
                        PostState.Error(it.errorBody().toString())
                    }
                }
            } catch (e: Exception) {
                PostState.Error(e.localizedMessage)
            }
        }
    }


/*
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

*/

/*    fun login(username: String, password: String) = viewModelScope.launch {
        repository.login(username = username, password = password).let {
            Log.e(TAG, "login: $it")
            try {
                if (it.isSuccessful) {
                    Log.e(TAG, "login: isSuccessful")
                    var data = it.body()
                    sessionManger.save(sessionManger.apiKey, data?.apiKey)
                    loginData.postValue(Resource.success(data))
                    Log.e(TAG, "login: $data")
                } else {
                    Log.e(TAG, "login: error")
                    loginData.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                Log.e(TAG, "login: ${e.message}")
                loginData.postValue(Resource.error(it.errorBody().toString(), null))

            }

        }

    }*/
}