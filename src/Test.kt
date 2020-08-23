/** When adding a new algorithm update:
 *      main(), menu(), average(), and newArray().
 *  If there are any new options for it update:
 *      options() and optionMenu() */

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.Random
import kotlin.math.absoluteValue

private var MIN = 0
private var MAX = 400_000
private var NUMBERS = import("numbers50K.txt")
private var JUMP_SEARCH = JumpSearch(NUMBERS)
private var BINARY_SEARCH = BinarySearch(NUMBERS)
private var PRINT_SEARCH = true
private var PRINT_ARRAY = false
private var LIN_FORWARD = true
private var IMPORTED = true


fun main() {
    var command = getNum(menu())

    while (command != 8) {
        when (command) {
            1 -> search(BINARY_SEARCH)
            2 -> search(JUMP_SEARCH)
            3 -> average()
            4 -> newArray()
            5 -> export()
            6 -> importConfirm()
            7 -> options()
        }
        command = getNum(menu())
    }
}

private fun menu(): String {
    return ("\n1 - Binary Search\n" +
            "2 - Jump Search\n" +
            "3 - Get Average Search steps for Binary versus Jump for the Array\n" +
            "4 - Create a new random Array\n" +
            "5 - export an Array of Numbers\n" +
            "6 - import an Array of numbers\n" +
            "7 - Options\n" +
            "8 - Exit")
}

private fun search(searchObject: Search) {
    val searches = getNum("How many searches would you like to do?").absoluteValue

    if (PRINT_ARRAY) println(NUMBERS.toList())

    repeat(searches) {
        val find = getNum("What number would you like to find the Index of?")
        val found = searchObject.find(find, PRINT_SEARCH)

        println(if (found == -1) "The number is not in the array." else "Found $find at index number: $found")
    }

    searchObject.average()
}

private fun average() {
    val jumpSearch = JumpSearch(NUMBERS)
    val binarySearch = BinarySearch(NUMBERS)

    NUMBERS.forEach {
        jumpSearch.find(it, false)
        binarySearch.find(it, false)
    }

    binarySearch.average()
    jumpSearch.average()
}

private fun newArray() {
    NUMBERS = getArray()
    BINARY_SEARCH = BinarySearch(NUMBERS)
    JUMP_SEARCH = JumpSearch(NUMBERS)
}

private fun export() {
    try {
        val file = File(getFileName())
        var numbers = ""

        for (num in NUMBERS) numbers += "$num "
        file.writeText(numbers.trim())
        println("The array of numbers have been saved to the file ${file.name}")
    } catch (e: IOException) {
        println(
            "Access was denied in writing to the file. Please ensure it is not set to read only, or open in " +
                    "another program.\n"
        )
    } catch (e: Exception) {
        println("There was an error in writing your file, please try again.\n")
    }
}

private fun import(fileName: String? = null): Array<Int> {
    if (!IMPORTED) IMPORTED = true
    try {
        val file = File(fileName ?: getFileName())

        return (file.readText().split(" ").map { it.toInt() }).toTypedArray()
    } catch (e: FileNotFoundException) {
        println("File not found.\n")
    } catch (e: Exception) {
        println(
            "There was an error in loading your file. Please ensure it is not open in another program and it is " +
                    "formatted correctly.\n"
        )
    }
    IMPORTED = false
    return if (NUMBERS.isNotEmpty()) {
        NUMBERS
    } else {
        println("Import failed, loading a random array of size 100.")
        getArray(100)
    }
}

private fun importConfirm(fileName: String = getFileName()) {
    NUMBERS = import(fileName)
    if (IMPORTED) println("Loaded $fileName")
}

private fun options() {
    var command = getNum(optionMenu())

    while (command != 6) {
        when (command) {
            1 -> PRINT_SEARCH = !PRINT_SEARCH
            2 -> PRINT_ARRAY = !PRINT_ARRAY
            3 -> jumpLinChange()
            4 -> getMin()
            5 -> getMax()
        }
        command = getNum(optionMenu())
    }
}

private fun getArray(size: Int? = null): Array<Int> {
    val numbers = mutableListOf<Int>()
    val arraySize = size ?: getNum("Enter Array size:")
    val range = MIN..MAX

    if (arraySize > ((MAX - MIN + 1) / 4) || arraySize < 5) {
        println("Size is too " + if (arraySize < 5) "small" else "large.")
        println("Please enter an array no smaller than 5 and no larger than ${(MAX - MIN + 1) / 4}.")
        return getArray()
    }

    repeat(arraySize) {
        var add = range.random()
        while (numbers.contains(add)) add = range.random()
        numbers.add(add)
    }
    numbers.sort()
    return numbers.toTypedArray()
}

private fun optionMenu(): String {
    val strYesNo = { check: Boolean -> if (check) "YES" else "NO" }

    return ("\n1. Print Search Steps: ${strYesNo(PRINT_SEARCH)}\n" +
            "2. Print the numbers in the array before doing a Search: ${strYesNo(PRINT_ARRAY)}\n" +
            "3. Jump Search with: Linear " + (if (LIN_FORWARD) "Forward\n" else "Backwards\n") +
            "4. Change the minimum range for Random Number\n" +
            "       Current minimum: $MIN\n" +
            "5. Change the max range for Random Number\n" +
            "       Current max: $MAX\n" +
            "6. Return to Main Menu")
}

private fun jumpLinChange() {
    LIN_FORWARD = !LIN_FORWARD
    JUMP_SEARCH.linDirection(LIN_FORWARD)
}

private fun getMin() {
    val minValue = if (MAX < 0 ) Int.MIN_VALUE + 40 else Int.MIN_VALUE + MAX + 40
    var newMin = getNum("Enter new minimum Number:")
    val strTryAgain = "Number can be no larger than ${MAX - 40}, and no smaller than $minValue. Please try again:"

    while (newMin > MAX - 40 || newMin < minValue) newMin = getNum(strTryAgain)
    MIN = newMin
}

private fun getMax() {
    val maxValue = if (MIN < 0) Int.MAX_VALUE - MIN.absoluteValue - 40 else Int.MAX_VALUE - 40
    var newMax = getNum("Enter new max number:")
    val strTryAgain = "Number can be no smaller than ${MIN + 40}, and no larger than $maxValue. Please try again:"

    while (newMax < MIN + 40 || newMax > maxValue) newMax = getNum(strTryAgain)
    MAX = newMax
}

private fun getFileName() = getString("File name:")

// this useful random function was found on Stack Overflow
private fun IntRange.random() = Random().nextInt(endInclusive + 1 - start) + start

private fun getNum(text: String, defaultMessage: Boolean = false): Int {
    val strErrorNum = " was not a number, please try again: "
    var num = text
    var default = defaultMessage

    do {
        num = getString(if (default) num + strErrorNum else num)
        if (!default) default = true
    } while (!isNumber(num))

    return num.toInt()
}

private fun getString(text: String): String {
    println(text)
    return readLine()!!
}

private fun isNumber(number: String) = number.toIntOrNull() != null