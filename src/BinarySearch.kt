class BinarySearch(numbers: Array<Int>) : Search(numbers) {
    private var left = 0
    private var right = lastIndex
    private val middle = { left + (right - left) / 2 }

    override fun findWork() {
        reset()
        while (returnNum == null) {
            search()
        }
    }

    override fun reset() {
        super.reset()
        if (right != lastIndex) right = lastIndex
        if (left != 0) left = 0
    }

    private fun search() {
        if (right >= left) {
            val mid = middle()
            printTries(mid)
            when {
                numbers[mid] == find -> {
                    returnNum = mid
                    return
                }
                numbers[mid] > find -> {
                    right = mid - 1
                    return
                }
                else -> {
                    left = mid + 1
                    return
                }
            }
        }
        returnNum = -1
    }
}