fun main() {
    fun part1(input: List<String>) {
        val map = input.map { it.toList().map(Char::digitToInt) }
        val startPoints = mutableListOf<IPos>()
        input.forEachIndexed {rowI, row ->
            row.forEachIndexed { colI, c ->
                if (c == '0') {
                    startPoints.add(IPos(rowI, colI))
                }
            }
        }
        val directions = listOf(IPos(0, 1), IPos(0, -1), IPos(1, 0), IPos(-1, 0))
        val scores = mutableListOf<Int>()
        startPoints.forEach { start ->
            val closed = mutableSetOf<IPos>()
            val open = mutableListOf(start)
            var currentScore = 0
            while (open.isNotEmpty()) {
                val currentPos = open.removeAt(0)
                if (!closed.contains(currentPos) && map.get(currentPos) == 9) {
                    ++currentScore
                    closed.add(currentPos)
                    continue
                }
                directions.forEach { dir ->
                    val tmpPos = currentPos.add(dir)
                    if (!closed.contains(tmpPos) && map.isValidIndex(tmpPos) && map.get(tmpPos) == map.get(currentPos) + 1) {
                        open.add(tmpPos)
                    }
                }
                closed.add(currentPos)
            }
            scores.add(currentScore)
        }
        scores.println()
        scores.sum().println()
    }

    fun part2(input: List<String>){
        val map = input.map { it.toList().map(Char::digitToInt) }
        val startPoints = mutableListOf<IPos>()
        input.forEachIndexed {rowI, row ->
            row.forEachIndexed { colI, c ->
                if (c == '0') {
                    startPoints.add(IPos(rowI, colI))
                }
            }
        }
        val directions = listOf(IPos(0, 1), IPos(0, -1), IPos(1, 0), IPos(-1, 0))
        val scores = mutableListOf<Int>()
        startPoints.forEach { start ->
            val closed = mutableSetOf<IPos>()
            val open = mutableListOf(start)
            var currentScore = 0
            while (open.isNotEmpty()) {
                val currentPos = open.removeAt(0)
                if (map.get(currentPos) == 9) {
                    ++currentScore
                    closed.add(currentPos)
                    continue
                }
                directions.forEach { dir ->
                    val tmpPos = currentPos.add(dir)
                    if (!closed.contains(tmpPos) && map.isValidIndex(tmpPos) && map.get(tmpPos) == map.get(currentPos) + 1) {
                        open.add(tmpPos)
                    }
                }
                closed.add(currentPos)
            }
            scores.add(currentScore)
        }
        scores.println()
        scores.sum().println()
    }

    val test = readInput("inputt")
    val input = readInput("input10")

    val isTest = false
    val inputFile = if (isTest) test else input

    part1(inputFile)
    part2(inputFile)
}