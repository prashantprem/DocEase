package com.document.docease.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.document.docease.R
import com.document.docease.utils.Extensions.noRippleClickable

@Composable
fun SignPdfBanner(onCLick: () -> Unit) {

    Row(
        modifier = Modifier
            .noRippleClickable {
                onCLick()
            }
            .fillMaxWidth()
            .height(80.dp)
            .padding(10.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color(0xff92B4FF),
                        Color(0xffFFFFFF)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_main_sign_banner),
            contentDescription = null
        )
        Text(
            text = "Easily Sign Your PDFs on the go\nClick here to explore!",
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Black, fontSize = 12.sp),
            textAlign = TextAlign.Start
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color.Black
        )
    }
}

@Composable
@Preview(showBackground = true)
fun previewSignPdfBanner() {
    SignPdfBanner {

    }
}