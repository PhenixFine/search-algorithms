package search

class LinearSearch(numbers: Array<Int>) : Search(numbers) {
    override fun findWork() {
        for (num in numbers.indices) {
            printTries(num)
            if (numbers[num] == find) {
                returnNum = num
                break
            }
        }
    }
}