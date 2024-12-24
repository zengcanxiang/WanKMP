package cn.zengcanxiang.wankmp.datetime

import io.ktor.client.request.forms.formData
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormatBuilder
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.absoluteValue


fun Instant.toSystemLocalDate(): LocalDateTime {
    return this.toLocalDateTime(
        timeZone = TimeZone.currentSystemDefault(),
    )
}

fun LocalDateTime.toSystemInstant(): Instant {
    return this.toInstant(
        timeZone = TimeZone.currentSystemDefault(),
    )
}

fun DateTimeFormatBuilder.WithDate.formatCustom(
    diffDateTime: LocalDateTime
) {
    formatCustom(
        diffDateTime.toSystemInstant()
    )
}

/**
 * TODO 这个diffDateTime是否可以取到，而不用传进来
 * 理论上，这些 year等函数的值就是从这个date来的
 *
 * diffPeriod取出来的值可能是负数的
 */
fun DateTimeFormatBuilder.WithDate.formatCustom(
    diffDateTime: Instant
) {
    val diffPeriod = diffDateTime.periodUntil(
        other = Clock.System.now(),
        timeZone = TimeZone.currentSystemDefault()
    )

    fun formatAll() {
        year()
        chars("年")
        monthNumber()
        chars("月")
        dayOfMonth()
        chars("日")
    }
    when {
        diffPeriod.years != 0 -> {
            formatAll()
        }

        diffPeriod.months != 0 -> {
            val months = diffPeriod.months
            if (months > 0) {
                chars("${months}个月前")
            } else {
                formatAll()
            }
        }

        diffPeriod.days != 0 -> {
            val days = diffPeriod.days
            if (days > 0) {
                chars("${days}天前")
            } else {
                chars("${days.absoluteValue}天后")
            }
        }

        diffPeriod.hours != 0 -> {
            val hours = diffPeriod.hours
            if (hours > 0) {
                chars("${hours}小时前")
            } else {
                chars("${hours.absoluteValue}小时前")
            }
        }

        diffPeriod.minutes > 0 -> {
            val minutes = diffPeriod.minutes
            if (minutes > 0) {
                if (minutes < 30) {
                    chars("刚刚")
                } else {
                    chars("半小时前")
                }
            } else {
                chars("${minutes.absoluteValue}分钟后")
            }
        }

        else -> {
            formatAll()
        }
    }
}


/**
 * 格式化显示输入框里时间和当前的差距：
 * 如果在30分钟内，显示刚刚
 * 如果大于30分钟，小于一小时，显示半小时前
 * 如果大于1小时，小于一天，显示n小时前
 * 如果大于1天，小于30天，显示n天前
 * 如果大于一个月小于一年，显示n个月前
 * 如果大于一年，显示x年x月x日
 */
fun DateTimeFormatBuilder.WithDate.formatCustom(
    diffDateTime: Long
) {
    formatCustom(
        Instant.fromEpochSeconds(diffDateTime)
    )
}