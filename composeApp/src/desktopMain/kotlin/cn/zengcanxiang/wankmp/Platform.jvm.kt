package cn.zengcanxiang.wankmp

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"

    override fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient {
        return HttpClient(
            engineFactory = CIO,
            block = config
        )
    }
}

actual fun getPlatform(): Platform = JVMPlatform()