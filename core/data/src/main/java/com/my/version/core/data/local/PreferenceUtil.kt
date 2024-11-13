package com.my.version.core.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val preference = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)

    var idToken: String
        get() = preference.getString(ID_TOKEN, DEFAULT_STRING).toString()
        set(value) = preference.edit().putString(ID_TOKEN, value).apply()

    companion object {
        private const val PREFERENCE_KEY = "my_version_pref"
        private const val ID_TOKEN = "id_token"
        private const val DEFAULT_STRING = ""
    }
}