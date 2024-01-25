package com.document.docease.ui.module.editors

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListPopupWindow
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
import com.artifex.sonui.AppNUIActivity
import com.artifex.sonui.editor.ToolbarButton
import com.artifex.sonui.editor.Utilities
import com.document.docease.R
import com.document.docease.utils.Constant
import com.document.docease.utils.FileUtil
import java.io.File


class ViewEditorActivity : AppNUIActivity() {
    private var imvActivityEditorBookmark: ImageView? = null
    private var imvBack: AppCompatImageView? = null
    private var editorStyle: LinearLayout? = null
    private var editorAlign: LinearLayout? = null
    private var editorInsertImage: LinearLayout? = null
    private var editorHighlight: LinearLayout? = null
    private var editorFontColor: LinearLayout? = null
    private var editorExpand: LinearLayout? = null
    private var editorExpandOptions: LinearLayout? = null
    private var editorExpandFont: LinearLayout? = null
    private var editorHistory: LinearLayout? = null
    private var editorExpandHighlight: LinearLayout? = null
    private var editorExpandAlignRight: AppCompatImageView? = null
    private var editorExpandAlignLeft: AppCompatImageView? = null
    private var editorExpandAlignCenter: AppCompatImageView? = null
    private var editorExpandAlignJustify: AppCompatImageView? = null
    private var toolbarCancel: AppCompatImageView? = null
    private var editorFontFamily: LinearLayout? = null
    private var popupWindow: ListPopupWindow? = null
    private val homeViewModel by viewModels<HomeViewModel>()
    private val viewEditorViewModel by viewModels<ViewEditorViewModel>()
    var filePath: String? = ""
    private var fileUtil: FileUtil? = null
    private var isFromOutside = true
    private var moreToolsPdf: ToolbarButton? = null
    private var moreToolsOther: LinearLayout? = null
    private var saveClickCount = 0
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        fileUtil = FileUtil(this)
        initView()
        val intent = intent
        val extras: Bundle? = intent.extras
        if (extras != null && extras.containsKey(Constant.SHOW_DEFAULT_BS)) {
            isFromOutside = false
        }
        if (isFromOutside) {
            try {
                filePath = fileUtil!!.getPath(getIntent().data!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (filePath != null && filePath != "") {
            val mFile = File(filePath!!)
            viewEditorViewModel.addFilesToRecent(this, mFile)
        }
    }

    private fun initView() {
        editorStyle = findViewById(R.id.imv_activity_editor_style)
        editorHistory = findViewById(R.id.imv_activity_editor_history)
        editorFontFamily = findViewById(R.id.imv_activity_editor_font_family)
        editorAlign = findViewById(R.id.imv_activity_editor_align)
        editorInsertImage = findViewById(R.id.imv_activity_editor_insert_image)
        editorHighlight = findViewById(R.id.imv_activity_editor_highlight)
        editorFontColor = findViewById(R.id.imv_activity_editor_fonts)
        editorExpand = findViewById(R.id.imv_activity_editor_expand)
        editorExpandOptions = findViewById(R.id.editor_expand_options)
        editorExpandHighlight = findViewById(R.id.editor_expand_highlight)
        editorExpandFont = findViewById(R.id.editor_expand_font_color)
        editorExpandAlignRight = findViewById(R.id.editor_expand_align_right)
        editorExpandAlignCenter = findViewById(R.id.editor_expand_align_center)
        editorExpandAlignLeft = findViewById(R.id.editor_expand_align_left)
        editorExpandAlignJustify = findViewById(R.id.editor_expand_align_justify)
        imvActivityEditorBookmark = findViewById(R.id.imv_activity_editor_bookmark)
        toolbarCancel = findViewById(R.id.toolbar_cancel)
        imvBack = findViewById(R.id.imv_back)
        moreToolsOther = findViewById(R.id.imv_tools_fragments)
        moreToolsPdf = findViewById(R.id.tools_button)
        if (moreToolsPdf != null) moreToolsPdf!!.setOnClickListener {

        }

        if (moreToolsOther != null) moreToolsOther!!.setOnClickListener {
            intent.data?.let { uri ->
            }
        }
        if (toolbarCancel != null) toolbarCancel!!.setOnClickListener { finish() }

        if (editorStyle != null) editorStyle!!.setOnClickListener { view -> showStyleListMenu(view) }
        if (editorAlign != null) editorAlign!!.setOnClickListener { view -> showAlignListMenu(view) }
        if (editorHistory != null) editorHistory!!.setOnClickListener { view ->
            showHistoryListMenu(
                view
            )
        }

        if (editorHighlight != null) editorHighlight!!.setOnClickListener {
            if (saveClickCount++ == 0) {
                Handler(Looper.getMainLooper()).postDelayed({
                    Utilities.hideKeyboard(this@ViewEditorActivity)
                    val bundleArgs = Bundle()
                    bundleArgs.putBoolean(Constant.COLOR_HEIGHT_LIGHT, true)
                    val highlightBottomSheetFragment =
                        HighlightBottomSheetFragment.newInstance(
                            param1 = true,
                            param2 = false,
                            showKeyboard = showKeyboardAfterBs()
                        )
                    highlightBottomSheetFragment.show(
                        supportFragmentManager,
                        Constant.HIGHLIGHT_BOTTOM_SHEET_FRAGMENT
                    )
                    saveClickCount = 0
                }, 1000)
            }
        }
        if (editorExpandHighlight != null) editorExpandHighlight!!.setOnClickListener {
            if (saveClickCount++ == 0) {
                Handler(Looper.getMainLooper()).postDelayed({
                    Utilities.hideKeyboard(this@ViewEditorActivity)
                    val bundleArgs = Bundle()
                    bundleArgs.putBoolean(Constant.COLOR_HEIGHT_LIGHT, true)
                    val highlightBottomSheetFragment =
                        HighlightBottomSheetFragment.newInstance(
                            param1 = true,
                            param2 = false,
                            showKeyboard = showKeyboardAfterBs()
                        )
                    highlightBottomSheetFragment.show(
                        supportFragmentManager,
                        Constant.HIGHLIGHT_BOTTOM_SHEET_FRAGMENT
                    )
                    saveClickCount = 0
                }, 1000)
            }
        }
        if (editorFontColor != null) editorFontColor!!.setOnClickListener {
            if (saveClickCount++ == 0) {
                Handler(Looper.getMainLooper()).postDelayed({

                    val bundleArgs = Bundle()
                    bundleArgs.putBoolean(Constant.COLOR_FONT, true)
                    val highlightBottomSheetFragment =
                        HighlightBottomSheetFragment.newInstance(
                            param1 = false,
                            param2 = true,
                            showKeyboardAfterBs()
                        )
                    highlightBottomSheetFragment.show(
                        supportFragmentManager,
                        Constant.HIGHLIGHT_BOTTOM_SHEET_FRAGMENT
                    )

                    saveClickCount = 0
                }, 1000)
            }

        }
        if (editorExpandFont != null) editorExpandFont!!.setOnClickListener {
            Utilities.hideKeyboard(this@ViewEditorActivity)
            val bundleArgs = Bundle()
            bundleArgs.putBoolean(Constant.COLOR_FONT, true)
            val highlightBottomSheetFragment =
                HighlightBottomSheetFragment.newInstance(
                    param1 = false,
                    param2 = true,
                    showKeyboard = showKeyboardAfterBs()
                )
            highlightBottomSheetFragment.show(
                supportFragmentManager,
                Constant.HIGHLIGHT_BOTTOM_SHEET_FRAGMENT
            )
        }
        if (editorExpand != null) editorExpand!!.setOnClickListener {
            Utilities.hideKeyboard(this@ViewEditorActivity)
            if (editorExpandOptions?.visibility == View.GONE) {
                editorExpandOptions!!.visibility = View.VISIBLE
            } else {
                editorExpandOptions?.visibility = View.GONE
            }
        }
        if (editorExpandAlignLeft != null) editorExpandAlignLeft!!.setOnClickListener {
            homeViewModel.getTriggerAlignLeft().setValue(true)
        }
        if (editorExpandAlignRight != null) editorExpandAlignRight!!.setOnClickListener {
            homeViewModel.getTriggerAlignRight().setValue(true)
        }
        if (editorExpandAlignCenter != null) editorExpandAlignCenter!!.setOnClickListener {
            homeViewModel.getTriggerAlignCenter().setValue(true)
        }
        if (editorExpandAlignJustify != null) editorExpandAlignJustify!!.setOnClickListener {
            homeViewModel.getTriggerJustify().setValue(true)
        }
        if (editorFontFamily != null) editorFontFamily!!.setOnClickListener {
            if (saveClickCount++ == 0) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val editFontBottomSheetFragment =
                        EditFontBottomSheetFragment.newInstance(showKeyboard = showKeyboardAfterBs())
                    editFontBottomSheetFragment.show(
                        supportFragmentManager,
                        Constant.EDITFONT_BOTTOM_SHEET_FRAGMENT
                    )
                    saveClickCount = 0
                }, 1000)
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(i: Int, i1: Int, intent: Intent?) {
        if (i == REQUEST_CAMERA_PERMISSIONS) {
            cameraPermission.launch(Manifest.permission.CAMERA)
        }

        super.onActivityResult(i, i1, intent)
    }

    // define a contract to request camera permissions
    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, Constant.READY, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, Constant.ERROR, Toast.LENGTH_SHORT).show()
            }
        }


    override fun onKeyDown(var1: Int, var2: KeyEvent): Boolean {
        if (var1 == KeyEvent.KEYCODE_BACK) {
            finish()
        }
        return super.onKeyDown(var1, var2)
    }

    private fun showKeyboardAfterBs(): Boolean {
        var threshold = 0f
        if (selectionLimits != null) {
            threshold = selectionLimits.box.right - selectionLimits.box.left
        }
        return threshold <= 5F;
    }

    private fun showStyleListMenu(anchor: View) {
        val data: MutableList<HashMap<String, Any?>> = ArrayList()
        var map = HashMap<String, Any?>()
        map["ICON"] = R.drawable.ic_bold_new
        data.add(map)
        map = HashMap()
        map["ICON"] = R.drawable.ic_italic_new
        data.add(map)
        map = HashMap()
        map["ICON"] = R.drawable.ic_underline_new
        data.add(map)
        popupWindow = ListPopupWindow(this)
        val adapter: ListAdapter = SimpleAdapter(
            this,
            data,
            R.layout.popup_menu_editor, arrayOf("ICON"), intArrayOf(R.id.shoe_select_icon)
        )
        popupWindow!!.width = ListPopupWindow.WRAP_CONTENT
        popupWindow!!.anchorView = anchor
        popupWindow!!.setAdapter(adapter)
        popupWindow!!.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> homeViewModel.getTriggerBold().setValue(true)
                1 -> homeViewModel.getTriggerItalic().setValue(true)
                2 -> homeViewModel.getTriggerUnderline().setValue(true)
                else -> {}
            }
            popupWindow!!.dismiss()
        }
        popupWindow!!.show()
    }

    private fun showAlignListMenu(anchor: View) {
        val data: MutableList<HashMap<String, Any?>> = ArrayList()
        var map = HashMap<String, Any?>()
        map["ICON"] = R.drawable.ic_align_left_new
        data.add(map)
        map = HashMap()
        map["ICON"] = R.drawable.ic_align_center_new
        data.add(map)
        map = HashMap()
        map["ICON"] = R.drawable.ic_align_right_new
        data.add(map)
        map = HashMap()
        map["ICON"] = R.drawable.ic_justify_new
        data.add(map)
        popupWindow = ListPopupWindow(this)
        val adapter: ListAdapter = SimpleAdapter(
            this,
            data,
            R.layout.popup_menu_editor, arrayOf("ICON"), intArrayOf(R.id.shoe_select_icon)
        )
        popupWindow!!.width = ListPopupWindow.WRAP_CONTENT
        popupWindow!!.anchorView = anchor
        popupWindow!!.setDropDownGravity(Gravity.CENTER)
        popupWindow!!.setAdapter(adapter)
        popupWindow!!.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> homeViewModel.getTriggerAlignLeft().setValue(true)
                1 -> homeViewModel.getTriggerAlignCenter().setValue(true)
                2 -> homeViewModel.getTriggerAlignRight().setValue(true)
                3 -> homeViewModel.getTriggerJustify().setValue(true)
                else -> {}
            }
            popupWindow!!.dismiss()
        }
        popupWindow!!.show()
    }


    private fun showHistoryListMenu(anchor: View) {
        val data: MutableList<HashMap<String, Any?>> = ArrayList()
        var map = HashMap<String, Any?>()
        map["ICON"] = R.drawable.sodk_editor_icon_undo
        data.add(map)
        map = HashMap()
        map["ICON"] = R.drawable.sodk_editor_icon_redo
        data.add(map)
        popupWindow = ListPopupWindow(this)
        val adapter: ListAdapter = SimpleAdapter(
            this,
            data,
            R.layout.popup_menu_editor, arrayOf("ICON"), intArrayOf(R.id.shoe_select_icon)
        )
        popupWindow!!.width = ListPopupWindow.WRAP_CONTENT
        popupWindow!!.anchorView = anchor
        popupWindow!!.isModal = true
        popupWindow!!.setAdapter(adapter)
        popupWindow!!.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> homeViewModel.getTriggerUndo().setValue(true)
                1 -> homeViewModel.getTriggerRedo().setValue(true)
                else -> {}
            }
        }
        popupWindow!!.show()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }


    companion object {
        const val REQUEST_CAMERA_PERMISSIONS = 0x11111
    }
}
