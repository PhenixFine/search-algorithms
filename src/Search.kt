abstract class Search(protected val numbers: Array<Int>) {
    private var tries = 0
    private var totalTries = 0
    private var numOfTries = 0
    protected var find = 0
    protected var returnNum: Int? = null
    protected var printStep = true
    protected val lastIndex = if (numbers.isEmpty()) -1 else numbers.lastIndex
    protected val printTries = { steps: Int ->
        tries++
        if (printStep) println("Step $tries: Checking index $steps, with a value of ${numbers[steps]}.")
    }

    fun find(number: Int, printSteps: Boolean = true): Int {
        if (numbers.isEmpty()) return -1
        if (printStep != printSteps) printStep = printSteps
        find = number

        findWork()

        return returnNum ?: -1
    }

    protected abstract fun findWork()

    protected open fun reset() {
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
        val average = if (numOfTries == 0) 0 else totalTries / numOfTries
        println("${this.javaClass.name.replace("S", " s")} average for $numOfTries searches is $average steps.")
    }
}