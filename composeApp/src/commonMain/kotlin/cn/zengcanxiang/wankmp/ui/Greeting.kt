package cn.zengcanxiang.wankmp.ui

import cn.zengcanxiang.wankmp.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}