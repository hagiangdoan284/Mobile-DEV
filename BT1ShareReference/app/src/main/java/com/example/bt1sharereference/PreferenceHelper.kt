package com.example.bt1sharereference

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ShareReference", Context.MODE_PRIVATE)

    fun saveCredentials(username: String, password: String) {
        sharedPreferences.edit().apply {
            putString("USERNAME", username)
            putString("PASSWORD", password)
            apply()
        }
    }

    fun getCredentials(): Pair<String?, String?> {
        val username = sharedPreferences.getString("USERNAME", null)
        val password = sharedPreferences.getString("PASSWORD", null)
        return Pair(username, password)
    }

    fun clearCredentials() {
        sharedPreferences.edit().apply {
            remove("USERNAME")
            remove("PASSWORD")
            apply()
        }
    }
}
