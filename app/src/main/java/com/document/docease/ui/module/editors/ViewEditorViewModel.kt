package com.document.docease.ui.module.editors

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.document.docease.utils.Constant.fontFamilyList
import com.document.docease.utils.Constant.fontSizeList

import com.document.docease.utils.StorageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ViewEditorViewModel @Inject constructor(
    private val storageUtils: StorageUtils
):ViewModel() {
    private val fontSizeMutableLiveData = MutableLiveData<ArrayList<String>>()
    val fontSizeLiveData = fontSizeMutableLiveData

    private val fontFamilyMutableLiveData = MutableLiveData<ArrayList<String>>()
    val fontFamilyLiveData = fontFamilyMutableLiveData

    fun getFontSizeList() {
        viewModelScope.launch {
            fontSizeMutableLiveData.postValue(fontSizeList)
        }
    }

    fun getFontFamilyList() {
        viewModelScope.launch {
            fontFamilyMutableLiveData.postValue(fontFamilyList)
        }
    }

    fun addFilesToRecent(context: Context, mFile: File) {
        viewModelScope.launch {
            storageUtils.addRecent(context,mFile)
        }
    }

}