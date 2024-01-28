package com.document.docease.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.document.docease.R
import com.document.docease.ui.components.piechart.PieChartData

@Composable
fun FileCountScreen(
    data: List<PieChartData>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            items(data) { item ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = "File Count",
                        modifier = Modifier.size(20.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.count, modifier = Modifier.width(80.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }


}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun previewFileCountScreen() {
    val pieChartData = listOf(
        PieChartData(
            "1200",
            1f,
            R.drawable.chart_pdf
        ),
        PieChartData(
            "102",
            2f,
            R.drawable.chart_word
        ),
        PieChartData(
            "1",
            3f,
            R.drawable.chart_excel
        ),
        PieChartData(
            "33",
            4f,
            R.drawable.chart_ppt
        ),
    )
    FileCountScreen(pieChartData)
}

