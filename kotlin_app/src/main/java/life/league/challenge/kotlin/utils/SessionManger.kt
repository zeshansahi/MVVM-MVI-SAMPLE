package life.league.challenge.kotlin.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManger @Inject constructor(@ApplicationContext context: Context?) {
    val apiKey="api_key"


    // Shared pref file name
    private val PREF_NAME = "challenge-app"
    // Shared Preferences
    var pref: SharedPreferences? = null

    // Editor for Shared preferences
    var editor: SharedPreferences.Editor? = null

    // Shared pref mode
    var PRIVATE_MODE = 0

    init {
        pref = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref?.edit()
    }
    fun save(key:String?,value:String?){
        editor!!.putString(key, value!!)
        editor!!.commit()
    }
    fun get(key:String?): String? {
        return pref!!.getString(key, "")
    }
}