package cn.zengcanxiang.wankmp.network

sealed interface Operation<out Error, out Data> {

    data object Default : Operation<Nothing, Nothing>

    data object Loading : Operation<Nothing, Nothing>

    data class Ok<out Data>(
        val data: Data
    ) : Operation<Nothing, Data>

    data class Error<out Error>(
        val error: Error
    ) : Operation<Error, Nothing>
}
