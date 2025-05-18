# Kotlin multi platform lifecycle

官方文档：https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-lifecycle.html

## 各个平台的生命周期

参考官方文档的https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-lifecycle.html#mapping-android-lifecycle-to-other-platforms

## kmp的lifecycle

### jetpack的lifecycle

### compose的lifecycle

https://developer.android.com/develop/ui/compose/lifecycle?hl=zh-cn

https://juejin.cn/post/7409869765177147430

### 平台映射

## 实际代码


应用程序的生命周期：

页面的生命周期：

组合项的生命周期：

每个 Composable 都有生命周期所有者LocalLifeCycleOwner.current

需要同时考虑 compose的生命周期，和原生平台的生命周期

一般是：flow-》asState + lifecycle.repeatOnLifecycle

官方实现: collectAsStateWithLifecycle

监听生命周期：
DisposableEffect(lifecycleOwner) {
val observer = LifecycleEventObserver { _, event ->
when (event) {
Lifecycle.Event.ON_CREATE -> {
/* onCreate */
}

                Lifecycle.Event.ON_START -> { /* onStart */
                }

                Lifecycle.Event.ON_RESUME -> { /* onResume */
                }

                Lifecycle.Event.ON_PAUSE -> { /* onPause */
                }

                Lifecycle.Event.ON_STOP -> { /* onStop */
                }

                Lifecycle.Event.ON_DESTROY -> { /* onDestroy */
                }

                Lifecycle.Event.ON_ANY -> { /* Any event */
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }