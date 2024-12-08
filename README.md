# WanKMP

这是一个针对 Android、iOS、Web 和桌面的 Kotlin 多平台项目。

在这里，我将会去学习kotlin multiplatform 项目的技术，并且尝试搭建一个自己的技术路线。

我将按照以下的目标去实现，并且每个知识点将从当前构建出来的基础分支上，切出一个新的分支，留做对比和记录.


## 搭建kmp环境


* `/composeApp` 用于将在您的 Compose 多平台应用程序中共享的代码。
  它包含几个子文件夹：
- `commonMain` 用于所有目标通用的代码。
- 其他文件夹用于仅为文件夹名称中指示的平台编译的 Kotlin 代码。
  例如，如果您想将 Apple 的 CoreCrypto 用于 Kotlin 应用程序的 iOS 部分，
  `iosMain` 将是此类调用的正确文件夹。
* `/iosApp` 包含 iOS 应用程序。即使您与 Compose 多平台共享 UI，
  也需要此 iOS 应用程序入口点。这也是您应该为您的项目添加 SwiftUI 代码的地方。

分支名：https://github.com/zengcanxiang/WanKMP/tree/main

参考资料链接：

| 内容     | 链接                                                                                                                                                                          |
|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 官方指南   | https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html 、https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform 、  https://kotl.in/wasm |
| 创建项目   | https://kmp.jetbrains.com                                                                                                                                                   |
| 设置运行命令 | https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-create-first-app.html#run-your-application                                                    |
| 想到再加   |                                                                                                                                                                             |



## kmp依赖

网络，json序列化，sql，时间，协程，flow，paging，

## kmp框架 

权限申请，网络状态监听，系统时间变化监听，路由，图片加载，可见性埋点

## kmp运行时 

和原生控件相互嵌套和调用，安装新版本，系统浏览器打开链接，安装后的文件目录，操作和选择文件，拍照和录像，获取系统相册，保存可见和私有的图片，触摸手势和鼠标、触摸板、键盘快捷键交互，皮肤+日夜间适配，多语言，通知，

## kmp动效 

进度，渐变，位移，缩放，

