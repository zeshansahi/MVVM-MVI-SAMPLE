package life.league.challenge.kotlin.states

import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Users

sealed class PostState {
    object Idle : PostState()
    object Loading : PostState()
    data class Posts(val user: ArrayList<Users>) : PostState()
    data class LoginSuccess(val user:Account ) : PostState()
    data class Error(val error: String?) : PostState()
}