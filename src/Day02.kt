import kotlin.math.abs

fun main() {
    fun part1(input: List<String>) {
        val tests = input.map {it.split(" ").map(String::toInt)}
        var count = 0
        tests.forEach { test ->
            val operation = test[1].compareTo(test[0])
            if (operation != 0) {
                var safe = true
                for (i in 1..< test.size) {
                    if (abs(test[i] - test[i - 1]) > 3) {
                        safe = false
                    }
                    if (test[i].compareTo(test[i - 1]) != operation) {
                        safe = false
                    }
                    if (!safe) break
                }
                if (safe) ++count
            }
        }
        println(count)
    }

    fun part2(input: List<String>){
        val tests = input.map {it.split(" ").map(String::toInt)}
        var count = 0
        tests.forEach { test ->
            var operation = test[1].compareTo(test[0])
            if (operation != 0) {
                var safe = true
                var dumperActive = true
                var i = 1
                while (i < test.size) {
                    if (abs(test[i] - test[i - 1]) > 3) {
                        safe = false
                    }
                    if (test[i].compareTo(test[i - 1]) != operation) {
                        safe = false
                    }
                    if (!safe) {
                        if (dumperActive && i != test.size - 1) {
                            safe = true
                            dumperActive = false
                            if (abs(test[i + 1] - test[i - 1]) > 3) {
                                safe = false
                            }
                            if (test[i + 1].compareTo(test[i - 1]) != operation) {
                                safe = false
                            }
                            ++i
                        } else if (dumperActive) {
                            safe = true
                        } else {
                            break
                        }
                    }
                    ++i
                }
                if (!safe) {
                    safe = true
                    operation = test[2].compareTo(test[1])
                    for (i in 2..<test.size) {
                        if (abs(test[i] - test[i - 1]) > 3) {
                            safe = false
                        }
                        if (test[i].compareTo(test[i - 1]) != operation) {
                            safe = false
                        }
                        if (!safe) break
                    }
                }
                if (safe) ++count else test.println()
            }
        }
        println(count)
    }

    val input = readInput("input02")
    part1(input)
    part2(input)
}
