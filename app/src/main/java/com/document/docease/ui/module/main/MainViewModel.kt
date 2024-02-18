package com.document.docease.ui.module.main

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.document.docease.data.BaseViewModel
import com.document.docease.data.Resource
import com.document.docease.ui.components.piechart.DocumentCount
import com.document.docease.ui.module.filescreen.FileType
import com.document.docease.utils.Constant
import com.document.docease.utils.StorageUtils
import com.document.docease.utils.Utility.isSupportedFileType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val storageUtils: StorageUtils
) : BaseViewModel() {


    var showSplash by mutableStateOf(true)

    var isRefreshing by mutableStateOf(false)


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

    private val _documentCount = MutableLiveData<Resource<DocumentCount>>()
    val documentCount: LiveData<Resource<DocumentCount>> get() = _documentCount


    private val _filteredFiles = MutableLiveData<List<File>>()
    val filteredFiles: LiveData<List<File>> get() = _filteredFiles


    private var allOfficeFile: MutableList<File> = mutableListOf()
    private var allPdfFiles: MutableList<File> = mutableListOf()
    private var allWordFiles: MutableList<File> = mutableListOf()
    private var allExcelFiles: MutableList<File> = mutableListOf()
    private var allPptFiles: MutableList<File> = mutableListOf()
    private var allTextFiles: MutableList<File> = mutableListOf()

    init {

    }


    fun getAllFiles(context: Context? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            initFileLoading()
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                async {
                    getAllFilesUsingMediaStore(context!!)
                }.await()
            } else {
                allOfficeFile.clear()
                allPdfFiles.clear()
                allExcelFiles.clear()
                allWordFiles.clear()
                allPptFiles.clear()
                allTextFiles.clear()
                async {
                    getAllFilesLegacy(Constant.dir)
                }.await()
            }
            updateFilesAfterLoading(context == null)
        }
    }

    private fun getAllFilesUsingMediaStore(context: Context) {
        val filesToLoad = arrayOf(FileType.PDF, FileType.WORD, FileType.EXCEL, FileType.P_POINT)
        for (fileType in filesToLoad) {
            fetchFilesUsingMediaStore(context, fileType)
        }
    }

    private fun initFileLoading() {
        CoroutineScope(Dispatchers.IO).launch {
            _allFiles.postValue(Resource.Loading())
            _pdfFiles.postValue(Resource.Loading())
            _wordFiles.postValue(Resource.Loading())
            _excelFiles.postValue(Resource.Loading())
            _pptFiles.postValue(Resource.Loading())
            _documentCount.postValue(Resource.Loading())
        }
    }

    private fun updateFilesAfterLoading(isSortRequired: Boolean = true) {
        CoroutineScope(Dispatchers.IO).launch {
            isRefreshing = false
            if (isSortRequired) {
                allPdfFiles.sortByDescending { it.lastModified() }
                allWordFiles.sortByDescending { it.lastModified() }
                allExcelFiles.sortByDescending { it.lastModified() }
                allPptFiles.sortByDescending { it.lastModified() }
            }
            _allFiles.postValue(Resource.Success(allOfficeFile))
            _pdfFiles.postValue(Resource.Success(allPdfFiles))
            _wordFiles.postValue(Resource.Success(allWordFiles))
            _excelFiles.postValue(Resource.Success(allExcelFiles))
            _pptFiles.postValue(Resource.Success(allPptFiles))
            _documentCount.postValue(
                Resource.Success(
                    DocumentCount(
                        total = allOfficeFile.size.toFloat(),
                        pdfCount = allPdfFiles.size.toFloat(),
                        wordCount = allWordFiles.size.toFloat(),
                        excelCount = allExcelFiles.size.toFloat(),
                        pptCount = allPptFiles.size.toFloat()
                    )
                )
            )
        }
    }


    private fun getAllFilesLegacy(dir: File) {
        val listFile = dir.listFiles()
        if (listFile != null && listFile.isNotEmpty()) {
            for (file in listFile) {
                if (file.isDirectory) {
                    getAllFilesLegacy(file)
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
        when {
            file.name.endsWith(".pdf") -> {
                allPdfFiles.add(file)
                allOfficeFile.add(file)
            }

            file.name.endsWith(".xls") || file.name.endsWith(".xlsx") -> {
                allExcelFiles.add(file)
                allOfficeFile.add(file)
            }

            file.name.endsWith(".ppt") || file.name.endsWith(".pptx") -> {
                allPptFiles.add(file)
                allOfficeFile.add(file)
            }

            file.name.endsWith(".docb") || file.name.endsWith(".docx") || file.name.endsWith(".doc") || file.name.endsWith(
                ".dotx"
            ) -> {
                allWordFiles.add(file)
                allOfficeFile.add(file)
            }

            file.name.endsWith(".txt") -> {
                allTextFiles.add(file)
                //not adding texts to all files now
            }
        }
    }

    fun getRecentFiles(): List<File>? {
        return storageUtils.getRecent()
    }

    fun getFavouriteFiles(): List<File>? {
        return storageUtils.getBookmark()
    }

    fun addToFavourites(file: File) {
        if (isFavourite(file)) {
            storageUtils.removeBookmark(file)
        } else {
            storageUtils.addBookmark(file)
        }
    }

    fun isFavourite(file: File): Boolean {
        val allFavourites = getFavouriteFiles() ?: return false
        return allFavourites.contains(file)
    }

    fun searchFile(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val filteredList = mutableListOf<File>()
            if (query.isNotEmpty()) {
                try {
                    for (item in allOfficeFile) {
                        if (item.name.lowercase(Locale.getDefault())
                                .contains(query.lowercase(Locale.getDefault()))
                        ) {
                            filteredList.add(item)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            _filteredFiles.postValue(filteredList)
        }
    }

    private fun fetchFilesUsingMediaStore(context: Context, fileType: FileType) {
        try {
            val fileList = mutableListOf<File>()
            val projection =
                arrayOf(
                    MediaStore.Files.FileColumns._ID,
                    MediaStore.Files.FileColumns.DATA,
                    MediaStore.Files.FileColumns.DISPLAY_NAME,
                    MediaStore.Files.FileColumns.DATE_MODIFIED,
                    MediaStore.Files.FileColumns.MIME_TYPE,
                    MediaStore.Files.FileColumns.SIZE
                )
            val sortOrder = MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
            val selectionArgs = when (fileType) {
                FileType.PDF -> arrayOf(MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf"))
                FileType.WORD -> {
                    arrayOf(
                        MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc"),
                        MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx"),
                    )
                }

                FileType.EXCEL -> {
                    arrayOf(
                        MimeTypeMap.getSingleton().getMimeTypeFromExtension("xls"),
                        MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx"),
                    )
                }

                FileType.P_POINT -> {
                    arrayOf(
                        MimeTypeMap.getSingleton().getMimeTypeFromExtension("ppt"),
                        MimeTypeMap.getSingleton().getMimeTypeFromExtension("pptx"),
                    )
                }
            }

            val selection =
                MediaStore.Files.FileColumns.MIME_TYPE + " IN (" + selectionArgs.joinToString { "?" } + ")"
            val collection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                MediaStore.Files.getContentUri("external")
            }

            context.contentResolver.query(
                collection,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )
                .use { cursor ->
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            val columnData: Int =
                                cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                            val columnName: Int =
                                cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
                            do {
                                fileList.add(File(cursor.getString(columnData)))
                                Log.d(
                                    "LoadingFile",
                                    "${fileType.name}: " + cursor.getString(columnData)
                                )
                            } while (cursor.moveToNext())
                        }
                    }
                }
            when (fileType) {
                FileType.PDF -> {
                    allPdfFiles = fileList
                    allOfficeFile.addAll(fileList)

                }

                FileType.WORD -> {
                    allWordFiles = fileList
                    allOfficeFile.addAll(fileList)

                }

                FileType.EXCEL -> {
                    allExcelFiles = fileList
                    allOfficeFile.addAll(fileList)

                }

                FileType.P_POINT -> {
                    allPptFiles = fileList
                    allOfficeFile.addAll(fileList)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun refresh(context: Context) {
        isRefreshing = true
        getAllFiles(context)
    }

}