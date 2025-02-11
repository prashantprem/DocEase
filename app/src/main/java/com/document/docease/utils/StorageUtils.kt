package com.document.docease.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.document.docease.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.util.*
import javax.inject.Inject


class StorageUtils @Inject constructor(private val context: Context) {
    fun saveRecent(favorites: List<File>?) {
        try {
            val editor: SharedPreferences.Editor
            val settings: SharedPreferences =
                context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
            editor = settings.edit()
            val gson: Gson = GsonBuilder()
                .registerTypeAdapter(File::class.java, FileTypeAdapter())
                .create()
            val jsonFavorites: String = gson.toJson(favorites)
            editor.putString(PrefKeys.keyRecent, jsonFavorites)
            editor.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addRecent(product: File) {
        var isExistFile = false
        var favorites: MutableList<File>?
        favorites = getRecent()
        if (favorites == null) {
            favorites = ArrayList()
            favorites.add(product)
            saveRecent(favorites)
        } else if (favorites.size == 0) {
            favorites.add(product)
            saveRecent(favorites)
        } else if (favorites.size > 0) {
            for (i in favorites.indices) {
                if (product.absolutePath == favorites[i].absolutePath) {
                    isExistFile = true
                }
            }
            if (!isExistFile) {
                favorites.add(0, product)
                saveRecent(favorites)
                Log.e("xxx", "addRecent: ")
            } else {
                favorites.remove(product)
                favorites.add(0, product)
                saveRecent(favorites)
                Log.e("xxx", "addRecent:exist ")
            }
        }
    }

    fun removeRecent(product: File) {
        val favorites = getRecent()
        if (favorites != null) {
            for (i in favorites.indices) {
                if (product.absolutePath == favorites[i].absolutePath) {
                    favorites.removeAt(i)
                }
            }
            saveRecent(favorites)
        }
    }

    fun getRecent(): ArrayList<File>? {
        var favorites: List<File>?
        val settings: SharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_FILE_NAME,
            Context.MODE_PRIVATE
        )
        try {
            if (settings.contains(PrefKeys.keyRecent)) {
                val jsonFavorites = settings.getString(PrefKeys.keyRecent, null)
                val gson: Gson = GsonBuilder()
                    .registerTypeAdapter(File::class.java, FileTypeAdapter())
                    .create()
                val favoriteItems: Array<File> = gson.fromJson(
                    jsonFavorites,
                    Array<File>::class.java
                )
                favorites = listOf(*favoriteItems)
                favorites = ArrayList(favorites)
            } else {
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return favorites
    }

    private fun saveBookmark(favorites: List<File>) {
        try {
            val editor: SharedPreferences.Editor
            val settings: SharedPreferences = context.getSharedPreferences(
                SHARED_PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE
            )
            editor = settings.edit()
            val gson: Gson = GsonBuilder()
                .registerTypeAdapter(File::class.java, FileTypeAdapter())
                .create()
            val jsonFavorites: String = gson.toJson(favorites)
            editor.putString(PrefKeys.keyBookMarks, jsonFavorites)
            editor.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addBookmark(product: File) {
        var isExistFile = false
        var favorites: MutableList<File>?
        favorites = getBookmark()
        if (favorites == null) {
            favorites = ArrayList()
            favorites.add(product)
            saveBookmark(favorites)
            Toast.makeText(
                context,
                context.resources.getString(R.string.toast_added_bookmark),
                Toast.LENGTH_SHORT
            ).show()
        } else if (favorites.size == 0) {
            favorites.add(product)
            saveBookmark(favorites)
            Toast.makeText(
                context,
                context.resources.getString(R.string.toast_added_bookmark),
                Toast.LENGTH_SHORT
            ).show()
        } else if (favorites.size > 0) {
            for (i in favorites.indices) {
                if (product.absolutePath == favorites[i].absolutePath) {
                    isExistFile = true
                }
            }
            if (!isExistFile) {
                favorites.add(0, product)
                saveBookmark(favorites)
                Log.e("xxx", "addBookmark: ")
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.toast_added_bookmark),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.toast_file_added),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun removeBookmark(product: File) {
        val favorites = getBookmark()
        if (favorites != null) {
            val iterator = favorites.iterator()
            while (iterator.hasNext()) {
                val file = iterator.next()
                if (product.absolutePath == file.absolutePath) {
                    iterator.remove()
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.toast_removed_file),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            saveBookmark(favorites)
        }
    }

    fun getBookmark(): ArrayList<File>? {
        var favorites: List<File>?
        val settings: SharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_FILE_NAME,
            Context.MODE_PRIVATE
        )
        try {
            if (settings.contains(PrefKeys.keyBookMarks)) {
                val jsonFavorites = settings.getString(PrefKeys.keyBookMarks, null)
                val gson: Gson = GsonBuilder()
                    .registerTypeAdapter(File::class.java, FileTypeAdapter())
                    .create()
                val favoriteItems: Array<File> = gson.fromJson(
                    jsonFavorites,
                    Array<File>::class.java
                )
                favorites = listOf(*favoriteItems)
                favorites = ArrayList(favorites)
            } else {
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return favorites
    }
}


