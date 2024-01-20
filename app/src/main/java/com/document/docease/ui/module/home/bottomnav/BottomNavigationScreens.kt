package com.document.docease.ui.module.home.bottomnav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.document.docease.R

sealed class BottomNavigationScreens(val route: String, @StringRes val label: Int, @DrawableRes val icon: Int) {
    object HOME : BottomNavigationScreens("home", R.string.home, R.drawable.ic_home)
    object PDF : BottomNavigationScreens("pdf", R.string.pdf,  R.drawable.ic_pdf)
    object WORD : BottomNavigationScreens("word", R.string.word,  R.drawable.ic_word)
    object EXCEL : BottomNavigationScreens("excel", R.string.excel,  R.drawable.ic_excel)
    object PPT : BottomNavigationScreens("ppt", R.string.ppt,  R.drawable.ic_ppt)

}