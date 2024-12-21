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
        var stones = mutableMapOf<ULong, ULong>()
        input[0].split(" ").forEach { c ->
            stones[c.toULong()] = stones.getOrPut(c.toULong()){0.toULong()} + 1.toULong()
        }
        val blinkNum = 75
        for (i in 0 until blinkNum) {
            val tmpStones = mutableMapOf<ULong, ULong>()
            stones.forEach { pair ->
                val stone = pair.key
                if (stone == 0.toULong()) {
                    tmpStones[1.toULong()] = tmpStones.getOrPut(1.toULong()) { 0.toULong() } + pair.value
                } else if (stone.isEvenDigit()) {
                    val halfDigitNum = stone.getDigitNum() / 2.toULong()
                    val left = stone / (10.0.pow(halfDigitNum.toDouble()).toULong())
                    val right = stone % (10.0.pow(halfDigitNum.toDouble()).toULong())
                    tmpStones[left] = tmpStones.getOrPut(left) { 0.toULong() } + pair.value
                    tmpStones[right] = tmpStones.getOrPut(right) { 0.toULong() } + pair.value
                } else {
                    tmpStones[stone * 2024.toULong()] =
                        tmpStones.getOrPut(stone * 2024.toULong()) { 0.toULong()} + pair.value
                }
            }
            stones = tmpStones
        }
        var result = 0.toULong()
        stones.forEach { (_, count) ->
            result += count
        }
        result.println()
    }

    val test = readInput("inputt")
    val input = readInput("input11")
    part1(input)
    part2(input)
}