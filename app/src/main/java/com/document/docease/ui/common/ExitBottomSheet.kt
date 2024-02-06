package com.document.docease.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.document.docease.R
import com.document.docease.ui.components.ads.NativeAdAdmobMedium
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitBottomSheet(
    onExit: () -> Unit,
    onDismiss: () -> Unit,
    exitBottomSheetState: SheetState,
    adState: NativeAd?
) {
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = colorResource(id = R.color.bg_color_main),
        dragHandle = null,
        sheetState = exitBottomSheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Exit ${stringResource(id = R.string.app_name)}?")
            Spacer(modifier = Modifier.height(16.dp))
            NativeAdAdmobMedium(context = LocalContext.current, loadedAd = adState)
            Row {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.LightGray,
                        contentColor = Color.Black
                    ),
                    onClick = {
                        scope.launch {
                            exitBottomSheetState.hide()
                            onExit()
                        }

                    }, shape = RoundedCornerShape(36.dp)
                ) {
                    Text(
                        text = "Exit",
                        Modifier.padding(4.dp)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.3f)
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = colorResource(id = R.color.primary),
                        contentColor = Color.White
                    ),
                    onClick = { onDismiss() }, shape = RoundedCornerShape(36.dp),
                ) {
                    Text(
                        text = "Cancel",
                        Modifier.padding(4.dp)
                    )
                }

            }


        }
    }

}


//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun previewExitBottomSheet() {
//    ExitBottomSheet {
//
//    }
//
//}