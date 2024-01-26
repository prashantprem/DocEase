package com.document.docease.ui.module.editors

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.ui.tooling.PreviewActivity
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.document.docease.ui.module.main.MainActivity
import com.document.docease.utils.Constant
import com.document.docease.utils.StorageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * Created by Amandeep Chauhan
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storageUtils: StorageUtils
) : ViewModel() {
    private val word: MutableLiveData<String> = MediatorLiveData()
    private val excel: MutableLiveData<String> = MediatorLiveData()
    val isDataLoaded: MutableLiveData<Boolean> = MediatorLiveData()
    private var recentFilesMutableLiveData = MutableLiveData<ArrayList<File>>()
    var recentFilesLiveData = recentFilesMutableLiveData
    var bookMarkUpdated = MutableLiveData<Boolean>()
    var recentUpdated = MutableLiveData<Boolean>()

    private var mutableBookMarksFilesList = MutableLiveData<ArrayList<File>>()
    var bookMarksFilesLiveData = mutableBookMarksFilesList


    fun getWord(): MutableLiveData<String> {
        return word
    }

    fun getIsDataLoaded(): MutableLiveData<Boolean> {
        return isDataLoaded
    }

    fun getExcel(): MutableLiveData<String> {
        return excel
    }

    fun getPpt(): MutableLiveData<String> {
        return ppt
    }

    fun getPdf(): MutableLiveData<String> {
        return pdf
    }

    @JvmName("getText1")
    fun getText(): MutableLiveData<String> {
        return text
    }

    fun getAll(): MutableLiveData<String> {
        return all
    }

    fun addFilesToBookMarks(context: Context, file: File) {
        viewModelScope.launch {
            bookMarkUpdated.postValue(true)
            storageUtils.addBookmark( file)
        }
    }

    fun getFileBookMarks(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            mutableBookMarksFilesList.postValue(storageUtils.getBookmark())
        }
    }

    fun removeFileBookMark(context: Context, file: File) {
        viewModelScope.launch {
            bookMarkUpdated.postValue(true)
            Log.d("testingFavourite", "called here-4")
            storageUtils.removeBookmark( file)
            getFileBookMarks(context)
        }
    }

    fun getRecentFiles(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            recentFilesMutableLiveData.postValue(storageUtils.getRecent())
        }
    }

    private val ppt: MutableLiveData<String> = MediatorLiveData()
    private val pdf: MutableLiveData<String> = MediatorLiveData()
    val text: MutableLiveData<String> = MediatorLiveData()
    private val all: MutableLiveData<String> = MediatorLiveData()
    fun getScreenshots(): MutableLiveData<String> {
        return screenshots
    }

    private val screenshots: MutableLiveData<String> = MediatorLiveData()
    fun getTriggerSaveAsPdf(): MutableLiveData<Boolean> {
        return triggerSaveAsPdf
    }

    private val triggerSaveAsPdf: MutableLiveData<Boolean> = MediatorLiveData()
    fun getTriggerSave(): MutableLiveData<Boolean> {
        return triggerSave
    }

    private val triggerSave: MutableLiveData<Boolean> = MediatorLiveData()
    fun getTriggerSearch(): MutableLiveData<Boolean> {
        return triggerSearch
    }

    private val triggerSearch: MutableLiveData<Boolean> = MediatorLiveData()

    //trigger style
    private val triggerBold: MutableLiveData<Boolean> = MediatorLiveData()
    private val triggerItalic: MutableLiveData<Boolean> = MediatorLiveData()
    fun getTriggerBold(): MutableLiveData<Boolean> {
        return triggerBold
    }

    fun getTriggerItalic(): MutableLiveData<Boolean> {
        return triggerItalic
    }

    fun getTriggerUnderline(): MutableLiveData<Boolean> {
        return triggerUnderline
    }

    private val triggerUnderline: MutableLiveData<Boolean> = MediatorLiveData()
    fun getTriggerAlignLeft(): MutableLiveData<Boolean> {
        return triggerAlignLeft
    }

    fun getTriggerAlignRight(): MutableLiveData<Boolean> {
        return triggerAlignRight
    }

    fun getTriggerAlignCenter(): MutableLiveData<Boolean> {
        return triggerAlignCenter
    }

    fun getTriggerJustify(): MutableLiveData<Boolean> {
        return triggerJustify
    }

    //trigger Align
    private val triggerAlignLeft: MutableLiveData<Boolean> = MediatorLiveData()
    private val triggerAlignRight: MutableLiveData<Boolean> = MediatorLiveData()
    private val triggerAlignCenter: MutableLiveData<Boolean> = MediatorLiveData()
    private val triggerJustify: MutableLiveData<Boolean> = MediatorLiveData()
    private val triggerUndo: MutableLiveData<Boolean> = MediatorLiveData()
    private val triggerRedo: MutableLiveData<Boolean> = MediatorLiveData()
    private val triggerShare: MutableLiveData<Boolean> = MediatorLiveData()

    fun getTriggerShare(): MutableLiveData<Boolean> {
        return triggerShare;
    }

    fun getTriggerUndo(): MutableLiveData<Boolean> {
        return triggerUndo
    }

    fun getTriggerRedo(): MutableLiveData<Boolean> {
        return triggerRedo
    }

    fun getTriggerInsertImage(): MutableLiveData<Boolean> {
        return triggerInsertImage
    }

    fun getTriggerCameraImage(): MutableLiveData<Boolean> {
        return triggerCameraImage
    }

    //trigger insert images button
    private val triggerInsertImage: MutableLiveData<Boolean> = MediatorLiveData()
    private val triggerCameraImage: MutableLiveData<Boolean> = MediatorLiveData()
    fun getTriggerHighlight(): MutableLiveData<String> {
        return triggerHighlight
    }

    fun setTriggerHighlight(color: String) {
        triggerHighlight.value = color
    }

    //triggerHiglight
    private val triggerHighlight: MutableLiveData<String> = MediatorLiveData()
    fun getTriggerFontColor(): MutableLiveData<String> {
        return triggerFontColor
    }

    private val triggerFontColor: MutableLiveData<String> = MediatorLiveData()
    fun getTriggerFontFamily(): MutableLiveData<String> {
        return triggerFontFamily
    }

    private val triggerFontFamily: MutableLiveData<String> = MediatorLiveData()
    fun getTriggerFontSize(): MutableLiveData<Double> {
        return triggerFontSize
    }

    private val triggerFontSize: MutableLiveData<Double> = MediatorLiveData()

    fun previewFile(activity: Activity, file: File, pageNumber: Int) {
        storageUtils.addRecent( file)
        val fromFile = Uri.fromFile(file)
        val intent = Intent(activity, PreviewActivity::class.java)
        intent.action = Constant.INTENT_ACTION_VIEW
        intent.data = fromFile
        intent.putExtra(Constant.STARTED_FROM_EXPLORER, true)
        intent.putExtra(Constant.START_PAGE, pageNumber)
        intent.putExtra(Constant.FILE_NAME, file.name)
        intent.putExtra(Constant.PREVIEW, true)
        intent.putExtra(Constant.FILE, file)
        if (file.name.endsWith(".pdf")) {
            intent.putExtra("isPdf", true)
        }
        activity.startActivityForResult(intent, MainActivity.CODE_RESULT_BOOKMARK)
    }
}
