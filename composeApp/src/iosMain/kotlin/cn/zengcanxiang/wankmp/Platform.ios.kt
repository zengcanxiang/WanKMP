package cn.zengcanxiang.wankmp

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient {
        return HttpClient(
            engineFactory = Darwin,
            block = config
        )
    }
}

actual fun getPlatform(): Platform = IOSPlatform()