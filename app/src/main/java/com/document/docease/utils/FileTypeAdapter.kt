package com.document.docease.utils

import android.util.Log
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.File
import java.io.IOException


class FileTypeAdapter : TypeAdapter<File?>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter, file: File?) {
        if (file == null) {
            out.nullValue()
        } else {
            out.value(file.toString())
        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): File? {
        return if (`in`.peek() == JsonToken.NULL) {
            `in`.nextNull()
            null
        } else {
            Log.d("testingRecent", `in`.toString())
            File(`in`.nextString())
        }
    }
}