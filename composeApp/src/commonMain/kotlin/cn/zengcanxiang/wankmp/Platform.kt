package cn.zengcanxiang.wankmp

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

interface Platform {
    val name: String

    fun httpClient(
        config: HttpClientConfig<*>.() -> Unit = {},
    ): HttpClient
}


expect fun getPlatform(): Platform