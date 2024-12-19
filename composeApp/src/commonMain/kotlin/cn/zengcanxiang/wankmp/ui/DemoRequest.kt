package cn.zengcanxiang.wankmp.ui

import arrow.core.Either
import cn.zengcanxiang.wankmp.network.AbsResponse
import cn.zengcanxiang.wankmp.network.GetOperation
import cn.zengcanxiang.wankmp.network.HttpError
import io.github.aakira.napier.Napier
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Url
import io.ktor.http.isSuccess

class BaiduGetRequest : GetOperation<Unit, String>() {

    override fun url(): Url {
        return Url("https://www.baidu.com")
    }

    /**
     * 自定义拦截处理
     *
     * 这样可以把返回结构非通用结构[AbsResponse]的httpResponse使用通用的请求包装成对应类型
     *
     * 既不需要修改框架，影响所有代码，也可以复用业务框架下的能力
     */
    override suspend fun processResponse(
        httpResponse: HttpResponse
    ): Either<HttpError, AbsResponse<String>> {
        Napier.d(
            tag = "百度Get请求"
        ) {
            "是否存在cookie:${httpResponse.headers.contains("set-cookie")}"
        }
        return try {
            if (httpResponse.status.isSuccess()) {
                val data = httpResponse.bodyAsText()
                val result = AbsResponse(
                    code = AbsResponse.OK_CODE,
                    message = "",
                    data = data
                )
                Either.Right(result)
            } else {
                // Failure: unsuccessful status code.
                Either.Left(HttpError.API.TCPError(httpResponse))
            }
        } catch (exception: Exception) {
            // Failure: exceptional, something wrong.
            Either.Left(HttpError.Unknown(exception))
        }
    }

}