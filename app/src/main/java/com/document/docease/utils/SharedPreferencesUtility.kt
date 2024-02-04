package com.document.docease.utils

import android.content.Context


object SharedPreferencesUtility {
    private const val NAME_PREF = "receive_shared_pre"
    const val CAMERA_PERMISSION_PROMPT_COUNT = "CAMERA_PERMISSION_PROMPT_COUNT"
    const val CAMERA_PERMISSION_PROMPT = "CAMERA_PERMISSION"



    fun saveString(context: Context, key: String?, value: String?) {
        val preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(key, value).apply()
    }

    fun getSavedString(context: Context, key: String?, defaultValue: String?): String? {
        val preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
        return preferences.getString(key, defaultValue)
    }

    fun saveBoolean(context: Context, key: String, value: Boolean) {
        val preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean(key, value).apply()
    }

    fun getSavedBoolean(context: Context, key: String, defaultValue: Boolean): Boolean {
        val preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
        return preferences.getBoolean(key, defaultValue)
    }

    fun saveInt(context: Context, key: String, value: Int) {
        val preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt(key, value).apply()
    }

    fun getSavedInt(context: Context, key: String, defaultValue: Int): Int {
        val preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
        return preferences.getInt(key, defaultValue)
    }


}
