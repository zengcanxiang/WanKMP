package cn.zengcanxiang.wankmp

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.js.Js

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
    override fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient {
        return HttpClient(
            engineFactory = Js,
            block = config
        )
    }
}

actual fun getPlatform(): Platform = WasmPlatform()