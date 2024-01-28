package com.document.docease.ui.components.piechart

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.document.docease.R
import com.document.docease.ui.theme.excelSelected
import com.document.docease.ui.theme.pdfSelected
import com.document.docease.ui.theme.pptSelected
import com.document.docease.ui.theme.wordSelected
import com.document.docease.utils.Utility
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.MPPointF

@Composable
fun FileDistributionChart(
    data: List<PieChartData>
) {

    // on below line we are creating a column and
    // specifying the horizontal and vertical arrangement
    // and specifying padding from all sides.
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .size(150.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // on below line we are creating a cross fade and
        // specifying target state as pie chart data the
        // method we have created in Pie chart data class.
        Crossfade(targetState = data, label = "tsest") { pieChartData ->
            // on below line we are creating an
            // android view for pie chart.
            AndroidView(factory = { context ->
                // on below line we are creating a pie chart
                // and specifying layout params.
                PieChart(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        // on below line we are specifying layout
                        // params as MATCH PARENT for height and width.
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    // on below line we are setting description
                    // enables for our pie chart.
                    this.description.isEnabled = false

                    // on below line we are setting draw hole
                    // to false not to draw hole in pie chart
                    this.isDrawHoleEnabled = false

                    // on below line we are enabling legend.
                    this.legend.isEnabled = true

                    // on below line we are specifying
                    // text size for our legend.
                    this.legend.textSize = 12F

                    // on below line we are specifying
                    // alignment for our legend.
                    this.legend.horizontalAlignment =
                        Legend.LegendHorizontalAlignment.CENTER

                    // on below line we are specifying entry label color as white.
                    this.setEntryLabelColor(resources.getColor(R.color.white))
                }
            },
                // on below line we are specifying modifier
                // for it and specifying padding to it.
                modifier = Modifier
                    .size(200.dp)
                    .padding(5.dp), update = {
                    // on below line we are calling update pie chart
                    // method and passing pie chart and list of data.
                    updatePieChartWithData(it, pieChartData, context)
                })
        }
    }
}

// on below line we are creating a update pie
// chart function to update data in pie chart.
fun updatePieChartWithData(
    // on below line we are creating a variable
    // for pie chart and data for our list of data.
    chart: PieChart,
    data: List<PieChartData>,
    contex: Context
) {
    // on below line we are creating
    // array list for the entries.
    val entries = ArrayList<PieEntry>()

    // on below line we are running for loop for
    // passing data from list into entries list.

    for (i in data.indices) {
        val item = data[i]
        entries.add(
            PieEntry(
                item.value,
                item.count,
                Utility.getResizedDrawableUsingSpecificSize(
                    AppCompatResources.getDrawable(
                        contex,
                        item.icon
                    )!!, 150, 100
                )
            )
        )
    }

    // on below line we are creating
    // a variable for pie data set.
    val ds = PieDataSet(entries, "")

    // on below line we are specifying color
    // int the array list from colors.
    ds.colors = arrayListOf(
        pdfSelected.toArgb(),
        wordSelected.toArgb(),
        excelSelected.toArgb(),
        pptSelected.toArgb(),
    )


    ds.setDrawValues(false)

    // on below line we are specifying position for value
    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    // on below line we are specifying position for value inside the slice.
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    // on below line we are specifying
    // slice space between two slices.
    ds.sliceSpace = 2f

    // on below line we are specifying text color
    ds.valueTextColor = Color.WHITE

    // on below line we are specifying
    // text size for value.
    ds.valueTextSize = 18f

    ds.iconsOffset = MPPointF(15f, 15f)

    // on below line we are specifying type face as bold.
    ds.valueTypeface = Typeface.DEFAULT_BOLD

    // on below line we are creating
    // a variable for pie data
    val d = PieData(ds)

    // on below line we are setting this
    // pie data in chart data.
    chart.data = d
    chart.setDrawEntryLabels(false)
    chart.legend.isEnabled = false

    // on below line we are
    // calling invalidate in chart.
    chart.invalidate()
}


