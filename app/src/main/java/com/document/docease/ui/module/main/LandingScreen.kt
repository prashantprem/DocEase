package com.document.docease.ui.module.main

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.document.docease.R
import com.document.docease.ui.common.ExitBottomSheet
import com.document.docease.ui.common.FileInfoBottomSheetUI
import com.document.docease.ui.components.ads.loadInterstitial
import com.document.docease.ui.components.ads.rememberNativeAdState
import com.document.docease.ui.components.ads.showInterstitial
import com.document.docease.ui.module.filescreen.FIleInfoBottomSheetClickListener
import com.document.docease.ui.module.filescreen.FileClickListener
import com.document.docease.ui.module.main.bottomnav.BottomNavigationScreens
import com.document.docease.ui.module.main.bottomnav.CustomBottomNavigation
import com.document.docease.ui.navigation.BottomNavigationScreenConfigurations
import com.document.docease.ui.navigation.Routes
import com.document.docease.utils.AdUnits
import com.document.docease.utils.Extensions.findActivity
import com.document.docease.utils.Extensions.noRippleClickable
import com.document.docease.utils.Utility
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    viewModel: MainViewModel,
    navigationController: NavHostController
) {
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.HOME,
        BottomNavigationScreens.PDF,
        BottomNavigationScreens.WORD,
        BottomNavigationScreens.EXCEL,
        BottomNavigationScreens.PPT
    )
    val fileInfoBottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showFileActionBottomSheet by remember { mutableStateOf(false) }
    var showExitBottomSheet by remember { mutableStateOf(false) }
    val exitBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var mFile: File? = null
    val mContext = LocalContext.current
    val bottomNavigationController = rememberNavController()

    val exitAdState = rememberNativeAdState(
        context = LocalContext.current, adUnitId = AdUnits.exitNative,
        refreshInterval = 300000
    )
    val bottomBarNativeState = rememberNativeAdState(
        context = LocalContext.current, adUnitId = AdUnits.filesNative,
        refreshInterval = 300000
    )
    var clickCount = 0
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.bg_color_main)
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(color = colorResource(id = R.color.primary))
                    )
                },
                navigationIcon = {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_hamburger),
//                            contentDescription = "App Logo",
//                            tint = colorResource(id = R.color.primary),
//                            modifier = Modifier.size(30.dp),
//                        )
//                    }
                },
                actions = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Button",
                        tint = colorResource(id = R.color.primary),
                        modifier = Modifier
                            .noRippleClickable {
                                navigationController.navigate(Routes.SEARCH) {
                                    popUpTo(navigationController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                Log.d("TestingCLick", "CLicked")
                            }
                            .size(80.dp)
                            .fillMaxWidth(0.2f)
                            .padding(end = 8.dp)
                    )
                }
            )
        },
        content = {
            if (showFileActionBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showFileActionBottomSheet = false
                        mFile = null
                    },
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    containerColor = colorResource(id = R.color.bg_color_main),
                    sheetState = fileInfoBottomSheetState,
                    dragHandle = null,

                    ) {
                    mFile?.let { file ->
                        FileInfoBottomSheetUI(
                            file,
                            viewModel.isFavourite(file),
                            object : FIleInfoBottomSheetClickListener {
                                override fun onEditClick(file: File) {
                                    Utility.openFileWithLocalContext(mContext, file)
                                }

                                override fun onWhatsAppShare(file: File) {
                                    Utility.shareToWhatsApp(file, mContext)
                                }

                                override fun onShare(file: File) {
                                    Utility.shareToAny(file, mContext)
                                }

                                override fun onPrint(file: File) {
                                    Utility.previewFileWithLocalContext(
                                        mContext,
                                        file,
                                        isForPrint = true
                                    )
                                }

                                override fun onAddToFavourite(file: File) {
                                    viewModel.addToFavourites(file)
                                }

                            })
                    }
                }
            } else if (showExitBottomSheet) {
                ExitBottomSheet(onExit = {
                    showExitBottomSheet = false
                    mContext.findActivity()?.finish()
                }, onDismiss = {
                    showExitBottomSheet = false
                }, exitBottomSheetState, exitAdState)
            }
            BackHandler(enabled = true, onBack = {
                if (navigationController.currentBackStackEntry?.destination?.route == Routes.LANDING) {
                    showExitBottomSheet = true
                }
            })

            Column(
                modifier = Modifier
                    .padding(it)
                    .background(color = colorResource(id = R.color.bg_color_main))
            ) {
                BottomNavigationScreenConfigurations(
                    bottomNavigationController,
                    viewModel,
                    object : FileClickListener {
                        override fun onMenuClick(file: File) {
                            scope.launch {
                                if (!fileInfoBottomSheetState.isVisible) {
                                    mFile = file
                                    showFileActionBottomSheet = true
                                }
                            }
                        }

                        override fun onFileClick(file: File) {
                            Utility.previewFileWithLocalContext(mContext, file)
                        }

                    }, bottomBarNativeState
                )
            }
        },
        bottomBar = {
            CustomBottomNavigation(
                navController = bottomNavigationController,
                items = bottomNavigationItems
            ) {
                clickCount += 1
                Log.d("TestingCount", "$clickCount")
                if (clickCount >= 2) {
                    showInterstitial(mContext, onAdDismissed = {})
                    clickCount = 0
                } else {
                    loadInterstitial(context = mContext, AdUnits.splashInterstitial)
                }
            }
        }
    )


}

//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun previewLandingSCreen2() {
//    DocEaseTheme {
//        val viewModel: MainViewModel = hiltViewModel()
//        LandingScreen(rememberNavController(), viewModel = viewModel)
//    }
//}

