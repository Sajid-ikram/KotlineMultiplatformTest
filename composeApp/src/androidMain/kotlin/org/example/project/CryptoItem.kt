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

    Surface(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(12.dp))
            .clickable { /* Navigate to detail/chart */ },
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onClick() }
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
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
        }
    }
}
