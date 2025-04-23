package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.model.CryptoData
import kotlin.math.absoluteValue

@Composable
fun CryptoItem(crypto: CryptoData, onClick: () -> Unit) {
    val price = crypto.priceUsd?.toDoubleOrNull()?.let { "%.2f".format(it) } ?: "N/A"
    val change = crypto.changePercent24Hr?.toDoubleOrNull()
    val changeFormatted = change?.let { "%.2f".format(it) } ?: "N/A"
    val changeColor = when {
        change == null -> Color.Gray
        change > 0 -> Color(0xFF4CAF50) // green
        else -> Color(0xFFF44336) // red
    }
    val marketCap = crypto.marketCapUsd?.toDoubleOrNull()?.let { "%.2f".format(it) } ?: "N/A"
    val volume = crypto.volumeUsd24Hr?.toDoubleOrNull()?.let { "%.2f".format(it) } ?: "N/A"
    val vwap = crypto.vwap24Hr?.toDoubleOrNull()?.let { "%.2f".format(it) } ?: "N/A"

    // Calculate supply percent
    val supply = crypto.supply?.toDoubleOrNull() ?: 0.0
    val maxSupply = crypto.maxSupply?.toDoubleOrNull() ?: 1.0
    val supplyPercent = if (maxSupply != 0.0) "%.2f".format((supply / maxSupply) * 100) else "N/A"

    // Calculate annualized volatility
    val dailyChange = change?.div(100)?.absoluteValue ?: 0.0
    val annualVolatility = "%.2f".format(dailyChange * kotlin.math.sqrt(365.0))

    // Calculate 24h ROI
    val previousPrice = price.toDoubleOrNull()?.let {
        it / (1 + (change?.div(100) ?: 0.0))
    } ?: 0.0
    val roi24h = if (previousPrice != 0.0) "%.2f".format(((price.toDoubleOrNull() ?: 0.0 - previousPrice) / previousPrice) * 100) else "N/A"

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${crypto.name} (${crypto.symbol})",
                            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Price: $$price",
                            style = MaterialTheme.typography.body2.copy(color = Color.Gray)
                        )
                    }
                    Text(
                        text = "$changeFormatted%",
                        color = changeColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Market Cap: $$marketCap", style = MaterialTheme.typography.body2.copy(color = Color.Gray))
                Text(text = "24h Volume: $$volume", style = MaterialTheme.typography.body2.copy(color = Color.Gray))
                Text(text = "VWAP (24h): $$vwap", style = MaterialTheme.typography.body2.copy(color = Color.Gray))
                Text(text = "Supply Circulation: $supplyPercent%", style = MaterialTheme.typography.body2.copy(color = Color.Gray))
                Text(text = "Annualized Volatility: $annualVolatility%", style = MaterialTheme.typography.body2.copy(color = Color.Gray))
                Text(text = "24h ROI: $roi24h%", style = MaterialTheme.typography.body2.copy(color = Color.Gray))
            }
        }
    }
}
