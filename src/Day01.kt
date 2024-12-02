import kotlin.math.abs

fun main() {
    fun part1(input: List<String>) {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        input.forEach { line ->
            val numbers = line.split("   ")
            list1.add(numbers[0].toInt())
            list2.add(numbers[1].toInt())
        }

        list1.sort()
        list2.sort()

        list1.println()
        list2.println()

        var sum = 0
        list1.forEachIndexed { i, number ->
            sum += abs(list2[i] - number)
        }
        println(sum)
    }

    fun part2(input: List<String>){
        val list1 = mutableListOf<Int>()
        val countMap = mutableMapOf<Int, Int>()

        input.forEach { line ->
            val numbers = line.split("   ")
            list1.add(numbers[0].toInt())
            countMap[numbers[1].toInt()] = countMap.getOrDefault(numbers[1].toInt(), 0) + 1
        }

        list1.sort()

        var sum = 0
        list1.forEach { number ->
            sum += number * countMap.getOrDefault(number, 0)
        }
        println(sum)
    }

    val input = readInput("input01")
    part1(input)
    part2(input)
}
