package com.example.healthandmind.ui.components


import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.Pie
import kotlinx.coroutines.delay


data class CircularTransitionData(
    val progress: Float, // 0 to 360 è un angolo
)

private const val raggioStd = 200f

@Composable
fun CircularProgressBar(
    transitionData: CircularTransitionData ,
    radius: Float = raggioStd,
    label: String = "Total Cal:",
    calories: Int = 0,
    modifier: Modifier = Modifier,
    arcColor : Color = MaterialTheme.colorScheme.inversePrimary,
    circleColor : Color = MaterialTheme.colorScheme.secondaryContainer,
    textColor : Color = MaterialTheme.colorScheme.primaryContainer
) {

    val animatedPhase by animateFloatAsState(targetValue = transitionData.progress, animationSpec = tween(1_000))

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(radius.dp)
    ) {
        inset(size.width / 2 - radius, size.height / 2 - radius) {
            drawCircle(
                color = circleColor,
                radius = radius,
                center = center,
                style = Stroke(width = 40f, cap = StrokeCap.Round)
            )

            drawArc(
                startAngle = 270f, // 270 is 0 degree
                sweepAngle = animatedPhase,
                useCenter = false,
                color = arcColor,
                style = Stroke(width = 40f, cap = StrokeCap.Round)
            )


            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "$label $calories",
                    center.x,
                    center.y,
                    android.graphics.Paint().apply {
                        color = textColor.toArgb()
                        textSize = 40f
                        textAlign = android.graphics.Paint.Align.CENTER
                        typeface = android.graphics.Typeface.create(
                            android.graphics.Typeface.DEFAULT,
                            android.graphics.Typeface.BOLD
                        )
                    }
                )
            }
        }
    }
}

data class PieData(
    val label : String,
    val percentage : Double, // 0 to 100
)

@Composable
fun ComposablePieChart (
    firstColors :  List<Color>,  // the size of the list must be equal to the size of the pieDatas
    secondaryColors : List<Color>,  // the size of the list must be equal to the size of the pieDatas
    pieDatas : List<PieData>,
    labelsWithColorBox : Boolean = false
) {

    val data: List<Pie> = pieDatas.mapIndexed { index, pieData ->
        Pie(
            label = pieData.label,
            data = pieData.percentage,
            color = firstColors[index],
        )
    }

    PieChart(
        modifier = Modifier
            .size(150.dp)
            .padding(16.dp),
        data = data,
        selectedScale = 1.2f,
        scaleAnimEnterSpec = spring<Float>(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        colorAnimEnterSpec = tween(300,),
        colorAnimExitSpec = tween(300),
        scaleAnimExitSpec = tween(300),
        spaceDegreeAnimExitSpec = tween(300),
        spaceDegree = 7f,
        selectedPaddingDegree = 5f,
        style = Pie.Style.Stroke(16.dp),
    )
}

@Composable
fun ComposableLineChart(
    values : List<Double>,
    labels : List<String>,
    modifier: Modifier = Modifier

) {
    val textColor = MaterialTheme.colorScheme.primaryContainer
    val chartsColor = MaterialTheme.colorScheme.inversePrimary
    val dotColor = MaterialTheme.colorScheme.tertiary
    LineChart(
        modifier = modifier.fillMaxSize().padding(horizontal = 22.dp, vertical = 10.dp),
        data = remember {
            listOf(
                Line(
                    label = "Weight",
                    values = values,
                    color = SolidColor(chartsColor),
                    dotProperties = DotProperties(
                        enabled = true,
                        color = SolidColor(dotColor),
                    ),
                    firstGradientFillColor = Color(textColor.toArgb()).copy(alpha = .5f),
                    secondGradientFillColor = Color.Transparent,
                    strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                    gradientAnimationDelay = 1000,
                    drawStyle = DrawStyle.Stroke(width = 2.dp),
                    curvedEdges = true
                )
            )
        },
        indicatorProperties = HorizontalIndicatorProperties(
            textStyle = TextStyle(fontSize = 10.sp, color = textColor),
        ),
        animationMode = AnimationMode.Together(delayBuilder = {
            it * 500L
        }),
        labelProperties = LabelProperties(
            enabled = true,
            textStyle = TextStyle(fontSize = 10.sp, color = textColor),
            labels = labels,
        )
    )
}


@Preview(
    showBackground = true,
)
@Composable
fun CircularProgressIndicatorProva() {

    var progress: Float by remember { mutableStateOf(0.8f) }
    val indicatorSize = 144.dp
    val trackWidth: Dp = (indicatorSize * .1f)
    val commonModifier = Modifier.size(indicatorSize)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator(
                progress = { progress },
                modifier = commonModifier,
                color = Color.Black,
                strokeWidth = trackWidth,
                trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            )

            Column {
                Text(
                    text = "Progress: ${progress * 100}%",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

/*

class ChartsKtExample {
    // Bar Chart Example

    // Line Chart Example
    @Composable
    fun LineChartExample(
        lineEntries: List<Entry>
    ) {
        // Prepare line entries
        val colorLine = MaterialTheme.colorScheme.tertiaryContainer.toArgb()
        val colorDot = MaterialTheme.colorScheme.primaryContainer.toArgb()
        val colorArea = MaterialTheme.colorScheme.secondary.toArgb()


        // Create line data set
        val lineDataSet = LineDataSet(lineEntries, "Performance").apply {
            // Line appearance
            color = colorLine
            lineWidth = 2f
            setCircleColor(colorDot)
            circleRadius = 5f

            // Fill and styling
            setDrawFilled(true)
            fillColor = colorArea
        }

        // Create line data
        val lineData = LineData(lineDataSet)

        // Render the chart
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp),
            factory = { context ->
                LineChart(context).apply {
                    data = lineData
                    description.isEnabled = false

                    // Axis and grid customization
                    xAxis.apply {
                        isEnabled = true
                        setDrawGridLines(false)
                    }

                    // Animations


                    invalidate()
                }
            }
        )
    }


    // Pie Chart Example
    @Composable
    fun PieChartExample(
        pieEntries: List<PieEntry>,
        modifier: Modifier = Modifier
    ) {

        val colors1 = listOf(
            MaterialTheme.colorScheme.primary.toArgb(),
            MaterialTheme.colorScheme.tertiary.toArgb(),
            MaterialTheme.colorScheme.inversePrimary.toArgb(),
            MaterialTheme.colorScheme.secondaryContainer.toArgb()
        )


        // Create pie data set
        val pieDataSet = PieDataSet(pieEntries, "Distribution").apply {
            // Customize colors
            colors = colors1

            // Styling
            sliceSpace = 3f
            selectionShift = 5f

            // disable value labels
            setDrawValues(true)
            valueTextColor = Color.White.toArgb()

        }

        // Create pie data
        val pieData = PieData(pieDataSet)

        // Render the chart
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(300.dp),
            factory = { context ->
                PieChart(context).apply {
                    data = pieData


                    // Chart customization
                    description.isEnabled = false
                    centerText = "Macros"
                    setCenterTextColor(colors1[0])

                    setDrawEntryLabels(false)

                    // legend positionng
                    legend.apply {
                        isEnabled = true
                        verticalAlignment = Legend.LegendVerticalAlignment.TOP
                        horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                        orientation = Legend.LegendOrientation.VERTICAL
                        setDrawInside(false)
                        xEntrySpace = 7f
                        yEntrySpace = 0f
                        yOffset = 0f
                    }


                    // Hole in the center
                    isDrawHoleEnabled = true
                    holeRadius = 45f
                    transparentCircleRadius = holeRadius + 3f
                    setHoleColor(Color.Transparent.toArgb())

                    maxAngle = 270f

                    // Animations
                    animateXY(1500, 1500)

                    invalidate()
                }
            }
        )
    }
}

// Comprehensive Chart Display Screen
@Preview(
    showBackground = true,
)
@Composable
fun ChartDisplayScreen() {
    val chartsExample = ChartsKtExample()
    Column {
        chartsExample.LineChartExample(
            lineEntries = listOf(
                Entry(0f, 20f),
                Entry(1f, 35f),
                Entry(2f, 40f),
                Entry(3f, 55f),
                Entry(4f, 50f)
            )
        )
        chartsExample.PieChartExample(
            pieEntries = listOf(
                PieEntry(25f, "Category A"),
                PieEntry(35f, "Category B"),
                PieEntry(20f, "Category C"),
                PieEntry(20f, "Category D")
            )
        )
    }
}


@Preview
@Composable
fun CustomBarChart(
    data: List<Float> = listOf(100f, 200f, 300f, 400f, 500f)) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val chartWidth = size.width
        val chartHeight = size.height
        val barWidth = chartWidth / (data.size * 1.5f)
        val maxValue = data.maxOrNull() ?: 0f

        data.forEachIndexed { index, value ->
            val barHeight = (value / maxValue) * chartHeight
            val left = index * barWidth * 1.5f
            val top = chartHeight - barHeight

            drawRect(
                color = Color.Blue,
                topLeft = Offset(left, top),
                size = Size(barWidth, barHeight)
            )
        }
    }
}



data class PieChartData(
    val value: Float,
    val color: Color
)

@Composable
fun PieChart(
    data: List<PieChartData>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
) {
    Canvas(modifier = modifier) {
        val totalValue = data.sumOf { it.value.toDouble() }.toFloat()
        var startAngle = 0f

        data.forEach { pieData ->
            val sweepAngle = (pieData.value / totalValue) * 360f
            drawPieSlice(
                color = pieData.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle
            )
            startAngle += sweepAngle
        }
    }
}

private fun DrawScope.drawPieSlice(
    color: Color,
    startAngle: Float,
    sweepAngle: Float
) {
    // Calculate the diameter and radius
    val diameter = minOf(size.width, size.height) * 0.8f
    val radius = diameter / 2

    // Center of the canvas
    val centerX = size.width / 2
    val centerY = size.height / 2

    // Draw the pie slice
    drawArc(
        color = color,
        startAngle = startAngle - 90f, // Start from top
        sweepAngle = sweepAngle,
        useCenter = true,
        topLeft = Offset(centerX - radius, centerY - radius),
        size = Size(diameter, diameter)
    )
}
@Preview
@Composable
fun PieChartScreen() {
    val pieData = listOf(
        PieChartData(25f, Color.Red),
        PieChartData(35f, Color.Green),
        PieChartData(15f, Color.Blue),
        PieChartData(25f, Color.Yellow)
    )

    PieChart(data = pieData)
}

 */



