package cn.zengcanxiang.wankmp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.zengcanxiang.wankmp.ktor.KtorClientHelper
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Url
import io.ktor.util.logging.Logger
import io.ktor.utils.io.core.use
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class MyViewModel : ViewModel() {

    val data = emptyFlow<String>()

    fun requestByGet(url: String) {
        viewModelScope.launch() {
            val response = KtorClientHelper.client.request(
                url = Url(
                    "https://www.baidu.com"
                )
            ) {

            }
            val body = response.bodyAsText()
            Napier.e(
                "打印结果 $body"
            )
        }
    }

}

data class TestDody(
    val data:String
)