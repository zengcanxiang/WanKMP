package cn.zengcanxiang.wankmp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private val baiduGetRequest = BaiduGetRequest()

    val responseFlow = baiduGetRequest.listener()

    fun request() {
        viewModelScope.launch {
            baiduGetRequest.request(Unit)
        }
    }

    fun retry() {
        viewModelScope.launch {
            baiduGetRequest.retry()
        }
    }
}