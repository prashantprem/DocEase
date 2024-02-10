package com.document.docease.ui.module.removeads

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.MutableIntState
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
import com.document.docease.ui.components.ads.loadRewardedAd
import com.document.docease.ui.components.ads.rewardedAd
import com.document.docease.ui.components.ads.showRewardedAd
import com.document.docease.utils.AdUnits
import com.document.docease.utils.Constant
import com.document.docease.utils.Extensions.noRippleClickable
import com.document.docease.utils.PrefKeys
import com.document.docease.utils.SharedPreferencesUtility
import com.document.docease.utils.Utility

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoveAds(
    navController: NavController
) {
    val adCount = remember { mutableIntStateOf(Constant.removeAdsCount) }
    val context = LocalContext.current
    loadRewardedAd(context, AdUnits.buyMeCoffee)
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
                            fontSize = 14.sp
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
            if (adCount.intValue > 0 && Constant.showAdsState.value) {
                AdScreen(
                    adCount = adCount, context,
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
            } else {
                if (Constant.showAdsState.value) {
                    Constant.showAdsState.value = false
                    SharedPreferencesUtility.apply {
                        saveBoolean(
                            context,
                            PrefKeys.keyShowAds,
                            false
                        )
                        saveInt(
                            context = context,
                            PrefKeys.keyRewardAdCount,
                            0
                        )
                        saveLong(
                            context = context,
                            PrefKeys.keyRemoveAdsFromTimeStamp,
                            System.currentTimeMillis()
                        )
                    }
                }
                Log.d("TestingShowAds2", Constant.showAdsState.value.toString())
                RewardScreen(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    context = context
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

@Composable
fun AdScreen(adCount: MutableIntState, context: Context, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

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
                containerColor = Color(0xff003EC9),
                contentColor = Color.White
            ),
            onClick = {
                if (rewardedAd != null) {
                    showRewardedAd(context, AdUnits.buyMeCoffee, onAdDismissed = {
                        if (adCount.intValue > 0) {
                            adCount.intValue = adCount.intValue - 1
                            Constant.removeAdsCount = adCount.intValue
                            SharedPreferencesUtility.saveInt(
                                context,
                                PrefKeys.keyRewardAdCount,
                                adCount.intValue
                            )
                        }
                    })
                } else {
                    loadRewardedAd(context, AdUnits.buyMeCoffee)
                    Toast.makeText(
                        context,
                        "Ad is not ready yet. Please try again in a few seconds!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            shape = RoundedCornerShape(36.dp),
        ) {

            Text(
                text = "Watch Ad! (${adCount.intValue}/${Constant.removeAdsCount})",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(.9f)
            )
        }
    }

}

@Composable
fun RewardScreen(modifier: Modifier, context: Context) {

    Column(modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_main_reward_granted),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text =
            "Congratulations! \uD83C\uDF89",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(
                    id = R.color.text_color
                )
            ),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(.9f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text =
            " You've earned ad-free browsing. Enjoy the uninterrupted experience!",
            style = MaterialTheme.typography.bodySmall.copy(
                color = colorResource(
                    id = R.color.text_color
                )
            ),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(.9f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text =
            "Valid till: ${Utility.getRewardValidityDate(context = context)}",
            style = MaterialTheme.typography.labelSmall.copy(
                color = colorResource(
                    id = R.color.text_color
                )
            ),
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(.9f)
        )


    }

}


@Preview
@Preview(showBackground = true)
@Composable
fun previewRemoveAds() {
    RemoveAds(rememberNavController())
}