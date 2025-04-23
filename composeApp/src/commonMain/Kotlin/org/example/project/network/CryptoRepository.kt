package org.example.project.network
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import org.example.project.model.CryptocurrencyModel
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.statement.*

object CryptoRepository {
    private val client = HttpClient(OkHttp)
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun fetchCryptos(): CryptocurrencyModel {
        val response: HttpResponse = client.get(
            "https://rest.coincap.io/v3/assets?apiKey=b6ee03780f6acadd929335235924e61c844785ad513ac283a109894fc814469d"
        )
        val body = response.bodyAsText()
        return json.decodeFromString(CryptocurrencyModel.serializer(), body)
    }
}
