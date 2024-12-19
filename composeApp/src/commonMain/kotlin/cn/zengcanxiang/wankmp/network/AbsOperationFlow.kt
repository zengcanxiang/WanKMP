package cn.zengcanxiang.wankmp.network

import arrow.core.Either
import arrow.core.flatMap
import cn.zengcanxiang.wankmp.network.AbsResponse.Companion.isOk
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Url

abstract class AbsOperationFlow<in RequestParams, out Response> :
    OperationFlow<RequestParams, AbsResponse<Response>, HttpError>() {

    override suspend fun operation(
        requestParams: RequestParams
    ): Either<HttpError, AbsResponse<Response>> = httpRequest(
        request = {
            requestImpl(
                httpClient = this,
            )
        },
        responseProcess = { response ->
            // 这样，不仅可以把对response的解析抽象和剥离具体实现
            // 也可以自类自定义response的解析，还能够实现，某些特殊的情况下，需要使用Header头来参与解析
            // 还能对自类的自定义解析进行校验，必须符合Abs规范
            processResponse(
                httpResponse = response,
            ).flatMap {
                if (it.isOk()) {
                    Either.Right(it)
                } else {
                    Either.Left(
                        HttpError.API.CustomError(
                            response = response,
                            message = "不符合成功数据，必须code == 0 的规范"
                        )
                    )
                }
            }
        }
    )

    abstract suspend fun requestImpl(httpClient: HttpClient): HttpResponse

    open suspend fun processResponse(
        httpResponse: HttpResponse
    ): Either<HttpError, AbsResponse<Response>> {
        return httpResponse.testCommonProcess<AbsResponse<Response>>()
    }
}


abstract class GetOperation<in RequestParams, out Response> :
    AbsOperationFlow<RequestParams, Response>() {

    override suspend fun requestImpl(httpClient: HttpClient): HttpResponse {
        return httpClient.get(
            url = url(),
            block = {
                // 通用get请求配置
            }
        )
    }

    abstract fun url(): Url
}

data class AbsResponse<out T>(
    val code: Int,
    val message: String,
    val data: T
) {
    companion object {
        const val OK_CODE = 0
        fun AbsResponse<*>.isOk(): Boolean {
            return code == OK_CODE
        }
    }
}