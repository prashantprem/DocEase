package com.document.docease.ui.module.main


import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.document.docease.R
import com.document.docease.ui.theme.DocEaseTheme
import com.document.docease.utils.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel by viewModels<MainViewModel>()
    private val PERMISSION_EXTERNAL = 0x111111


    val requestPermissionResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
//                    queryDataFile()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.allow_permission_for_storage_access),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            viewModel.showSplash
        }
        PermissionUtils.isPermission(PERMISSION_EXTERNAL,this@MainActivity,requestPermissionResultLauncher)
        setContent {
            DocEaseTheme {
                val navController = rememberNavController()
                Box(Modifier.safeDrawingPadding()) {
                    LandingScreen(navController)
                }
            }

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_EXTERNAL) {
            if (grantResults.isNotEmpty() && permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    queryDataFile()
                } else {

                    PermissionUtils.isPermission(
                        PERMISSION_EXTERNAL,
                        this,
                        requestPermissionResultLauncher
                    )
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun previewLandingScreen() {
    DocEaseTheme {
        LandingScreen(rememberNavController())
    }
}
