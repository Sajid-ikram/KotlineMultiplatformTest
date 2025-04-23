package org.example.project

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import network.chaintech.cmpcharts.axis.AxisProperties
import network.chaintech.cmpcharts.common.extensions.formatToSinglePrecision
import network.chaintech.cmpcharts.common.model.Point
import network.chaintech.cmpcharts.common.ui.SelectionHighlightPoint
import network.chaintech.cmpcharts.common.ui.SelectionHighlightPopUp
import network.chaintech.cmpcharts.common.ui.ShadowUnderLine
import network.chaintech.cmpcharts.ui.linechart.LineChart
import network.chaintech.cmpcharts.ui.linechart.model.Line
import network.chaintech.cmpcharts.ui.linechart.model.LineChartProperties
import network.chaintech.cmpcharts.ui.linechart.model.LinePlotData
import network.chaintech.cmpcharts.ui.linechart.model.LineStyle
import network.chaintech.cmpcharts.ui.linechart.model.LineType

@Composable
fun DashedLineChart(pointsData: List<Point>) {
    val textMeasurer = rememberTextMeasurer()
    val steps = 10
    val xAxisProperties = AxisProperties(

        stepSize = 40.dp,
        stepCount = pointsData.size - 1,
        labelFormatter = { i -> i.toString() },

    )

    val yAxisProperties = AxisProperties(

        stepCount = steps,
        labelFormatter = { i ->
            val yMin = pointsData.minOf { it.y }
            val yMax = pointsData.maxOf { it.y }
            val yScale = (yMax - yMin) / steps
            ((i * yScale) + yMin).formatToSinglePrecision()
        },
        labelColor = Color.White,
        lineColor =  Color.Green,
        labelPadding = 20.dp
    )

    val lineChartProperties = LineChartProperties(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(
                        lineType = LineType.SmoothCurve(isDotted = true),
                        color = Color.Red
                    ),
                    shadowUnderLine = ShadowUnderLine(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Black,
                                Color.Transparent
                            )
                        ), alpha = 0.3f
                    ),
                    selectionHighlightPoint = SelectionHighlightPoint(
                        color = Color.Green
                    ),
                    selectionHighlightPopUp = SelectionHighlightPopUp(
                        backgroundColor = Color.Green,
                        labelColor = Color.Green,
                        labelTypeface = FontWeight.Bold,
                        textMeasurer = textMeasurer
                    )
                )
            )
        ),
        xAxisProperties = xAxisProperties,
        yAxisProperties = yAxisProperties
    )
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartProperties = lineChartProperties
    )
}