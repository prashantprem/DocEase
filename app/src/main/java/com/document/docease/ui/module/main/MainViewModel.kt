package com.document.docease.ui.module.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.document.docease.data.Resource
import com.document.docease.utils.Constant
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


    private val _allFiles = MutableLiveData<Resource<List<File>>>()
    val allFiles: LiveData<Resource<List<File>>> get() = _allFiles

    private val _pdfFiles = MutableLiveData<Resource<List<File>>>()
    val pdfFiles: LiveData<Resource<List<File>>> get() = _pdfFiles

    private val _wordFiles = MutableLiveData<Resource<List<File>>>()
    val wordFiles: LiveData<Resource<List<File>>> get() = _wordFiles

    private val _excelFiles = MutableLiveData<Resource<List<File>>>()
    val excelFiles: LiveData<Resource<List<File>>> get() = _excelFiles

    private val _pptFiles = MutableLiveData<Resource<List<File>>>()
    val pptFiles: LiveData<Resource<List<File>>> get() = _pptFiles


    private var allOfficeFile: MutableList<File> = mutableListOf()
    private var allPdfFiles: MutableList<File> = mutableListOf()
    private var allWordFiles: MutableList<File> = mutableListOf()
    private var allExcelFiles: MutableList<File> = mutableListOf()
    private var allPptFiles: MutableList<File> = mutableListOf()
    private var allTextFiles: MutableList<File> = mutableListOf()


    init {
        viewModelScope.launch {
            delay(3000)
            showSplash = false
        }
    }


    fun getAllFiles() {
        CoroutineScope(Dispatchers.IO).launch {
            _allFiles.postValue(Resource.Loading())
            _pdfFiles.postValue(Resource.Loading())
            _wordFiles.postValue(Resource.Loading())
            _excelFiles.postValue(Resource.Loading())
            _pptFiles.postValue(Resource.Loading())
            async {
                getAllFiles(Constant.dir)
            }.await()
            _allFiles.postValue(Resource.Success(allOfficeFile))
            _pdfFiles.postValue(Resource.Success(allPdfFiles))
            _wordFiles.postValue(Resource.Success(allWordFiles))
            _excelFiles.postValue(Resource.Success(allExcelFiles))
            _pptFiles.postValue(Resource.Success(allPptFiles))

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
        when {
            file.name.endsWith(".pdf") -> {
                allPdfFiles.add(file)
            }

            file.name.endsWith(".xls") || file.name.endsWith(".xlsx") -> {
                allExcelFiles.add(file)
            }

            file.name.endsWith(".ppt") || file.name.endsWith(".pptx") -> {
                allPptFiles.add(file)
            }

            file.name.endsWith(".docb") || file.name.endsWith(".docx") || file.name.endsWith(".doc") || file.name.endsWith(
                ".dotx"
            ) -> {
                allWordFiles.add(file)
            }

            file.name.endsWith(".txt") -> {
                allTextFiles.add(file)
            }
        }
    }


}