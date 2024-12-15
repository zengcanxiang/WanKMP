package cn.zengcanxiang.wankmp.ktor

import cn.zengcanxiang.wankmp.getPlatform
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json

object KtorClientHelper {

    private val platform by lazy {
        getPlatform()
    }

    val client by lazy {
        platform.httpClient {
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                logger = Logger.Companion.DEFAULT
                level = LogLevel.HEADERS
//                filter { request ->
//                    request.url.host.contains("ktor.io")
//                }
//                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.e(
                            message = message,
                            throwable = null,
                            tag = "HTTP_CLIENT"
                        )
                    }
                }
                // 正式包使用None
                level = LogLevel.ALL
            }.also {
                // 这个antiLog是什么？
                Napier.base(DebugAntilog())
            }
        }
    }
}