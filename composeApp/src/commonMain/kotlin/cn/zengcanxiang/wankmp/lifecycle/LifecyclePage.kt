package cn.zengcanxiang.wankmp.lifecycle

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LifecycleStopOrDisposeEffectResult
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun LifecyclePage(
    owner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val state = owner.lifecycle.currentStateFlow.collectAsStateWithLifecycle()
    Text(
        "${state.value.name}"
    )
    val lifecycleOwner = LocalLifecycleOwner.current

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
}