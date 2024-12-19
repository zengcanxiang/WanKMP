package cn.zengcanxiang.wankmp.network

import arrow.core.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * 包装一个网络请求发起和监听的流程
 *
 * 使用泛型来描述请求参数[RequestParams]、响应结果[Response]、和异常错误[Error]
 */
abstract class OperationFlow<in RequestParams, out Response, out Error> {

    /**
     * 子类需要实现的，真正进行网络请求的地方
     */
    protected abstract suspend fun operation(
        requestParams: RequestParams
    ): Either<Error, Response>

    /**
     * 通过热流，来内部监听他，如果他发出来了数据，就认为是发起一次请求的信号
     */
    private val triggerFlow = MutableSharedFlow<RequestParams>(
        replay = 1
    )

    /**
     * 上一次请求的参数
     */
    private var lastRequest: RequestParams? = null

    /**
     * 获取网络请求的监听
     *
     * TODO 是否存在同步请求的场景？
     *
     * @return 具有[kotlinx.coroutines.channels.Channel]特性的flow，来让外界监听到网络请求的结果，会包含请求状态[Operation.Loading]+[Operation.Ok]和错误[Operation.Error]
     */
    fun listener(): Flow<Operation<Error, Response>> = channelFlow {
        // 用户监听的时候，对热流设置一个监听，用户在这个之前和之后设置的请求数据，都能够立马发起一次请求
        //TODO 如果对这个listener调用多次，会怎么样？
        triggerFlow.collectLatest {
            // 请求前，发送loading的状态
            send(
                Operation.Loading
            )
            send(
                // 发送请求结果
                when (val result = operation(it)) {
                    is Either.Left -> {
                        Operation.Error(
                            result.value
                        )
                    }

                    is Either.Right -> {
                        Operation.Ok(
                            result.value
                        )
                    }
                }
            )
        }
    }

    suspend fun retry() {
        lastRequest?.let {
            triggerFlow.emit(it)
        }
    }

    suspend fun request(
        request: RequestParams
    ) {
        this.lastRequest = request
        triggerFlow.emit(request)
    }
}