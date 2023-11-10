package com.natiqhaciyef.composable_widget.component.common.util.helpers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs


fun ticketTimeCalculator(from: String, to: String): String {
    val preFrom = from.substring(0..1).toDouble()
    val sufFrom = from.substring(3..4).toDouble()

    val preTo = to.substring(0..1).toDouble()
    val sufTo = to.substring(3..4).toDouble()

    val fromX = preFrom * 60 + sufFrom
    val toX = preTo * 60 + sufTo
    val res = abs(fromX - toX) / 60

    return fromDoubleToTimeLine(res)
}

fun fromDateToDay(day: String): Int = if (day.startsWith("0")) 1 else 0


fun fromDoubleToTimeLine(d: Double = 7.5): String {
    val hours = d.toInt()
    val minutes = (60 * (d - hours)).toInt()

    return "$hours hours\n$minutes minutes"
}


fun fromDateToMonth(month: String) = when (month) {
    "01" -> {
        "January"
    }

    "02" -> {
        "February"
    }

    "03" -> {
        "March"
    }

    "04" -> {
        "April"
    }

    "05" -> {
        "May"
    }

    "06" -> {
        "June"
    }

    "07" -> {
        "July"
    }

    "08" -> {
        "August"
    }

    "09" -> {
        "September"
    }

    "10" -> {
        "October"
    }

    "11" -> {
        "November"
    }

    "12" -> {
        "December"
    }

    else -> "Time left"
}


fun fromDoubleToTime(d: Double = 7.5): String {
    val hours = d.toInt()
    val minutes = (60 * (d - hours)).toInt()

    return "$hours hours\n$minutes minutes"
}


fun majorStringToDateChanger(s: String = "01.12.2001 15:59"): String {
    val subDay = s.substring(0..1)
    val subMonth = s.substring(3..4)
    val subYear = s.substring(6..9)
    val subTime = s.substring(11..15)

    val day = if (subDay.startsWith("0")) subDay[1] else subDay
    val month = fromDateToMonth(subMonth)
    val time = fromStringToMappedTime(subTime)

    return "$time ($day $month, $subYear)"
}

fun fromStringToMappedTime(time: String): String {
    val start = time.substring(0..1)

    return if (start.toInt() > 12) {
        "${start.toInt() - 12}:${time.substring(3..4)} PM"
    } else {
        if (time.startsWith("0")) "${time.substring(1 until time.length)} AM" else "$time AM"
    }
}

fun fromStringToMappedDay(date: String): String {
    val day = date.substring(0..1)
    val month = date.substring(3..4)

    return "$day ${fromDateToMonth(month)}"
}


fun getNow(dateTime: LocalDateTime = LocalDateTime.now()): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    return formatter.format(dateTime)
}

fun String.stringToFormattedLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    return LocalDateTime.parse(this, formatter)
}