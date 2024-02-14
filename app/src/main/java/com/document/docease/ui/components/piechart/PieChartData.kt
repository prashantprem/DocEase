package com.document.docease.ui.components.piechart

data class PieChartData(
    var count: String,
    var value: Float,
    var icon: Int
)

data class DocumentCount(
    val total: Float,
    val pdfCount: Float,
    val wordCount: Float,
    val excelCount: Float,
    val pptCount: Float
)
