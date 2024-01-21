package com.document.docease.ui.module.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.document.docease.utils.Constants
import com.document.docease.utils.Utility.isSupportedFileType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {


    var showSplash by mutableStateOf(true)
        private set


    private val _allFiles = MutableLiveData<List<File>>()
    val allFiles: LiveData<List<File>> get() = _allFiles

    private var allOfficeFile: MutableList<File> = mutableListOf()



    init {
        viewModelScope.launch {
            delay(3000)
            showSplash = false
        }
    }


    fun getAllFiles() {
        CoroutineScope(Dispatchers.IO).launch {
             async {
                getAllFiles(Constants.dir)
            }.await()
            _allFiles.postValue(allOfficeFile)
        }
    }



    private fun getAllFiles(dir: File) {
        val listFile = dir.listFiles()
        if (listFile != null && listFile.isNotEmpty()) {
            for (file in listFile) {
                if (file.isDirectory) {
                    getAllFiles(file)
                } else {
                    val name = file.name
                    if (isSupportedFileType(name)) {
                        handleSupportedFile(file)
                    }
                }
            }
        }
    }


    private fun handleSupportedFile(file: File) {
        allOfficeFile.add(file)
//        _allFiles.postValue(allOfficeFile)
        when {
            file.name.endsWith(".pdf") -> {
                // Handle PDF file
                // mPdfFiles.add(file)
                // pdfFilesUpdated.postValue(true)
            }

            file.name.endsWith(".xls") || file.name.endsWith(".xlsx") -> {
                // Handle Excel file
                // mExcelFiles.add(file)
                // excelFilesUpdated.postValue(true)
            }

            file.name.endsWith(".ppt") || file.name.endsWith(".pptx") -> {
                // Handle PowerPoint file
                // mPowerPointFiles.add(file)
                // powerPointFilesUpdated.postValue(true)
            }

            file.name.endsWith(".docb") || file.name.endsWith(".docx") || file.name.endsWith(".doc") || file.name.endsWith(
                ".dotx"
            ) -> {
                // Handle Word file
                // mWordFiles.add(file)
                // wordFilesUpdated.postValue(true)
            }

            file.name.endsWith(".txt") -> {
                // Handle Text file
                // mListTxtFile.add(file)
                // textFilesUpdated.postValue(true)
            }
        }
    }


}