package com.document.docease.ui.components.ads

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.document.docease.R
import com.document.docease.databinding.NativeAdMediumBinding
import com.document.docease.databinding.NativeAdSmallBinding
import com.google.android.gms.ads.nativead.NativeAd

@Composable
fun BannerAdAdmobSmall(context: Context, loadedAd: NativeAd?, isDarkTheme: Boolean = false) {
    if(loadedAd != null){
        AndroidViewBinding(
            modifier = Modifier.padding(start = 4.dp, top = 8.dp, end = 4.dp, bottom = 4.dp),
            factory = (NativeAdSmallBinding::inflate)
        ) {
            loadedAd?.let { nativead ->
                // Display the loaded ad
                nativead.icon?.let { icon ->
                    this.adIcon.setImageDrawable(icon.drawable)
                }
                nativead.headline?.let { headline ->
                    this.adHeadline.text = headline
                    if (!isDarkTheme) {
                        this.adHeadline.setTextColor(context.getColor(R.color.black))
                    }
                }
                nativead.body?.let { body ->
                    this.adBody.text = body
                    if (!isDarkTheme) {
                        this.adBody.setTextColor(context.getColor(R.color.black))
                    }
                }
                nativead.callToAction?.let { actionbutton ->
                    this.adActionbutton.text = actionbutton
                    this.nativeadview.callToActionView = this.adActionbutton
                }
                this.nativeadview.setNativeAd(nativead)
            }

        }
    } else {
        Spacer(modifier = Modifier.size(0.dp))
    }


}

@Composable
fun BannerAdAdmobMedium(context: Context, loadedAd: NativeAd?, isDarkTheme: Boolean = false) {
    if (loadedAd != null) {
        AndroidViewBinding(
            modifier = Modifier.padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 4.dp),
            factory = (NativeAdMediumBinding::inflate)
        ) {

            loadedAd.let { nativead ->
                // Display the loaded ad
                nativead.icon?.let { icon ->
                    this.adIcon.setImageDrawable(icon.drawable)
                }
                nativead.headline?.let { headline ->
                    this.adHeadline.text = headline
                    if (!isDarkTheme) {
                        this.adHeadline.setTextColor(context.getColor(R.color.black))
                    }
                }
                nativead.body?.let { body ->
                    this.adBody.text = body
                    if (!isDarkTheme) {
                        this.adBody.setTextColor(context.getColor(R.color.black))
                    }

                }
                //media
                if (nativead.mediaContent?.hasVideoContent() == true) {
                    this.adMedia.mediaContent = nativead.mediaContent
                    this.adMedia.visibility = View.VISIBLE
                } else {
                    if (nativead.images.isNotEmpty()) {
                        val image = nativead.images[0]
                        this.adImage.setImageDrawable(image.drawable)
                        this.adImage.visibility = View.VISIBLE
                    }
                }
                //end media
                nativead.callToAction?.let { actionbutton ->
                    this.adActionbutton.text = actionbutton
                    this.nativeadview.callToActionView = this.adActionbutton
                }
                this.nativeadview.setNativeAd(nativead)
            }


        }
    } else {
        Spacer(modifier = Modifier.size(0.dp))
    }

}



