package cn.zengcanxiang.wankmp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import cn.zengcanxiang.wankmp.ktor.KtorClientHelper
import io.ktor.client.request.request
import io.ktor.http.Url
import org.jetbrains.compose.ui.tooling.preview.Preview

val viewModel by lazy {
    MyViewModel()
}

@Composable
@Preview
fun NetworkPage() {
    Column {
        var text by remember {
            mutableStateOf("")
        }

        val rainbowColors = listOf(
            Color.Yellow,
            Color.Green,
            Color.Red,
        )
        val brush = remember {
            Brush.linearGradient(
                colors = rainbowColors
            )
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("请输入get请求地址") },
            textStyle = TextStyle(brush = brush),
        )

        Button(
            onClick = {
                viewModel.requestByGet(
                    url = text,
                )
            }
        ) {
            Text("请求")
        }
    }

}

