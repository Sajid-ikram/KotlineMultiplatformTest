package org.example.project

import android.graphics.Point
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.*
import kotlin.math.sin
import kotlin.random.Random
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.aay.compose.baseComponents.model.GridOrientation

@Composable
fun CryptoChartScreen(navController: NavHostController) {
    val random = remember { Random(System.currentTimeMillis()) }
    val basePrice = remember { 30000.0 }
    var x by remember { mutableStateOf(0.0) }
    var volatility by remember { mutableStateOf(5000.0) }
    var isBullMarket by remember { mutableStateOf(true) }
    var points by remember { mutableStateOf(generateInitialData(basePrice, volatility)) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            while (isActive) {
                delay(500)
                points = points.drop(1).toMutableList().apply {
                    x += 1
                    var priceChange = sin(x / 50.0) * volatility + random.nextDouble() * 1000

                    if (isBullMarket) priceChange += volatility * 0.5
                    else priceChange -= volatility * 0.5

                    var newPrice = basePrice + priceChange
                    newPrice = newPrice.coerceIn(basePrice * 0.5, basePrice * 2)
                    add(x to newPrice)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            while (isActive) {
                delay(10_000)
                isBullMarket = random.nextBoolean()
                volatility = 3000 + random.nextDouble() * 5000
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                title = {
                    Text(
                        text = "Cryptocurrencies",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                backgroundColor = Color(0xFF1E1E1E),
                elevation = 4.dp
            )
        },
        backgroundColor = Color(0xFF121212),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // TODO: Replace with navigation or action
                    println("FAB clicked! Navigate to prediction screen.")
                    navController.navigate("MonteCarloSimulationPage")
                },
                backgroundColor = Color(0xFF4CAF50) // Green like in Flutter
            ) {
                Text("Predict", color = Color.White)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF121212)),
            contentAlignment = Alignment.Center // Ensures content is centered
        ) {
            val yValues = points.map { it.second }
            val xAxisLabels = points.map { it.first.toInt().toString() }

            val lineParams = listOf(
                LineParameters(
                    label = "Price",
                    data = yValues,
                    lineColor = Color(0xFF64B5F6),
                    lineType = LineType.CURVED_LINE,
                    lineShadow = true
                )
            )

            LineChart(
                modifier = Modifier
                    .fillMaxWidth() // Ensures the chart takes the full width
                    .fillMaxHeight() // Maintains the height set earlier
                    .padding(16.dp), // Avoids excessive padding
                linesParameters = lineParams,
                xAxisData = xAxisLabels,
                isGrid = true,
                gridColor = Color.Gray.copy(alpha = 0.2f),
                animateChart = true,
                showGridWithSpacer = true,
                yAxisRange = 8,
                gridOrientation = GridOrientation.VERTICAL,
                yAxisStyle = TextStyle(fontSize = 12.sp, color = Color.LightGray),
                xAxisStyle = TextStyle(fontSize = 12.sp, color = Color.LightGray),

            )
        }
    }
}

fun generateInitialData(basePrice: Double, volatility: Double): MutableList<Pair<Double, Double>> {
    val random = Random(System.currentTimeMillis())
    return MutableList(1000) { i ->
        val x = i.toDouble()
        val price = basePrice + sin(i / 100.0) * volatility + random.nextDouble() * 1000
        x to price
    }
}


