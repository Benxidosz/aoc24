fun main() {
    fun countForX(pos: IPos, matrix: List<List<Char>>): Int {
        var count = 0
        val directions = listOf(IPos(1,0), IPos(-1,0), IPos(0,1), IPos(0,-1), IPos(1,1), IPos(-1,-1), IPos(-1,1), IPos(1,-1))
        directions.forEach {
            val tmpEnd = pos.add(it.mul(3))
            if (tmpEnd.first in matrix.indices && tmpEnd.second in matrix[tmpEnd.first].indices) {
                var word = ""
                for (i in 0..3) {
                    val currPos = pos.add(it.mul(i))
                    word += matrix[currPos.first][currPos.second]
                }
                if (word == "XMAS") ++count
            }
        }
        return count
    }

    fun countForA(pos: IPos, matrix: List<List<Char>>): Boolean {
        val directions = listOf(Pair(IPos(-1, -1), IPos(1, 1)), Pair(IPos(-1, 1), IPos(1, -1)))
        var allMatch = true
        directions.forEach {
            val tmpEnd1 = pos.add(it.first)
            val tmpEnd2 = pos.add(it.second)
            if (tmpEnd1.first in matrix.indices && tmpEnd1.second in matrix[tmpEnd1.first].indices &&
                tmpEnd2.first in matrix.indices && tmpEnd2.second in matrix[tmpEnd2.first].indices) {
                var word = ""
                word += matrix[tmpEnd1.first][tmpEnd1.second]
                word += matrix[tmpEnd2.first][tmpEnd2.second]
                if (allMatch) {
                    allMatch = word == "SM" || word == "MS"
                }
            } else {
                allMatch = false
            }
        }
        return allMatch
    }

    fun part1(input: List<String>) {
        val matrix = List(input.size) {
            input[it].toList()
        }
        var count = 0
        matrix.forEachIndexed { i, row ->
            row.forEachIndexed { j, ch ->
                if (ch == 'X') {
                    count += countForX(IPos(i, j), matrix)
                }
            }
        }
        count.println()
    }

    fun part2(input: List<String>){
        val matrix = List(input.size) {
            input[it].toList()
        }
        var count = 0
        matrix.forEachIndexed { i, row ->
            row.forEachIndexed { j, ch ->
                if (ch == 'A' && countForA(IPos(i, j), matrix)) {
                    ++count
                }
            }
        }
        count.println()
    }

    val test = readInput("inputt")
    val input = readInput("input04")
    part1(input)
    part2(input)
}