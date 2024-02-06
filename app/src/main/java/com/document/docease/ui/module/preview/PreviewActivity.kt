package com.document.docease.ui.module.preview

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.artifex.sonui.AppNUIActivity
import com.document.docease.BuildConfig
import com.document.docease.R
import com.document.docease.ui.components.ads.loadInterstitial
import com.document.docease.ui.components.ads.showInterstitial
import com.document.docease.ui.module.editors.HomeViewModel
import com.document.docease.ui.module.editors.ViewEditorActivity
import com.document.docease.ui.module.editors.ViewEditorViewModel
import com.document.docease.utils.AdUnits
import com.document.docease.utils.Constant
import com.document.docease.utils.FileUtil
import com.document.docease.utils.Utility
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class PreviewActivity
    : AppNUIActivity() {
    private var imvBack: AppCompatImageView? = null
    private var isClickBookmark = false
    private var imvMenuOption: ImageView? = null

    private var mFile: File? = null
    private var popupMenu: PopupMenu? = null
    private var imvEditorShare: AppCompatImageView? = null

    private var showEditInPopup = false
    private var toolbarSearch: AppCompatImageView? = null
    private var isCheckFavorite = false
    private var isFromOutside = true
    private var fileUtil: FileUtil? = null
    var filePath: String? = ""
    private val viewEditorViewModel by viewModels<ViewEditorViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()
    private var imvEdit: ImageView? = null


    @Suppress("DEPRECATION")
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        loadInterstitial(this@PreviewActivity, AdUnits.fileEditInterstitial)
        fileUtil = FileUtil(this)
        imvEditorShare = findViewById(R.id.imv_activity_editor_share)
        imvMenuOption = findViewById(R.id.imv_menu_option)
        toolbarSearch = findViewById(R.id.toolbar_search)
        imvBack = findViewById(R.id.imv_back)
        popupMenu = PopupMenu(this@PreviewActivity, imvMenuOption, Gravity.END)
        popupMenu!!.menuInflater.inflate(R.menu.menu_reading_file_actions, popupMenu!!.menu)
        imvEdit = findViewById(R.id.imv_preview_edit)
        intent?.apply {
            if (extras != null && extras!!.containsKey(Constant.FILE)) {
                isFromOutside = false
                mFile = extras!![Constant.FILE] as File?
                if (Utility.fileType(mFile!!) == 6) {
                    toolbarSearch?.visibility = View.GONE
                }
                if (Utility.fileType(mFile!!) == 6 || Utility.fileType(mFile!!) == 9) {
                    showEditInPopup = true
                }
            }
        }
        if (isFromOutside) {
            onFileOpenedFromOutsideIntent()
        }
        checkIfIsFavourite()
        initClickListeners()
    }

    private fun initClickListeners() {

        if (imvEdit != null) {
            imvEdit!!.setOnClickListener {
                showInterstitial(
                    this@PreviewActivity,
                    AdUnits.fileEditInterstitial,
                    onAdDismissed = {
                        if (isFromOutside) {
                            Utility.openFileFromUri(this, intent.data!!)
                        } else {
                            try {
                                Utility.openFile(this, mFile!!, 0)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Utility.openFileFromUri(this, intent.data!!)
                            }
                        }
                    })
            }
        }


        if (imvBack != null) {
            imvBack!!.setOnClickListener {
                finish()
            }
        }
        if (imvEditorShare != null) {
            imvEditorShare!!.setOnClickListener {
                if (isFromOutside) {
                    homeViewModel.getTriggerShare().value = true
                } else {
                    Utility.shareToAny(mFile!!, this@PreviewActivity)
                }
            }
        }
        if (imvMenuOption != null) {
            imvMenuOption!!.setOnClickListener {
                showPopUpMenu()
            }
        }
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun showPopUpMenu() {
        try {
            popupMenu?.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.share -> {
                        if (isFromOutside) {
                            homeViewModel?.getTriggerShare()?.value = true
                        } else {
                            try {
                                val sharingIntent = Intent(Intent.ACTION_SEND)
                                sharingIntent.type = "file/*"
                                val uri: Uri? = try {
                                    FileProvider.getUriForFile(
                                        this,
                                        BuildConfig.APPLICATION_ID + ".provider",
                                        mFile!!
                                    )
                                } catch (e: Exception) {
                                    Uri.fromFile(mFile)
                                }
                                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri)
                                startActivity(
                                    Intent.createChooser(
                                        sharingIntent,
                                        getString(R.string.share_document)
                                    )
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    R.id.bookmark -> {
                        val finalIsCheckFavorite = booleanArrayOf(isCheckFavorite)
                        isClickBookmark = true
                        setResult(RESULT_OK, intent)
                        if (intent.data != null && intent.data!!.path != null && intent.data!!.path != ""
                        ) {
                            if (isCheckFavorite) {
                                popupMenu?.menu?.findItem(R.id.bookmark)?.icon =
                                    ContextCompat.getDrawable(this, R.drawable.ic_star_bookmark)
                                homeViewModel?.removeFileBookMark(
                                    this, File(
                                        intent.data!!.path!!
                                    )
                                )
                                isCheckFavorite = false
                                finalIsCheckFavorite[0] = false
                            } else {
                                val file = intent.data!!.path?.let { it1 -> File(it1) }
                                val suffix = file?.name?.substring(file.name.lastIndexOf(".") + 1)
                                if (suffix != null) {
                                }
                                popupMenu?.menu?.findItem(R.id.bookmark)?.icon =
                                    ContextCompat.getDrawable(
                                        this,
                                        R.drawable.ic_filled_star_bookmark
                                    )
                                homeViewModel?.addFilesToBookMarks(
                                    this@PreviewActivity, File(
                                        intent.data!!.path!!
                                    )
                                )
                                isCheckFavorite = true
                                finalIsCheckFavorite[0] = true
                            }
                        }
                    }

                    R.id.print -> {
                        try {
                            if (isFromOutside) {
                                Utility.openFileFromUri(this, intent.data!!, true)
                            } else {
                                if (mFile != null) {
                                    Utility.previewFile(this, mFile!!, 0, true)
                                } else {
                                    Toast.makeText(
                                        this@PreviewActivity,
                                        "Not supported!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(
                                this@PreviewActivity,
                                "Not supported!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                true
            }
            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                popupMenu?.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(i: Int, i1: Int, intent: Intent?) {
        if (i == REQUEST_CAMERA_PERMISSIONS) {
            if (i1 == RESULT_OK) {
                Toast.makeText(this, Constant.READY, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, Constant.ERROR, Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(i, i1, intent)
    }

    override fun onKeyDown(var1: Int, var2: KeyEvent): Boolean {
        if (var1 == KeyEvent.KEYCODE_BACK) {
            finish()
        }
        return super.onKeyDown(var1, var2)
    }

    fun logFirebaseEvents(event: String, properties: String, value: String) {
        val bundle = Bundle()
        bundle.putString(properties, value)
    }

    private fun checkIfIsFavourite() {
        if (intent.data != null && intent.data!!.path != null && intent.data!!.path != ""
        ) {
            homeViewModel.getFileBookMarks(this)
            homeViewModel.bookMarksFilesLiveData.observe(this) {
                if (it != null) {
                    if (it.size > 0) {
                        for (i in it.indices) {
                            if (it[i].path == intent.data!!
                                    .path
                            ) {
                                popupMenu!!.menu.findItem(R.id.bookmark).icon =
                                    ContextCompat.getDrawable(
                                        this,
                                        R.drawable.ic_filled_star_bookmark
                                    )
                                isCheckFavorite = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onFileOpenedFromOutsideIntent() {
        try {
            filePath = fileUtil!!.getPath(intent.data!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (filePath != null && filePath != "") {
            mFile = File(filePath!!)
            if (Utility.fileType(mFile!!) == 6) {
                toolbarSearch?.visibility = View.GONE
            }
            if (Utility.fileType(mFile!!) == 6 || Utility.fileType(mFile!!) == 9) {
                showEditInPopup = true
            }
            viewEditorViewModel.addFilesToRecent(this, mFile!!)
        }
        if (!Utility.isDefaultReaderSet(intent.data, this)) {
            if (filePath != null && filePath != "") {
                val args = Bundle()
                args.putString(Constant.INTENT_DATA, intent.data.toString())
                args.putString(Constant.INTENT_FILE_URI, filePath)
                val makeDefaultViewer = MakeDefaultViewer()
                makeDefaultViewer.arguments = args
                makeDefaultViewer.show(supportFragmentManager, "makeDefaultViewer")
            }
        }
    }

    companion object {
        private val TAG = ViewEditorActivity::class.java.name
        const val REQUEST_CAMERA_PERMISSIONS = 0x11111
    }

}