package search

import utility.TrackTime
import utility.numberFormat

abstract class Search(protected val numbers: Array<Int>) {
    private var tries = 0
    private var totalTries = 0L
    private var numOfTries = 0
    protected var find = 0
    protected var returnNum: Int? = null
    protected var printStep = true
    protected val lastIndex = if (numbers.isEmpty()) -1 else numbers.lastIndex
    private val time = TrackTime()
    protected val printTries = { steps: Int ->
        tries++
        if (printStep) println("Step $tries: Checking index $steps, with a value of ${numberFormat(numbers[steps])}.")
    }

    fun find(number: Int, printSteps: Boolean = true): Int {
        if (numbers.isEmpty()) return -1
        reset()
        if (printStep != printSteps) printStep = printSteps
        find = number
        time.start()
        findWork()
        time.stop()

        return returnNum ?: -1
    }

    protected abstract fun findWork()

    private fun reset() {
        if (returnNum != null) returnNum = null
        resetTries()
    }

    private fun resetTries() {
        if (tries != 0) {
            totalTries += tries
            numOfTries++
            tries = 0
        }
    }

    open fun average() {
        resetTries()
        val name = this.javaClass.name.replace("S", " s").removeRange(0..6)
        val average = if (numOfTries == 0) 0 else (totalTries / numOfTries).toInt()
        println(
            "$name average for ${numberFormat(numOfTries)} searches " +
                    "is ${numberFormat(average)} steps. Time taken: ${time.elapsed()}"
        )
    }
}