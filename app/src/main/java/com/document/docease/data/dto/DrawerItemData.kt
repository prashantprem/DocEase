package com.document.docease.data.dto

import androidx.annotation.DrawableRes
import com.document.docease.ui.common.AppDrawerItemType

data class DrawerItemData(
    val type: AppDrawerItemType,
    val title: String,
    @DrawableRes val icon: Int
)