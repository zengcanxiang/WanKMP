package cn.zengcanxiang.wankmp

import android.os.Build
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient {
        return HttpClient(
            engineFactory = OkHttp,
            block = config
        )
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()