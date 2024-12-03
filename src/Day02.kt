import kotlin.math.abs

fun testForSafety(test: List<Int>): Boolean {
    val operation = test[1].compareTo(test[0])
    if (operation != 0) {
        var safe = true
        for (i in 1 until test.size) {
            if (abs(test[i] - test[i - 1]) > 3) {
                safe = false
            }
            if (test[i].compareTo(test[i - 1]) != operation) {
                safe = false
            }
            if (!safe) break
        }
        return safe
    }
    return false
}

fun main() {
    fun part1(input: List<String>) {
        val tests = input.map {it.split(" ").map(String::toInt)}
        var count = 0
        tests.forEach { test ->
            if (testForSafety(test)) ++count
        }
        println(count)
    }

    fun part2(input: List<String>){
        val tests = input.map {it.split(" ").map(String::toInt)}
        var count = 0
        tests.forEach { test ->
            if (testForSafety(test))
                ++count
            else {
                for (i in test.indices) {
                    val tempTest = test.toMutableList()
                    tempTest.removeAt(i)
                    if (testForSafety(tempTest)) {
                        ++count
                        break
                    }
                }
            }
        }
        println(count)
    }

    val input = readInput("input02")
    part1(input)
    part2(input)
}
