package search

class JumpSearch(numbers: Array<Int>) : Search(numbers) {
    private var currentRight = 0
    private var prevRight = 0
    private var jump = 0
    private var linForward = true

    override fun findWork() {
        reset()
        for (i in 1..3) {
            when (i) {
                1 -> findBlock(lastIndex)
                2 -> checkBlock()
                3 -> if (linForward) linForward() else linBackwards()
            }
            if (returnNum != null) break
        }
    }

    private fun reset() {
        if (currentRight != 0) currentRight = 0
        if (prevRight != 0) prevRight = 0
        jump = Math.sqrt((lastIndex + 1).toDouble()).toInt()
    }

    fun linDirection(linearForward: Boolean) {
        linForward = linearForward
    }

    private fun findBlock(indexLast: Int) {
        if (printStep) println("The jump length is $jump")
        printTries(currentRight)

        while (numbers[currentRight] < find) {
            prevRight = currentRight
            currentRight = Math.min(currentRight + jump, indexLast)
            printTries(currentRight)
            if (currentRight == indexLast && numbers[indexLast] < find) {
                returnNum = -1
                return
            }
        }

        if (numbers[currentRight] == find) returnNum = currentRight else if (prevRight >= currentRight - 1) {
            returnNum = -1
        }
    }

    private fun checkBlock() {
        val setJump = { jump = Math.sqrt(Math.max((currentRight - 1) - (prevRight), 1).toDouble()).toInt() }
        setJump()

        while (jump >= 2 && returnNum == null) {
            val indexLast = currentRight - 1
            currentRight = Math.min(prevRight + jump, lastIndex)
            findBlock(indexLast)
            setJump()
        }
    }

    private fun linForward() {
        if (printStep) println("Searching with linear forward: from previous index searched to last index searched")

        while (numbers[prevRight] < find) {
            prevRight++
            if (prevRight < currentRight) printTries(prevRight) else {
                returnNum = -1
                return
            }
        }
        returnNum = if (numbers[prevRight] == find) prevRight else -1
    }

    private fun linBackwards() {
        if (printStep) println("Searching with linear backwards: from last index searched to previous index searched")

        while (numbers[currentRight] > find) {
            currentRight--
            if (currentRight <= prevRight) {
                returnNum = -1
                return
            } else printTries(currentRight)
        }
        returnNum = if (numbers[currentRight] == find) currentRight else -1
    }
}