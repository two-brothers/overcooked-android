package com.bigfriedicecream.overcooked.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import kotlin.math.roundToInt

fun Int.Companion.minsToPrettyTimeFormat(mins:Int):String {
    val hours = mins / 60
    val leftoverMins = String.format("%02d", mins % 60) // formats number to 2 digits
    return "$hours:$leftoverMins"
}

fun Double.Companion.fractionFromNumber(value:Double):String {
    val intPart = value.toInt()
    val fractionPart = value - intPart

    if (fractionPart > 0.125 && fractionPart < 0.29) return (if (intPart > 0) "$intPart " else "") + "1/4"
    if (fractionPart > 0.29 && fractionPart < 0.415) return (if (intPart > 0) "$intPart " else "") + "1/3"
    if (fractionPart > 0.415 && fractionPart < 0.58) return (if (intPart > 0) "$intPart " else "") + "1/2"
    if (fractionPart > 0.58 && fractionPart < 0.705) return (if (intPart > 0) "$intPart " else "") + "2/3"
    if (fractionPart > 0.705 && fractionPart < 0.875) return (if (intPart > 0) "$intPart " else "") + "3/4"

    return value.roundToInt().toString()
}

fun String.Companion.fromHtml(html:String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY) else Html.fromHtml(html)
}