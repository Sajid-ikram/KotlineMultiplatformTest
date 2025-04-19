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
            "https://rest.coincap.io/v3/assets?apiKey=f955f902fe4e99cc913e39a0cd56a74f71d5740bf9c957516af3a21269610464"
        )
        val body = response.bodyAsText()
        return json.decodeFromString(CryptocurrencyModel.serializer(), body)
    }
}
