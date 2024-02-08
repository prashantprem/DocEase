package com.document.docease.ui.module.home

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.document.docease.R
import com.document.docease.data.Resource
import com.document.docease.ui.common.FileCountScreen
import com.document.docease.ui.common.FileListWrapper
import com.document.docease.ui.common.StoragePermissionScreen
import com.document.docease.ui.components.ads.NativeAdAdmobMedium
import com.document.docease.ui.components.piechart.FileDistributionChart
import com.document.docease.ui.components.piechart.PieChartData
import com.document.docease.ui.module.filescreen.FileClickListener
import com.document.docease.ui.module.main.MainActivity
import com.document.docease.ui.module.main.MainViewModel
import com.document.docease.utils.Extensions.findActivity
import com.document.docease.utils.PermissionUtils
import com.document.docease.utils.ScreenType
import com.google.android.gms.ads.nativead.NativeAd

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    fileClickListener: FileClickListener,
    storageRequestLauncher: ActivityResultLauncher<Intent>,
    ad: NativeAd?,
) {
    val tabs = intArrayOf(R.drawable.ic_history, R.drawable.ic_favourites, R.drawable.ic_settings)
    val mActivity = LocalContext.current.findActivity()
    val documentCountState = viewModel.documentCount.observeAsState()

    val hasPermission = PermissionUtils.storagePermissionState.value



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
    ) {
        var tabIndex by remember { mutableIntStateOf(0) }
        val pagerState = rememberPagerState(initialPage = 0, pageCount = {
            tabs.size
        })
        LaunchedEffect(tabIndex) {
            Log.d("Testing", tabIndex.toString())
            pagerState.scrollToPage(tabIndex)
        }
        LaunchedEffect(pagerState.currentPage) {
            tabIndex = pagerState.currentPage
        }
        if (mActivity != null && !hasPermission) {
            StoragePermissionScreen {
                PermissionUtils.isPermission(
                    MainActivity.PERMISSION_EXTERNAL,
                    activity = mActivity,
                    storageRequestLauncher
                )

            }
        } else {
            NativeAdAdmobMedium(
                context = LocalContext.current,
                loadedAd = ad,
                isDarkTheme = isSystemInDarkTheme()
            )
        }

        TabRow(selectedTabIndex = tabIndex,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[tabIndex])
                        .height(3.dp)
                        .width(8.dp)
                        .clip(RoundedCornerShape(30.dp)) // clip modifier not working
                        .padding(horizontal = 40.dp)
                        .background(color = colorResource(id = R.color.primary))

                )

            },
            divider = {}) {
            tabs.forEachIndexed { index, item ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(30.dp)
                        .background(
                            color = colorResource(
                                id = R.color.bg_color_main
                            )
                        )
                ) {
                    Image(
                        painter = painterResource(id = tabs[index]),
                        contentDescription = "Recent",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(6.dp),
                        colorFilter = ColorFilter.tint(
                            color = if (tabIndex == index) colorResource(
                                id = R.color.primary
                            ) else Color.LightGray
                        )

                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                when (tabIndex) {
                    0 -> {
                        FileListWrapper(
                            files = viewModel.getRecentFiles(),
                            fileClickListener = fileClickListener,
                            screenType = ScreenType.HISTORY
                        )
                    }

                    1 -> {
                        FileListWrapper(
                            files = viewModel.getFavouriteFiles(),
                            fileClickListener = fileClickListener,
                            screenType = ScreenType.FAVOURITES
                        )
                    }

                    else -> {
                        when (val res = documentCountState.value) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                res.data?.let { docCount ->
                                    val pieChartData = listOf(
                                        PieChartData(
                                            "PDF(${docCount.pdfCount.toInt()})",
                                            (docCount.pdfCount / docCount.total),
                                            R.drawable.ic_large_pdf
                                        ),
                                        PieChartData(
                                            "WORD(${docCount.wordCount.toInt()})",
                                            (docCount.wordCount / docCount.total),
                                            R.drawable.ic_large_word
                                        ),
                                        PieChartData(
                                            "EXCEL(${docCount.excelCount.toInt()})",
                                            (docCount.excelCount / docCount.total),
                                            R.drawable.ic_large_excel
                                        ),
                                        PieChartData(
                                            "PPT(${docCount.pptCount.toInt()})",
                                            (docCount.pptCount / docCount.total),
                                            R.drawable.ic_large_ppt
                                        ),
                                    )
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color = colorResource(id = R.color.bg_color_main)),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        FileDistributionChart(pieChartData)
                                        FileCountScreen(pieChartData)
                                    }

                                }

                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun previewHomeScreen() {
//    val viewModel: MainViewModel = hiltViewModel()
//    HomeScreen(viewModel = viewModel)
//
//}


