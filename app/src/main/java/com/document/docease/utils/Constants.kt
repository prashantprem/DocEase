package com.document.docease.utils

import android.os.Environment
import androidx.compose.runtime.mutableStateOf
import com.document.docease.BuildConfig
import java.io.File

object Constant {
    var hasOpenedAFileInSession = false

    //    var showAds = true
    var showAdsState = mutableStateOf(false)
    var appOpenTimeout = 6000
    var adPerClickCount = 3
    var nativeAdRefreshInterval = 30000L
    var splashTime = 7000L
    var isPreview = false
    var removeAdsCount = 3
    var removeAdsDays = 2
    var supportMail = "premifsb@gmail.com"
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
    const val APP_OPEN_THRESHOLD = "APP_OPEN_THRESHOLD"
    const val FLOW_AD_CLICK_THRESHOLD = "FLOW_AD_CLICK_THRESHOLD"
    const val ADS_ENABLED = "ADS_ENABLED"
    const val NATIVE_AD_REFRESH_TIME_IN_MILLIS = "NATIVE_AD_REFRESH_TIME_IN_MILLIS"
    const val SPLASH_TIME = "SPLASH_TIME"
    const val REWARD_AD_COUNT = "REWARD_AD_COUNT"
    const val REWARD_DAYS = "REWARD_DAYS"
    const val SUPPORT_MAIL = "SUPPORT_MAIL"

    //pdfSign module
    const val DYNAMIC_MODULE_PDF_SIGN = "pdfsign"
    const val PDF_SIGN_Digital_SIGNATURE_ACTIVITY =
        "com.docease.pdfsign.pdfdigitalsignature.DigitalSignatureActivity"
    const val PDF_SIGN_MAIN_ACTIVITY = "com.docease.pdfsign.pdfdigitalsignature.MainActivity"


    const val platform = "android"

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
    const val inviteMessage =
        "\uD83D\uDCF2 Hey! Found an amazing app! \uD83D\uDE80 Manage docs like a pro – view & edit PDFs, Word, Excel, & PowerPoint files effortlessly. \uD83D\uDCBC✨ Trust me, it's a productivity booster! Tap to download now! \uD83D\uDCE5"


    fun getFileAssetList(): ArrayList<FileAssetModel> {
        val mFileArrayList = ArrayList<FileAssetModel>()
        mFileArrayList.add(
            FileAssetModel(
                "spreadsheet-blank.xlsx", "ic_excel_new_file.png", "Spreadsheet.xlsx"
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
                "document-blank.docx", "ic_word_new_file.png", "Document.docx"
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
                "presentation-blank.pptx", "ic_ppt_new_file.png", "Presentation.pptx"
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
                "template-docx-a.docx", "template-docx-a.png", "Resume.docx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "template-docx-b.docx", "template-docx-b.png", "Report II.docx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "template-pptx-a.pptx", "template-pptx-a.png", "Dark II.pptx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "template-pptx-b.pptx", "template-pptx-b.png", "Light II.pptx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "template-xlsx-a.xlsx", "template-xlsx-a.png", "Expenses II.xlsx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "template-xlsx-b.xlsx", "template-xlsx-b.png", "Invoice.xlsx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "document-letter.docx", "document-letter.png", "Letter.docx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "document-report.docx", "document-report.png", "Report I.docx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "presentation-dark-amber.pptx", "presentation-dark-amber.png", "Dark I.pptx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "presentation-light-bubbles.pptx", "presentation-light-bubbles.png", "Light I.pptx"
            )
        )
        mFileArrayList.add(
            FileAssetModel(
                "spreadsheet-chart-data.xlsx", "spreadsheet-chart-data.png", "Chart.xlsx"
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
    const val keyOutSideLaunchCount = "keyOutSideLaunchCount"
    const val keyRewardAdCount = "keyRewardAdCount"
    const val keyRemoveAdsFromTimeStamp = "keyRemoveAdsTill"
    const val keyShowAds = "keyShowAds"
}


object AdUnits {
    //test ads
//    const val homeNative = "ca-app-pub-3940256099942544/2247696110"
//    const val filesNative = "ca-app-pub-3940256099942544/2247696110"
//    const val exitNative = "ca-app-pub-3940256099942544/2247696110"
//    const val splashInterstitial = "ca-app-pub-3940256099942544/8691691433"
//    const val appOpen = "ca-app-pub-3940256099942544/9257395921"


    //prod
    const val homeNative = "ca-app-pub-5810646146446064/6965812065"
    const val filesNative = "ca-app-pub-5810646146446064/4451329369"
    const val exitNative = "ca-app-pub-5810646146446064/3138247694"
    const val splashInterstitial = "ca-app-pub-5810646146446064/5652730392"
    const val appOpen = "ca-app-pub-5810646146446064/6718400057"
    const val buyMeCoffee = "ca-app-pub-5810646146446064/3026567053"
    const val fileEditInterstitial = "ca-app-pub-5810646146446064/1029398600"
    const val flowInterstitial = "ca-app-pub-5810646146446064/3964244202"
    const val fileBanner = "ca-app-pub-7642879603256855/8890158470"
    const val drawerNative = "ca-app-pub-7642879603256855~7054294630"
}

object FirebaseEvents {
    const val filePreview = "FilePreview"
    const val fileEdit = "FileEdit"
    const val fileOpenFromOutSide = "FileOpenFromOutside"
    const val fileOpenFromInside = "FileOpenFromInside"
    const val fileSearch = "FileSearch"
    const val fileShareBottomSheet = "FileShareBottomSheet"
    const val fileSharePreview = "FileSharePreview"
    const val fileShareEdit = "FileShareEdit"
    const val splashLaunch = "SplashLaunch"
    const val shownAdOnSPlash = "ShownAdOnSplash"
    const val notShownAdOnSPlash = "NotShownAdOnSplash"
    const val ratingShown = "RatingShown"
    const val fileInfoBottomSheetOpened = "FileInfoBottomSheetOpened"
    const val sharedApp = "SharedApp"
    const val removeAdsOpened = "RemoveAdOpened"
    const val pdfSignBottomSheet = "PDFSignBottomSheet"
    const val pdfSignHomeBanner = "PDFSignHomeBanner"
    const val pdfSignDrawer = "PDFSignDrawer"
    const val pdfModuleDownloadStarted = "PDFModuleDownloadStarted"
    const val pdfModuleDownloadCompleted = "PDFModuleDownloadCompleted"
    const val pdfModuleDownloadFailed = "PDFModuleDownloadFailed"
    const val signedPDFSaved = "SignedPDFSaved"
    const val savedSignature = "SavedSignature"
    const val signedPdfShared = "signedPdfShared"
    const val signedPdfRenamed = "signedPdfRenamed"
    const val clickAllowPermission = "ClickAllowPermission"
    const val permissionGranted = "PermissionGranted"
    const val permissionDenied = "PermissionDenied"
    const val clickConvertToPdf = "ClickConvertToPdf"
    const val clickTopBarScanner = "ClickTopBarScanner"
    const val successfullyScanned = "SuccessfullyScanned"

}