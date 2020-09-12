/** When adding a new algorithm update:
 *      main(), menu(), average(), and updateNumbers().
 *  If there are any new options for it update:
 *      options() and optionMenu() */

import search.*
import utility.*
import java.io.*
import kotlin.math.absoluteValue

private var MIN = 0
private var MAX = 400_000
private const val PATH = "data/"
private var NUMBERS = import("numbers50K.dat")
private var JUMP_SEARCH = JumpSearch(NUMBERS)
private var BINARY_SEARCH = BinarySearch(NUMBERS)
private var PRINT_SEARCH = true
private var PRINT_ARRAY = false
private var LIN_FORWARD = true
private var DO_LIN_SEARCH = false

fun main() {
    var command = getNum(menu())

    while (command != 8) {
        when (command) {
            1 -> search(BINARY_SEARCH)
            2 -> search(JUMP_SEARCH)
            3 -> average()
            4 -> updateNumbers(getArray())
            5 -> export()
            6 -> updateNumbers(import(getFileName()))
            7 -> options()
        }
        command = getNum(menu())
    }
}

private fun menu(): String {
    val strLinear = { if (DO_LIN_SEARCH) ", Jump, and Linear" else " and Jump" }

    return ("\n1 - Binary Search\n" +
            "2 - Jump Search\n" +
            "3 - Get Average steps for Binary" + strLinear() + " searches of the Array\n" +
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

        println(
            if (found == -1) "The number is not in the array." else "Found ${numberFormat(find)} at index number: " +
                    numberFormat(found)
        )
    }

    searchObject.average()
}

private fun average() {
    val jumpSearch = JumpSearch(NUMBERS)
    val binarySearch = BinarySearch(NUMBERS)
    val linearSearch = LinearSearch(NUMBERS)

    println("Finding Averages...")
    NUMBERS.forEach {
        jumpSearch.find(it, false)
        binarySearch.find(it, false)
        if (DO_LIN_SEARCH) linearSearch.find(it, false)
    }

    binarySearch.average()
    jumpSearch.average()
    if (DO_LIN_SEARCH) linearSearch.average()
}

private fun updateNumbers(newArray: Array<Int>) {
    NUMBERS = newArray
    JUMP_SEARCH = JumpSearch(NUMBERS)
    BINARY_SEARCH = BinarySearch(NUMBERS)
}

private fun export() {
    try {
        val file = File(PATH + getFileName())
        val time = TrackTime()

        println("Exporting Array to File ${file.name}...")
        time.start()
        ObjectOutputStream(FileOutputStream(file)).use { it.writeObject(NUMBERS) }
        time.stop()
        println("The array of numbers have been saved to the file ${file.name}. Time taken: ${time.elapsed()}")
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
    try {
        val file = File(PATH + (fileName ?: getFileName()))
        val numbers: Array<Int>
        val time = TrackTime()

        println("Importing File ${file.name} to an array...")
        time.start()
        ObjectInputStream(FileInputStream(file)).use { numbers = it.readObject() as? Array<Int> ?: throw Exception() }
        time.stop()
        println("The file ${file.name} has been imported. Time taken: ${time.elapsed()}")

        return numbers
    } catch (e: FileNotFoundException) {
        println("File not found.\n")
    } catch (e: Exception) {
        println(
            "There was an error in loading your file. Please ensure it is not open in another program and it is " +
                    "formatted correctly.\n"
        )
    }
    return if (!NUMBERS.isNullOrEmpty()) {
        NUMBERS
    } else {
        println("Import failed, loading a random array of size 100.")
        getArray(100)
    }
}

private fun options() {
    var command = getNum(optionMenu())

    while (command != 7) {
        when (command) {
            1 -> PRINT_SEARCH = !PRINT_SEARCH
            2 -> PRINT_ARRAY = !PRINT_ARRAY
            3 -> jumpLinChange()
            4 -> DO_LIN_SEARCH = !DO_LIN_SEARCH
            5 -> getMin()
            6 -> getMax()
        }
        command = getNum(optionMenu())
    }
}

private fun getArray(size: Int? = null): Array<Int> {
    val numbers = mutableListOf<Int>()
    val arraySize = size ?: getNum("Enter Array size:")
    val rangeSize = MAX - MIN + 1
    val increment = if (arraySize <= 0) 0 else rangeSize / arraySize
    var min = MIN
    var max = min + increment - 1
    val range = { min..max }
    val time = TrackTime()

    if (arraySize > rangeSize || arraySize < 5) {
        println("Size is too " + if (arraySize < 5) "small" else "large.")
        println("Please enter an array no smaller than 5 and no larger than ${numberFormat(rangeSize)}.")
        return getArray()
    }
    println("Creating Array...")
    time.start()
    repeat(arraySize) {
        numbers.add(range().random())
        min = max + 1
        max += increment
    }
    time.stop()
    println("Array of size ${numberFormat(arraySize)} was created. Time taken: ${time.elapsed()}")
    return numbers.toTypedArray()
}

private fun optionMenu(): String {
    val strYesNo = { check: Boolean -> if (check) "YES" else "NO" }
    val strAddRemove = { if (DO_LIN_SEARCH) "Remove" else "Add" }
    val strFromTo = { if (DO_LIN_SEARCH) "from" else "to" }

    return ("\n1. Print Search Steps: ${strYesNo(PRINT_SEARCH)}\n" +
            "2. Print the numbers in the array before doing a Search: ${strYesNo(PRINT_ARRAY)}\n" +
            "3. Jump Search with: Linear " + (if (LIN_FORWARD) "Forward\n" else "Backwards\n") +
            "4. " + strAddRemove() + " Linear Search " + strFromTo() + " Average Steps in Main Menu\n" +
            "5. Change the minimum range for Random Number\n" +
            "       Current minimum: ${numberFormat(MIN)}\n" +
            "6. Change the max range for Random Number\n" +
            "       Current max: ${numberFormat(MAX)}\n" +
            "7. Return to Main Menu")
}

private fun jumpLinChange() {
    LIN_FORWARD = !LIN_FORWARD
    JUMP_SEARCH.linDirection(LIN_FORWARD)
}

private fun getMin() {
    val minValue = if (MAX < 0) Int.MIN_VALUE + 40 else Int.MIN_VALUE + MAX + 40
    var newMin = getNum("Enter new minimum Number:")
    val strTryAgain = "Number can be no larger than ${numberFormat(MAX - 40)}, and no smaller " +
            "than ${numberFormat(minValue)}. Please try again:"

    while (newMin > MAX - 40 || newMin < minValue) newMin = getNum(strTryAgain)
    MIN = newMin
}

private fun getMax() {
    val maxValue = if (MIN < 0) Int.MAX_VALUE - MIN.absoluteValue - 40 else Int.MAX_VALUE - 40
    var newMax = getNum("Enter new max number:")
    val strTryAgain = "Number can be no smaller than ${numberFormat(MIN + 40)}, " +
            "and no larger than ${numberFormat(maxValue)}. Please try again:"

    while (newMax < MIN + 40 || newMax > maxValue) newMax = getNum(strTryAgain)
    MAX = newMax
}