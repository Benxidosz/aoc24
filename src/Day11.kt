import kotlin.math.log10
import kotlin.math.pow

fun main() {
    fun ULong.getDigitNum() = log10(toFloat()).toULong() + 1.toULong()
    fun ULong.isEvenDigit() = (getDigitNum()) % 2.toULong() == 0.toULong()
    fun part1(input: List<String>) {
        var stones = input[0].split(" ").map { it.toULong() }
        val blinkNum = 25
        for (i in 0 until blinkNum) {
            val tmpStones = mutableListOf<ULong>()
            stones.forEach { stone ->
                if (stone == 0.toULong()) {
                    tmpStones.add(1.toULong())
                } else if (stone.isEvenDigit()) {
                    val halfDigitNum = stone.getDigitNum() / 2.toULong()
                    val left = stone / (10.0.pow(halfDigitNum.toDouble()).toULong())
                    val right = stone % (10.0.pow(halfDigitNum.toDouble()).toULong())
                    tmpStones.add(left)
                    tmpStones.add(right)
                } else {
                    tmpStones.add(stone * 2024.toULong())
                }
            }
            stones = tmpStones
        }
        stones.size.println()
    }

    fun part2(input: List<String>){
        var stones = input[0].split(" ").map { it.toULong() }
        val blinkNum = 6
        for (i in 0 until blinkNum) {
            val tmpStones = mutableListOf<ULong>()
            stones.forEach { stone ->
                if (stone == 0.toULong()) {
                    tmpStones.add(1.toULong())
                } else if (stone.isEvenDigit()) {
                    val halfDigitNum = stone.getDigitNum() / 2.toULong()
                    val left = stone / (10.0.pow(halfDigitNum.toDouble()).toULong())
                    val right = stone % (10.0.pow(halfDigitNum.toDouble()).toULong())
                    tmpStones.add(left)
                    tmpStones.add(right)
                } else {
                    tmpStones.add(stone * 2024.toULong())
                }
            }
            stones = tmpStones
            stones.println()
        }
        stones.size.println()
    }

    val test = readInput("inputt")
    val input = readInput("input11")
    part1(input)
    part2(test)
}