package org.example.project.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptocurrencyModel(
    val data: List<CryptoData>? = null,
    val timestamp: Long? = null
)

@Serializable
data class CryptoData(
    val id: String? = null,
    val rank: String? = null,
    val symbol: String? = null,
    val name: String? = null,
    val supply: String? = null,
    @SerialName("maxSupply")
    val maxSupply: String? = null,
    @SerialName("marketCapUsd")
    val marketCapUsd: String? = null,
    @SerialName("volumeUsd24Hr")
    val volumeUsd24Hr: String? = null,
    @SerialName("priceUsd")
    val priceUsd: String? = null,
    @SerialName("changePercent24Hr")
    val changePercent24Hr: String? = null,
    @SerialName("vwap24Hr")
    val vwap24Hr: String? = null,
    val explorer: String? = null
)
