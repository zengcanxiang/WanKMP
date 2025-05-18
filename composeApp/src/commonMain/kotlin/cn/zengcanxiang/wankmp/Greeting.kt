package cn.zengcanxiang.wankmp

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }


    suspend fun x() {
        CoroutineScope(
            context = KtInterceptor()
        )
    }
}

class KtInterceptor(
) : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor.Key

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return KtContinuation(continuation)
    }
}

class KtContinuation<T>(
    private val continuation: Continuation<T>
) : Continuation<T> by continuation {
    override fun resumeWith(result: Result<T>) {
        continuation.resumeWith(result)
    }
}