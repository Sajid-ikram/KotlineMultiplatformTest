import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.random.Random
import org.example.project.model.CryptoData
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.navigation.NavHostController

@Composable
fun MonteCarloSimulationPage(navController: NavHostController) {
    var steps by remember { mutableStateOf(100) } // Number of simulation steps
    var simulations by remember { mutableStateOf(50) } // Number of simulation paths
    var simulationResults by remember { mutableStateOf(emptyList<List<Pair<Float, Float>>>()) }
    val coroutineScope = rememberCoroutineScope()

    fun runSimulation() {
        coroutineScope.launch(Dispatchers.Default) {
            val random = Random.Default
            //val initialPrice = cryptocurrency.priceUsd?.toFloatOrNull() ?: 0f
            val initialPrice = 300f
            val volatility = 0.05f // Adjustable or dynamic
            val results = mutableListOf<List<Pair<Float, Float>>>()

            repeat(simulations) {
                val path = mutableListOf<Pair<Float, Float>>()
                var price = initialPrice
                repeat(steps) { step ->
                    val randomChange =
                        random.nextFloat() * volatility * initialPrice * if (random.nextBoolean()) 1 else -1
                    price += randomChange
                    price = max(0f, price) // Prevent negative prices
                    path.add(step.toFloat() to price)
                }
                results.add(path)
            }
            simulationResults = results
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }


                },
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                title = { Text("Monte Carlo") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(
                    //text = "Simulating price changes for ${cryptocurrency.name} (${cryptocurrency.symbol})",
                    text = "Simulating price changes",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicTextField(
                        value = steps.toString(),
                        onValueChange = { value -> steps = value.toIntOrNull() ?: 100 },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (steps.toString().isEmpty()) {
                                Text("Steps", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    )
                    BasicTextField(
                        value = simulations.toString(),
                        onValueChange = { value -> simulations = value.toIntOrNull() ?: 50 },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (simulations.toString().isEmpty()) {
                                Text("Simulations", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { runSimulation() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Run Simulation")
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (simulationResults.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Run a simulation to see results.")
                    }
                } else {
                    println("Simulation Results: $simulationResults")
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height

                        // Find the max and min values from simulationResults to scale the values
                        val maxPrice =
                            simulationResults.flatten().maxOfOrNull { it.second.toDouble() } ?: 1.0
                        val minPrice =
                            simulationResults.flatten().minOfOrNull { it.second.toDouble() } ?: 0.0

                        simulationResults.forEach { path ->
                            path.zipWithNext().forEach { (start, end) ->
                                drawLine(
                                    color = Color.Blue,
                                    start = Offset(
                                        x = (start.first / steps.toFloat()) * canvasWidth,
                                        y = canvasHeight - ((start.second.toDouble() - minPrice) / (maxPrice - minPrice) * canvasHeight).toFloat()
                                    ),
                                    end = Offset(
                                        x = (end.first / steps.toFloat()) * canvasWidth,
                                        y = canvasHeight - ((end.second.toDouble() - minPrice) / (maxPrice - minPrice) * canvasHeight).toFloat()
                                    ),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }
                        }
                    }


                }
            }
        }
    )
}
