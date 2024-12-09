# Ktor-Client

前言：我们通过下方的配置，基本上满足一个项目需要进行网络请求的基础框架，但是作为跨平台，在检验一个网络请求功能时，我们同时需要搭建出其他的内容，网络请求往往不是独立存在的。

我们同时还需要掌握以下能力：
1. 如何在跨平台下掌握打印日志和查看日志的能力：[README_DEBUG.m](./README/debug/README_DEBUG.md)
2. 如何在代码中异步监听网络请求的结果：[README_KTOR_FLOW.m](./README_KTOR_FLOW.md)
3. 如何在代码中异步发起网络请求：协程(多线程发起请求和等待)、viewModel(kotlinx对逻辑封装的官方推荐组件)



源码分析：https://juejin.cn/post/7396930610537168947


## 导入
```
sourceSets {
    val desktopMain by getting

    androidMain.dependencies {
        //...
        implementation("io.ktor:ktor-client-android:ktor_version")
    }
    commonMain.dependencies {
        //...
        implementation("io.ktor:ktor-client-core:ktor_version")
        implementation("io.ktor:ktor-client-content-negotiation:ktor_version")
        implementation("io.ktor:ktor-serialization-kotlinx-json:ktor_version")
    }
    desktopMain.dependencies {
        //...
        implementation("io.ktor:ktor-client-cio:ktor_version")
    }
    iosMain.dependencies {
        implementation("io.ktor:ktor-client-darwin:ktor_version")
        或者
        implementation("io.ktor:ktor-client-ios:ktor_version")
    }
    wasmJsMain.dependencies {
        implementation("io.ktor:ktor-client-js:ktor_version")
    }
}
```

| 库    | 官方地址                                                                        |
|------|-----------------------------------------------------------------------------|
| ktor | 官方介绍和入门：https://ktor.io/docs/welcome.html <br/>官方API文档：https://api.ktor.io/ | https://ktor.io/docs/client-engines.html#dependencies |


| 配套库             | 作用     | 依赖                                                                                                                                                                                                                | 官方资源                                                                                                                                                            |
|-----------------|--------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| JSON serializer | 解析接口数据 | ktor-negotiation = {group = "io.ktor", name = "ktor-client-content-negotiation",version.ref = "ktor"}<br/>ktor-serialization = {group = "io.ktor", name = "ktor-serialization-kotlinx-json",version.ref = "ktor"} | demo : https://github.com/ktorio/ktor-documentation/tree/3.0.2/codeSnippets/snippets/client-json-kotlinx <br> 文档：https://ktor.io/docs/client-serialization.html |
| Logging         | 日志抽象层     | implementation("io.ktor:ktor-client-logging:$ktor_version")                                                                                                                                                       | https://ktor.io/docs/client-logging.html                                                                                                                        | 


## 执行

### 在协程里执行


#### android

#### iOS

#### desktop

#### js


#### 多个请求

```kotlin
val client = HttpClient()
runBlocking {
    // Parallel requests
    val firstRequest: Deferred<String> = async { client.get("http://localhost:8080/path1").bodyAsText() }
    val secondRequest: Deferred<String> = async { client.get("http://localhost:8080/path2").bodyAsText() }
    val firstRequestContent = firstRequest.await()
    val secondRequestContent = secondRequest.await()
}
```

#### 取消请求

### request

#### get 


```kotlin
val client = HttpClient()
val response: HttpResponse = client.get("https://ktor.io") {
    url {
        parameters.append("token", "abc123")
    }
}
```

#### post

```kotlin
val client = HttpClient()
val response: HttpResponse = client.post("http://localhost:8080/post") {
    setBody("Body content")
}
```

开启ContentNegotiation plugin后，可以使用

```kotlin
val client = HttpClient()
val response: HttpResponse = client.post("http://localhost:8080/customer") {
    contentType(ContentType.Application.Json)
    setBody(Customer(3, "Jet", "Brains"))
}
```

#### 文件上传

##### post表单提交

```kotlin
val client = HttpClient()

val response: HttpResponse = client.post("http://localhost:8080/upload") {
    setBody(MultiPartFormDataContent(
        formData {
            append("description", "Ktor logo")
            append("image", File("ktor_logo.png").readBytes(), Headers.build {
                append(HttpHeaders.ContentType, "image/png")
                append(HttpHeaders.ContentDisposition, "filename=\"ktor_logo.png\"")
            })
        },
        boundary = "WebAppBoundary"
    )
    )
    onUpload { bytesSentTotal, contentLength ->
        println("Sent $bytesSentTotal bytes from $contentLength")
    }
}
```

#### 二进制流请求上传

```kotlin
val client = HttpClient()
val response = client.post("http://0.0.0.0:8080/upload") {
    setBody(File("ktor_logo.png").readChannel())
}
```


### response

#### 解析数据

#### 下载文件
```kotlin
val client = HttpClient()
val file = File.createTempFile("files", "index")

runBlocking {
    val httpResponse: HttpResponse = client.get("https://ktor.io/") {
        onDownload { bytesSentTotal, contentLength ->
            println("Received $bytesSentTotal bytes from $contentLength")
        }
    }
    val responseBody: ByteArray = httpResponse.body()
    file.writeBytes(responseBody)
    println("A file saved to ${file.path}")
}
```
分块下载

```kotlin
val client = HttpClient()
val file = File.createTempFile("files", "index")

runBlocking {
    client.prepareGet("https://ktor.io/").execute { httpResponse ->
        val channel: ByteReadChannel = httpResponse.body()
        while (!channel.isClosedForRead) {
            val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
            while (!packet.isEmpty) {
                val bytes = packet.readBytes()
                file.appendBytes(bytes)
                println("Received ${file.length()} bytes from ${httpResponse.contentLength()}")
            }
        }
        println("A file saved to ${file.path}")
    }
}
```

#### SSL配置

### cookie
```kotlin
val client = HttpClient() {
    install(HttpCookies)
}
```

自定义存储

```kotlin
val client = HttpClient(CIO) {
    install(HttpCookies) {
        storage = CustomCookiesStorage()
    }
}

public class CustomCookiesStorage : CookiesStorage {
    // ...
}
```

### UA

```kotlin
val client = HttpClient() {
    install(UserAgent) {
        agent = "Ktor client"
    }
}
```

### 编解码

```kotlin
implementation("io.ktor:ktor-client-encoding:$ktor_version")
```

```kotlin
val client = HttpClient() {
    install(ContentEncoding) {
        deflate(1.0F)
        gzip(0.9F)
    }
}
```

### 缓存

### 自定义插件

```kotlin
val CustomHeaderConfigurablePlugin = createClientPlugin("CustomPluginName", ::CustomPluginConfig) {
    
    onRequest { request, _ ->

    }

    transformRequestBody{

    }

    onResponse { response ->
    
    }

    transformResponseBody{

    }

    onClose {

    }
}

class CustomPluginConfig {

}
```

如何自定义一个切换各个环节域名的插件？
如何自定义一个生成加密head头的插件？
如何自定义一个接口需要二次校验(防攻击，人机交互校验的)，成功后自动重试请求的插件？

```kotlin
import io.ktor.client.plugins.api.*
import io.ktor.http.*

val AuthPlugin = createClientPlugin("AuthPlugin", ::AuthPluginConfig) {
    val token = pluginConfig.token

    on(Send) { request ->
        val originalCall = proceed(request)
        originalCall.response.run { // this: HttpResponse
            if(status == HttpStatusCode.Unauthorized && headers["WWW-Authenticate"]!!.contains("Bearer")) {
                request.headers.append("Authorization", "Bearer $token")
                proceed(request)
            } else {
                originalCall
            }
        }
    }
}

class AuthPluginConfig {
    var token: String = ""
}
```