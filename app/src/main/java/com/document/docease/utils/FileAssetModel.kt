package com.document.docease.utils

import android.graphics.Bitmap


class FileAssetModel {
    var mPath: String
    var mIcon: String
    var mType: String
    var mPath2: String? = null
    var mBitmap: Bitmap?

    constructor(mPath: String, mIcon: String, mType: String) {
        this.mPath = mPath
        this.mIcon = mIcon
        this.mType = mType
        mBitmap = null
    }

    constructor(mPath: String, mIcon: String, mType: String, mPath2: String?) {
        this.mPath = mPath
        this.mIcon = mIcon
        this.mType = mType
        this.mPath2 = mPath2
        mBitmap = null
    }

    fun getmPath(): String {
        return mPath
    }

    fun setmPath(mPath: String) {
        this.mPath = mPath
    }

    fun getmIcon(): String {
        return mIcon
    }

    fun setmIcon(mIcon: String) {
        this.mIcon = mIcon
    }

    fun getmType(): String {
        return mType
    }

    fun setmType(mType: String) {
        this.mType = mType
    }

    fun getmPath2(): String? {
        return mPath2
    }

    fun setmPath2(mPath2: String?) {
        this.mPath2 = mPath2
    }
}
