package cn.zengcanxiang.wankmp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.zengcanxiang.wankmp.network.Operation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun NetworkPage() {
    // 如果使用viewModel<MyViewModel>()这种反射的方式，会在iOS上崩溃，抛出
    // kotlin.UnsupportedOperationException: `Factory.create(String, CreationExtras)` is not implemented.
    val viewModel = viewModel() {
        MyViewModel()
    }

    val resultState by viewModel.responseFlow.collectAsState(
        Operation.Default
    )

    Column {
        when (val result = resultState) {
            is Operation.Default -> {
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
                        viewModel.request()
                    }
                ) {
                    Text("请求")
                }
            }

            is Operation.Loading -> {
                Text("请求中....")
            }

            is Operation.Error -> {
                Row {
                    Text("错误")
                    Button(
                        onClick = {
                            viewModel.retry()
                        }
                    ) {
                        Text("重试")
                    }
                }
            }

            is Operation.Ok -> {
                Text("成功，${result.data.data}")
            }
        }

    }

}

