package cn.zengcanxiang.wankmp.network

import arrow.core.Either
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.util.reflect.typeInfo
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * 测试情况下的通用response结构的某种情况的处理
 *
 * 模拟返回数据都是符合泛型[Response]标准的
 */
@OptIn(InternalSerializationApi::class)
suspend inline fun <reified Response> HttpResponse.testCommonProcess(): Either<HttpError, Response> {
    return try {
        if (this.status.isSuccess()) {
            // Success: 200 <= status code <= 299.
            // 如果有kotlinx.serialization + t泛型，则会自动json解析
            val data: Response = this.body()
            Either.Right(data)
        } else {
            // Failure: unsuccessful status code.
            Either.Left(HttpError.API.TCPError(this))
        }
    } catch (exception: Exception) {
        // Failure: exceptional, something wrong.
        Either.Left(HttpError.Unknown(exception))
    }
}