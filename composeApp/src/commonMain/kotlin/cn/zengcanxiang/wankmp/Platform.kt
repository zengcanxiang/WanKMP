package cn.zengcanxiang.wankmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform