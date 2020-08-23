package old

private var TRIES = 0

fun main() {
    val numbers = arrayOf(10, 13, 19, 20, 24, 26, 30, 34, 35)
    val find = 26
    val found: Int

    println(numbers.asList())
    println("find number: $find")
    found = binarySearchRecursive(numbers, 0, numbers.lastIndex, find)
    println(if (found == -1) "The number is not in the array" else "Found the number at index number: $found")
}

fun binarySearchRecursive(numbers: Array<Int>, left: Int, right: Int, find: Int): Int {
    if (right >= left) {
        val mid = left + (right - left) / 2
        TRIES++
        println("Step $TRIES: Checking middle index $mid, with a value of ${numbers[mid]}.")
        if (numbers[mid] == find) return mid
        if (numbers[mid] > find) return binarySearchRecursive(numbers, left, mid - 1, find)
        return binarySearchRecursive(numbers, mid + 1, right, find)
    }
    return -1
}