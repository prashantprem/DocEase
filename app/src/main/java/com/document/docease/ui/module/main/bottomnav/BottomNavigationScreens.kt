package com.document.docease.ui.module.main.bottomnav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.document.docease.R
import com.document.docease.ui.theme.excelSelected
import com.document.docease.ui.theme.homeSelected
import com.document.docease.ui.theme.pdfSelected
import com.document.docease.ui.theme.pptSelected
import com.document.docease.ui.theme.wordSelected

sealed class BottomNavigationScreens(val route: String, @StringRes val label: Int, @DrawableRes val icon: Int, val selectedColor:Color) {
    object HOME : BottomNavigationScreens("home", R.string.home, R.drawable.ic_home, homeSelected)
    object PDF : BottomNavigationScreens("pdf", R.string.pdf,  R.drawable.ic_pdf, pdfSelected)
    object WORD : BottomNavigationScreens("word", R.string.word,  R.drawable.ic_word, wordSelected)
    object EXCEL : BottomNavigationScreens("excel", R.string.excel,  R.drawable.ic_excel, excelSelected)
    object PPT : BottomNavigationScreens("ppt", R.string.ppt,  R.drawable.ic_ppt, pptSelected)




}