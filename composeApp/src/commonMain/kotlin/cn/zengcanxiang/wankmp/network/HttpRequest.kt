package cn.zengcanxiang.wankmp.network

import arrow.core.Either
import cn.zengcanxiang.wankmp.ktor.KtorClientHelper
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <reified Response> httpRequest(
    ktorClient: HttpClient = KtorClientHelper.client,
    crossinline responseProcess: suspend (HttpResponse) -> Either<HttpError, Response>,
    crossinline request: suspend HttpClient.() -> HttpResponse
): Either<HttpError, Response> = withContext(Dispatchers.Default) {
    try {
        responseProcess.invoke(
            request(ktorClient)
        )
    } catch (exception: Exception) {
        // Failure: exceptional, something wrong.
        Either.Left(HttpError.Unknown(exception))
    }
}
