package cn.zengcanxiang.wankmp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cn.zengcanxiang.wankmp.datetime.DateTimeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        DateTimeScreen()
    }
}