package com.document.docease.ui.common

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.document.docease.BuildConfig
import com.document.docease.R
import com.document.docease.data.dto.DrawerItemData
import com.document.docease.utils.Extensions.noRippleClickable

@Composable
fun AppDrawer(
    onItemClick: ((AppDrawerItemType) -> Unit)? = null,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(fraction = .85f)
            .fillMaxHeight()
            .background(color = colorResource(id = R.color.bar_color)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val drawerItem = listOf(
            DrawerItemData(
                AppDrawerItemType.removeAds,
                "Remove ads",
                R.drawable.ic_main_drawer_ads
            ),

            DrawerItemData(
                AppDrawerItemType.rate,
                "Rate us on Playstore",
                R.drawable.ic_main_drawer_rate
            ),
            DrawerItemData(
                AppDrawerItemType.share,
                "Share with friends",
                R.drawable.ic_main_drawer_share
            ),
            DrawerItemData(
                AppDrawerItemType.feedback,
                "Contact us",
                R.drawable.ic_main_drawer_contact
            )

        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xff0066FF), Color(0xffB2C5FF),
                        ),
                        startX = 0f
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "AppLogo",
                contentScale = ContentScale.Fit
            )
            Text(
                text = stringResource(id = R.string.app_name) + ": PDF, Word, Excel",
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.text_color),
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp)
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        drawerItem.forEachIndexed { index, drawerItemData ->
            AppDrawerItem(item = drawerItemData, onItemClick)
            if (index < drawerItem.size - 1) HorizontalDivider() else Spacer(
                modifier = Modifier.size(
                    0.dp
                )
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
//        NativeAdAdmobSmall(context = context, loadedAd = nativeAd)
        Text(
            text = "${stringResource(id = R.string.app_name)} v${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.text_color)
        )
    }
}

@Composable
fun AppDrawerItem(item: DrawerItemData, onItemClick: ((AppDrawerItemType) -> Unit)? = null) {
    Row(
        modifier = Modifier
            .noRippleClickable {
                if (onItemClick != null) {
                    onItemClick(item.type)
                }
            }
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = item.icon), contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = colorResource(id = R.color.text_color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = item.title, style = MaterialTheme.typography.bodyLarge.copy(
                color = colorResource(
                    id = R.color.text_color
                ),
                fontSize = 14.sp,
            )
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = colorResource(id = R.color.text_color)
        )
    }

}

@Composable
fun DrawerHeader() {
    Column(modifier = Modifier.fillMaxWidth()) {


    }
}


@Composable
@Preview(showBackground = true)
fun previewDrawer() {
//    AppDrawer()

}

enum class AppDrawerItemType {
    share, rate, removeAds, faq, feedback
}