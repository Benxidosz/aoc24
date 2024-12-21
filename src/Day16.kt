fun main() {
    fun getSmallestScore(startPos: Pair<IPos, IPos>, endPos: IPos, map: List<List<Char>>): Int {
        val open = mutableListOf(startPos)
        val distanceMap = mutableMapOf(
            startPos.first to 0
        )
        val dirMap = mutableMapOf(
            startPos.first to startPos.second
        )
        val parentMap = mutableMapOf(
            startPos.first to startPos.first
        )
        val operations = listOf(
            Pair({ pos: IPos -> pos }, 0 ),
            Pair({ pos: IPos -> pos.rotate90CW() }, 1000),
            Pair({ pos: IPos -> pos.rotate90CCW() }, 1000),
            Pair({ pos: IPos -> pos.rotate90CW().rotate90CW() }, 2000),
        )
        while (open.isNotEmpty()) {
            val current = open.removeFirst()
            val currentPos = current.first
            val currentDir = current.second
            val currentDistance = distanceMap.getOrDefault(currentPos, 0)

            operations.forEach { operation ->
                val tmpDir = operation.first(currentDir)
                val tmpCost = operation.second
                val nextPos = currentPos.add(tmpDir)
                val nextDistance = currentDistance + tmpCost + 1
                if (map.isValidIndex(nextPos) && map.get(nextPos) != '#') {
                    if (distanceMap.getOrDefault(nextPos, Int.MAX_VALUE) > nextDistance) {
                        distanceMap[nextPos] = nextDistance
                        parentMap[nextPos] = currentPos
                        dirMap[nextPos] = tmpDir
                        open.removeIf { it.first == nextPos }
                        open.add(Pair(nextPos, tmpDir))
                    }
                }
            }
        }
        return distanceMap.getOrDefault(endPos, 0)
    }
    fun part1(input: List<String>) {
        var startPos = IPos(0, 0)
        var endPos = IPos(0, 0)
        val map = List(input.size) {
            val colS = input[it].indexOf('S')
            val colE = input[it].indexOf('E')
            if (colS != -1) startPos = IPos(it, colS)
            if (colE != -1) endPos = IPos(it, colE)
            input[it].toList()
        }
        getSmallestScore(Pair(startPos, IPos(0, 1)), endPos, map).println()
    }

    fun part2(input: List<String>){
        var startPos = IPos(0, 0)
        var endPos = IPos(0, 0)
        val map = List(input.size) {
            val colS = input[it].indexOf('S')
            val colE = input[it].indexOf('E')
            if (colS != -1) startPos = IPos(it, colS)
            if (colE != -1) endPos = IPos(it, colE)
            input[it].toList()
        }
        val maxCost = getSmallestScore(Pair(startPos, IPos(0, 1)), endPos, map)
        val open = mutableListOf(Pair(Pair(startPos, IPos(0, 1)), 0))
        val operations = listOf(
            Pair({ pos: IPos -> pos }, 0 ),
            Pair({ pos: IPos -> pos.rotate90CW() }, 1000),
            Pair({ pos: IPos -> pos.rotate90CCW() }, 1000),
            Pair({ pos: IPos -> pos.rotate90CW().rotate90CW() }, 2000),
        )
        val closed = mutableSetOf<Pair<IPos, IPos>>()
        val validPositions = mutableSetOf<IPos>()
        while (open.isNotEmpty()) {
            val (currentState, currentCost) = open.removeFirst()
            val (currentPos, currentDir) = currentState
            validPositions.add(currentPos)

            if (currentState.first == endPos) continue

            if (closed.contains(currentState)) continue
            closed.add(currentState)

            operations.forEach operationLoop@ { operation ->
                val tmpDir = operation.first(currentDir)
                val operationCost = operation.second
                val nextPos = currentPos.add(tmpDir)
                val nextCost = currentCost + operationCost + 1
                if (map.get(nextPos) != '#' && (nextPos to tmpDir) !in closed) {
                    if (nextCost + getSmallestScore((nextPos to tmpDir), endPos, map) <= maxCost) {
                        open.add((nextPos to tmpDir) to nextCost)
                    }
                }
            }
        }
        validPositions.size.println()
    }

    val test = readInput("inputt")
    val input = readInput("input16")
    part1(input)
    part2(input)
}