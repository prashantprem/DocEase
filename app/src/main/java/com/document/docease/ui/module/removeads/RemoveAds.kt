package com.document.docease.ui.module.removeads

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.document.docease.R
import com.document.docease.ui.components.ads.rewardedAd
import com.document.docease.ui.components.ads.showRewardedAd
import com.document.docease.utils.AdUnits
import com.document.docease.utils.Extensions.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoveAds(
    navController: NavController
) {
    val adCount = remember { mutableIntStateOf(3) }
    val context = LocalContext.current
//    loadRewardedAd(context, AdUnits.buyMeCoffee)
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.bar_color)
                ),
                title = {
                    Text(
                        text = "Remove Ads",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = colorResource(
                                id = R.color.text_color
                            ),
                            fontSize = 18.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back",
                        tint = colorResource(id = R.color.text_color),
                        modifier = Modifier.noRippleClickable {
                            navController.popBackStack()
                        }
                    )

                }
            )
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(colorResource(id = R.color.bg_color_main))
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(.1f)
            )
            Text(
                text = "Tired of Ads?",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold, color = colorResource(
                        id = R.color.text_color
                    )
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Unlock Ad-Free experience by simply watching rewarded ads!",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = colorResource(
                        id = R.color.text_color
                    )
                ),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(.9f)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_main_ads_illustration),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = colorResource(id = R.color.primary),
                    contentColor = Color.White
                ),
                onClick = {
                    if (rewardedAd != null) {
                        showRewardedAd(context, AdUnits.buyMeCoffee, onAdDismissed = {
                            if (adCount.intValue > 0) {
                                adCount.intValue = adCount.intValue - 1
                            }
                        })
                    } else {
                        Toast.makeText(
                            context,
                            "Ad is not ready yet. Please try again!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                shape = RoundedCornerShape(36.dp),
            ) {

                Text(
                    text = "Watch Ad!(${adCount.intValue}/3)",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(.9f)
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(.1f)
            )


        }


    }

}


@Preview
@Preview(showBackground = true)
@Composable
fun previewRemoveAds() {
    RemoveAds(rememberNavController())
}