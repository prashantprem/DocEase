package com.document.docease.ui.module.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.document.docease.R
import com.document.docease.ui.common.FileInfoBottomSheetUI
import com.document.docease.ui.common.FileListWrapper
import com.document.docease.ui.module.filescreen.FIleInfoBottomSheetClickListener
import com.document.docease.ui.module.filescreen.FileClickListener
import com.document.docease.ui.module.main.MainViewModel
import com.document.docease.utils.Extensions.canGoBack
import com.document.docease.utils.Extensions.noRippleClickable
import com.document.docease.utils.Utility
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    var queryText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(true) }
    val fileSearchState = viewModel.filteredFiles.observeAsState()
    val fileInfoBottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showFileActionBottomSheet by remember { mutableStateOf(false) }
    var mFile: File? = null
    val mContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bg_color_main))
    ) {
        SearchBar(query = queryText,
            onQueryChange = {
                queryText = it
                viewModel.searchFile(it)
            }, onSearch = {
                active = false
            },
            active = active,
            onActiveChange = {
                active = it
                if (!it && navController.canGoBack()) {
                    navController.popBackStack()
                }
                Log.d("Testing", "calcjhgdsghjds")
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.noRippleClickable {
                        queryText = ""
                        viewModel.searchFile("")
                        if (navController.canGoBack()) {
                            navController.popBackStack()
                        }
                    })
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp)),

            content = {
                if (showFileActionBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showFileActionBottomSheet = false
                            mFile = null
                        },
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        containerColor = colorResource(id = R.color.bottom_sheet_bg),
                        sheetState = fileInfoBottomSheetState,
                        dragHandle = null,

                        ) {
                        mFile?.let { file ->
                            FileInfoBottomSheetUI(
                                file,
                                viewModel.isFavourite(file),
                                object : FIleInfoBottomSheetClickListener {

                                    override fun onReadClick(file: File) {
                                        Utility.previewFileWithLocalContext(mContext, file)
                                    }

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

                                    override fun onSignPdf(file: File) {
                                    }

                                })
                        }
                    }
                }

                FileListWrapper(
                    files = fileSearchState.value,
                    fileClickListener = object : FileClickListener {
                        override fun onMenuClick(file: File) {
                            scope.launch {
                                if (!fileInfoBottomSheetState.isVisible) {
                                    mFile = file
                                    showFileActionBottomSheet = true
                                }
                            }
                        }

                        override fun onFileClick(file: File) {
                            Utility.openFileWithLocalContext(mContext, file)
                        }

                    }
                )


            },
            tonalElevation = 0.dp,
            placeholder = {
                Text(text = "Search files...")
            }
        )

    }


}


//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun previewSearchScreen() {
//    SearchScreen(BaseViewModel())
//
//}
