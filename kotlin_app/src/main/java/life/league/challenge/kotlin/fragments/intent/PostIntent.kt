package life.league.challenge.kotlin.fragments.intent

sealed class PostIntent {
    data class LoginIntent(val name:String,val password:String) : PostIntent()
    object FetchPosts : PostIntent()
}