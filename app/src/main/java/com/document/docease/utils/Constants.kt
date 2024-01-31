package com.document.docease.utils

import android.os.Environment
import com.document.docease.BuildConfig
import java.io.File

object Constant {
    var showAds = false
    var adPerClickCount = 2
    val dir = File(Environment.getExternalStorageDirectory().absolutePath)
    val fontSizeList = ArrayList(
        listOf(
            "6 pt",
            "8 pt",
            "9 pt",
            "10 pt",
            "12 pt",
            "14 pt",
            "16 pt",
            "18 pt",
            "20 pt",
            "24 pt",
            "30 pt",
            "36 pt",
            "48 pt",
            "60 pt",
            "72 pt"
        )
    )

    val fontFamilyList = ArrayList(
        listOf(
            "Arial",
            "Arial Black",
            "Calibri",
            "Calibri Light",
            "Courier New",
            "Georgia",
            "Helvetica",
            "Tahoma",
            "Times New Roman",
            "Verdana"
        )
    )

    const val COUPON_URL = "https://redeemoffercode-3jb3ulwblq-uc.a.run.app?offer_code="
    const val entitlement = "pro_entitlement"
    const val promo_entitlement = "promotional_entitlement"
    const val flavor = "docViewer"
    const val platform = "android"
    const val BILLING_PUBLIC_KEY = "billing_public_key"
    const val POINT_ADS = "point_ads"
    const val TIME_RATE_DEFAULT = 180000 //3p ( minisecond)
    const val SHORT_CUT_FILE_NAME = "fileName"
    const val SHORT_CUT_PAGE_NUM = "pageNum"
    const val RESULT = "CREATE_FILE"

    const val INTENT_DATA = "intentData"
    const val INTENT_FILE_URI = "intentFileUri"
    const val FILE_URI = "fileUri"
    const val TRANSPARENT = "transparent"
    const val SHOW_DEFAULT_BS = "SHOW_DEFAULT_BS"
    const val COLOR_HEIGHT_LIGHT = "colorHighlight"
    const val COLOR_FONT = "colorFont"
    const val HIGHLIGHT_BOTTOM_SHEET_FRAGMENT = "highlightBottomSheetFragment"
    const val EDITFONT_BOTTOM_SHEET_FRAGMENT = "editFontBottomSheetFragment"
    const val READY = "Ready!"
    const val ERROR = "Error!"
    const val ANDROID = "android"
    const val FILE = "file"
    const val PRINT = "print"
    const val FILE_PATH = "file_path"
    const val IS_IMAGE_FILE = "isImageFile"
    const val STARTED_FROM_EXPLORER = "STARTED_FROM_EXPLORER"
    const val START_PAGE = "START_PAGE"
    const val FILE_NAME = "FILE_NAME"
    const val PREVIEW = "preview"
    const val EDITOR = "editor"
    const val INTENT_ACTION_VIEW = "android.intent.action.VIEW"

    const val PACKAGE = "package"

    const val REGULAR_SPACE_CHARACTER = ' '
    const val NON_BREAKABLE_SPACE_UNICODE = '\u00A0'


    fun getFileAssetList(): ArrayList<FileAssetModel> {
        val mFileArrayList = ArrayList<FileAssetModel>()
        mFileArrayList.add(
            FileAssetModel(
                "spreadsheet-blank.xlsx",
                "ic_excel_new_file.png",
                "Spreadsheet.xlsx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "spreadsheet-office2003.xls",
                "ic_excel_new_file.png",
                "2003 spreadsheet.xls",
                "Spreadsheet.xlsx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "document-blank.docx",
                "ic_word_new_file.png",
                "Document.docx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "document-office2003.doc",
                "ic_word_new_file.png",
                "2003 document.doc",
                "Document.docx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "presentation-blank.pptx",
                "ic_ppt_new_file.png",
                "Presentation.pptx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "presentation-office2003.ppt",
                "ic_ppt_new_file.png",
                "2003 presentation.ppt",
                "Presentation.pptx"
            )
        )
        return mFileArrayList
    }

    fun getFileTemplates(): ArrayList<FileAssetModel> {
        val mFileArrayList = ArrayList<FileAssetModel>()
        mFileArrayList.add(
            FileAssetModel(
                "template-docx-a.docx",
                "template-docx-a.png",
                "Resume.docx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "template-docx-b.docx",
                "template-docx-b.png",
                "Report II.docx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "template-pptx-a.pptx",
                "template-pptx-a.png",
                "Dark II.pptx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "template-pptx-b.pptx",
                "template-pptx-b.png",
                "Light II.pptx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "template-xlsx-a.xlsx",
                "template-xlsx-a.png",
                "Expenses II.xlsx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "template-xlsx-b.xlsx",
                "template-xlsx-b.png",
                "Invoice.xlsx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "document-letter.docx",
                "document-letter.png",
                "Letter.docx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "document-report.docx",
                "document-report.png",
                "Report I.docx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "presentation-dark-amber.pptx",
                "presentation-dark-amber.png",
                "Dark I.pptx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "presentation-light-bubbles.pptx",
                "presentation-light-bubbles.png",
                "Light I.pptx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "spreadsheet-chart-data.xlsx",
                "spreadsheet-chart-data.png",
                "Chart.xlsx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "spreadsheet-expense-budget.xlsx",
                "spreadsheet-expense-budget.png",
                "Expenses I.xlsx"
            )
        )
        return mFileArrayList
    }

}

const val SHARED_PREFERENCES_FILE_NAME = BuildConfig.APPLICATION_ID + "_shared_pref"

object PrefKeys {
    const val keyRecent = "keyRecent"
    const val keyBookMarks = "keyBookMarks"
}


object AdUnits {
    const val homeNative = "ca-app-pub-3940256099942544/2247696110"
    const val filesNative = "ca-app-pub-3940256099942544/2247696110"
    const val exitNative = "ca-app-pub-3940256099942544/2247696110"
    const val splashInterstitial = "ca-app-pub-3940256099942544/8691691433"
}