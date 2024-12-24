package cn.zengcanxiang.wankmp.datetime

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.number
import kotlinx.datetime.periodUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

@Composable
fun DateTimeScreen() {
    val dateTime = remember {
        mutableStateOf(
            Clock.System.now()
        )
    }
    val diffDefaultYear = 1949
    val diffDefaultMonth = 10
    val diffDefaultDay = 1
    val diffDefaultHour = 12
    val diffDefaultMinute = 0
    val diffDefaultSecond = 0
    val diffDefault = LocalDate(
        year = diffDefaultYear,
        monthNumber = diffDefaultMonth,
        dayOfMonth = diffDefaultDay
    ).atTime(
        hour = diffDefaultHour,
        minute = diffDefaultMinute,
        second = diffDefaultSecond
    ).toSystemInstant()
    val inputDiffTime = remember {
        mutableStateOf(diffDefault.toEpochMilliseconds().toString())
    }
    Column(
        Modifier.fillMaxWidth().verticalScroll(
            state = rememberScrollState(
                initial = 0,
            )
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            dateTime.value = Clock.System.now()
        }) {
            Text("更新")
        }
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val datetime = dateTime.value

            val format = LocalDateTime.Format {
                byUnicodePattern("yyyy-MM-dd HH:mm:ss")
            }
            SelectionContainer {
                Text("当前时间戳: ${datetime.toEpochMilliseconds()}")
            }
            Text("当前时间字符: ${datetime.toString()}")
            Text("当前时间格式化: ${format.format(datetime.toSystemLocalDate())}")
            Text("当前时间-年: ${datetime.toSystemLocalDate().date.year}")
            Text(
                "当前时间-月: ${datetime.toSystemLocalDate().date.month.number}(${
                    datetime.toLocalDateTime(
                        TimeZone.currentSystemDefault()
                    ).date.month
                })"
            )
            Text("当前时间-日: ${datetime.toSystemLocalDate().date.dayOfMonth}")
            Text("当前时间-时: ${datetime.toSystemLocalDate().time.hour}")
            Text("当前时间-分: ${datetime.toSystemLocalDate().time.minute}")
            Text("当前时间-秒: ${datetime.toSystemLocalDate().time.second}")
            Text(
                "当前时间-加一年: ${
                    datetime.plus(
                        value = 1,
                        unit = DateTimeUnit.YEAR,
                        timeZone = TimeZone.currentSystemDefault()
                    )
                }"
            )
            Text(
                "当前时间-加一月: ${
                    datetime.plus(
                        value = 1,
                        unit = DateTimeUnit.MONTH,
                        timeZone = TimeZone.currentSystemDefault()
                    )
                }"
            )
            Text(
                "当前时间-加一天: ${
                    datetime.plus(
                        value = 1,
                        unit = DateTimeUnit.DAY,
                        timeZone = TimeZone.currentSystemDefault()
                    )
                }"
            )
            Text(
                "当前时间-加一时: ${
                    datetime.plus(
                        value = 1,
                        unit = DateTimeUnit.HOUR,
                        timeZone = TimeZone.currentSystemDefault()
                    )
                }"
            )
            Text(
                "当前时间-加一分: ${
                    datetime.plus(
                        value = 1,
                        unit = DateTimeUnit.MINUTE,
                        timeZone = TimeZone.currentSystemDefault()
                    )
                }"
            )
            Text(
                "当前时间-加一秒: ${
                    datetime.plus(
                        value = 1,
                        unit = DateTimeUnit.MICROSECOND,
                        timeZone = TimeZone.currentSystemDefault()
                    )
                }"
            )

            TextField(
                value = inputDiffTime.value,
                onValueChange = {
                    inputDiffTime.value = it
                },
            )
            //TODO 在这里新增时间选择器，去修改时间
            val inputDiffTimeMilliseconds = inputDiffTime.value.toLong()
            val userInputDiffDateTime = Instant.Companion.fromEpochMilliseconds(
                inputDiffTimeMilliseconds
            )
            val diffDate = userInputDiffDateTime.toSystemLocalDate()
            val diffYear = diffDate.year
            val diffMonth = diffDate.month.number
            val diffDay = diffDate.dayOfMonth
            val diffHour = diffDate.hour
            val diffMinute = diffDate.minute
            val diffSecond = diffDate.second
            val diffResult = datetime.periodUntil(
                other = userInputDiffDateTime,
                timeZone = TimeZone.currentSystemDefault()
            )
            Text("${diffYear}年${diffMonth}月${diffDay}号${diffHour}点${diffSecond}分${diffSecond}秒离我们现在差距${diffResult.years}年")
            Text("${diffYear}年${diffMonth}月${diffDay}号${diffHour}点${diffSecond}分${diffSecond}秒离我们现在差距${diffResult.months}月")
            Text("${diffYear}年${diffMonth}月${diffDay}号${diffHour}点${diffSecond}分${diffSecond}秒离我们现在差距${diffResult.days}日")
            Text("${diffYear}年${diffMonth}月${diffDay}号${diffHour}点${diffSecond}分${diffSecond}秒离我们现在差距${diffResult.hours}时")
            Text("${diffYear}年${diffMonth}月${diffDay}号${diffHour}点${diffSecond}分${diffSecond}秒离我们现在差距${diffResult.minutes}分")
            Text("${diffYear}年${diffMonth}月${diffDay}号${diffHour}点${diffSecond}分${diffSecond}秒离我们现在差距${diffResult.seconds}秒")
            Text("${diffYear}年${diffMonth}月${diffDay}号${diffHour}点${diffSecond}分${diffSecond}秒离我们现在绝对值差距${userInputDiffDateTime.toEpochMilliseconds() - datetime.toEpochMilliseconds()}毫秒")

            Text("${diffYear}年${diffMonth}月${diffDay}号${diffHour}点${diffSecond}分${diffSecond}秒是一个我们${if (userInputDiffDateTime < datetime) "以前" else "以后"}的时间")
            val formatStr = userInputDiffDateTime.format(
                format = DateTimeComponents.Format {
                    formatCustom(
                        diffDateTime = userInputDiffDateTime
                    )
                },
            )
            Text(
                """
                    自定义格式化：显示输入框里时间和当前的差距：
                    如果在30分钟内，显示刚刚
                    如果大于30分钟，小于一小时，显示半小时前
                    如果大于1小时，小于一天，显示n小时前
                    如果大于1天，小于30天，显示n天前
                    如果大于一个月小于一年，显示n个月前
                    如果大于一年，显示x年x月x日
                    $formatStr
                """.trimIndent()
            )
            Button(
                onClick = {
                    val originLocalDateTime = datetime.toSystemLocalDate()
                    dateTime.value = LocalDate(
                        year = diffYear,
                        month = originLocalDateTime.month,
                        dayOfMonth = originLocalDateTime.dayOfMonth,
                    ).atTime(
                        originLocalDateTime.time
                    ).toSystemInstant()
                },
            ) {
                Text("修改当前时间的年为${diffYear}年")
            }

            Button(
                onClick = {
                    val originLocalDateTime = datetime.toSystemLocalDate()
                    dateTime.value = LocalDate(
                        year = originLocalDateTime.year,
                        monthNumber = diffMonth,
                        dayOfMonth = originLocalDateTime.dayOfMonth,
                    ).atTime(
                        originLocalDateTime.time
                    ).toSystemInstant()
                },
            ) {
                Text("修改当前时间的月为${diffMonth}月")
            }

            Button(
                onClick = {
                    val originLocalDateTime = datetime.toSystemLocalDate()
                    dateTime.value = LocalDate(
                        year = originLocalDateTime.year,
                        month = originLocalDateTime.month,
                        dayOfMonth = diffDay,
                    ).atTime(
                        originLocalDateTime.time
                    ).toSystemInstant()
                },
            ) {
                Text("修改当前时间的日为${diffDay}号")
            }

            Button(
                onClick = {
                    val originLocalDateTime = datetime.toSystemLocalDate()
                    dateTime.value = LocalDate(
                        year = originLocalDateTime.year,
                        month = originLocalDateTime.month,
                        dayOfMonth = diffDay,
                    ).atTime(
                        hour = diffHour,
                        minute = originLocalDateTime.hour,
                        second = originLocalDateTime.hour,
                    ).toSystemInstant()
                },
            ) {
                Text("修改当前时间的时为${diffHour}点")
            }

            Button(
                onClick = {
                    val originLocalDateTime = datetime.toSystemLocalDate()
                    dateTime.value = LocalDate(
                        year = originLocalDateTime.year,
                        month = originLocalDateTime.month,
                        dayOfMonth = diffDay,
                    ).atTime(
                        hour = originLocalDateTime.hour,
                        minute = diffMinute,
                        second = originLocalDateTime.hour,
                    ).toSystemInstant()
                },
            ) {
                Text("修改当前时间的分为${diffMinute}分")
            }

            Button(
                onClick = {
                    val originLocalDateTime = datetime.toSystemLocalDate()
                    dateTime.value = LocalDate(
                        year = originLocalDateTime.year,
                        month = originLocalDateTime.month,
                        dayOfMonth = diffDay,
                    ).atTime(
                        hour = originLocalDateTime.hour,
                        minute = originLocalDateTime.hour,
                        second = diffSecond,
                    ).toSystemInstant()
                },
            ) {
                Text("修改当前时间的秒为${diffSecond}秒")
            }
        }
    }
}