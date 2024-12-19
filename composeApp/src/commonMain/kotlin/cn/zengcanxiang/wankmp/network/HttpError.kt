package cn.zengcanxiang.wankmp.network

import io.ktor.client.statement.HttpResponse

sealed interface HttpError {
    sealed interface API : HttpError {
        data class TCPError(
            val response: HttpResponse
        ) : API

        data class CustomError(
            val response: HttpResponse,
            val message: String
        ) : API
    }

    data class Unknown(val exception: Exception) : HttpError
}