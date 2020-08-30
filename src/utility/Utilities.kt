package utility

import java.text.NumberFormat
import java.util.Random

fun numberFormat(num: Int): String = NumberFormat.getIntegerInstance().format(num)

fun getFileName() = getString("File name:")

// this useful random function was found on Stack Overflow
fun IntRange.random() = Random().nextInt(endInclusive + 1 - start) + start

fun getNum(text: String, defaultMessage: Boolean = false): Int {
    val strErrorNum = " was not a number, please try again: "
    var num = text
    var default = defaultMessage

    do {
        num = getString(if (default) num + strErrorNum else num)
        if (!default) default = true
    } while (!isNumber(num))

    return num.toInt()
}

fun getString(text: String): String {
    println(text)
    return readLine()!!
}

private fun isNumber(number: String) = number.toIntOrNull() != null