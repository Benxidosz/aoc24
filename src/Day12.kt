import kotlin.math.log10
import kotlin.math.pow

fun main() {
    fun part1(input: List<String>) {
        val map = input.map { it.toList() }
        val directions = listOf(IPos(1, 0), IPos(-1, 0), IPos(0, 1), IPos(0, -1))
        var solution = 0
        val closed = mutableSetOf<IPos>()
        map.forEachIndexed { rowI, row ->
            row.forEachIndexed { colI, field ->
                val startingPos = IPos(rowI, colI)
                if (startingPos !in closed) {
                    val open = mutableListOf(startingPos)
                    var size = 0
                    var perimeter = 0
                    while (open.isNotEmpty()) {
                        val currentPos = open.removeFirst()
                        ++size
                        closed.add(currentPos)
                        directions.forEach { dir ->
                            val neiPos = currentPos.add(dir)
                            val isOnMap = map.isValidIndex(neiPos)
                            if (!isOnMap || map.get(neiPos) != map.get(startingPos)) {
                                ++perimeter
                            } else {
                                if (neiPos !in closed && neiPos !in open) {
                                    open.add(neiPos)
                                }
                            }
                        }
                    }
                    solution += size * perimeter
                }
            }
        }
        solution.println()
    }

    fun part2(input: List<String>){
        val map = input.map { it.toList() }
        val directions = listOf(
            IPos(1, 0), IPos(-1, 0), IPos(0, 1), IPos(0, -1)
        )
        val checkDirections = listOf(
            IPos(1, 1), IPos(-1, 1), IPos(1, -1), IPos(-1, -1)
        )
        val cornerDirections = listOf(
            Pair(listOf(IPos(-1, -1), IPos(-1, 0), IPos(0, -1)), listOf()),
            Pair(listOf(IPos(-1, 1), IPos(-1, 0), IPos(0, 1)), listOf()),
            Pair(listOf(IPos(1, 1), IPos(1, 0), IPos(0, 1)), listOf()),
            Pair(listOf(IPos(1, -1), IPos(0, -1), IPos(1, 0)), listOf()),

            Pair(listOf(IPos(-1, -1)), listOf(IPos(-1, 0), IPos(0, -1))),
            Pair(listOf(IPos(-1, 1)), listOf(IPos(-1, 0), IPos(0, 1))),
            Pair(listOf(IPos(1, 1)), listOf(IPos(1, 0), IPos(0, 1))),
            Pair(listOf(IPos(1, -1)), listOf(IPos(0, -1), IPos(1, 0))),

            Pair(listOf(IPos(-1, 0), IPos(0, -1)), listOf(IPos(-1, -1))),
            Pair(listOf(IPos(-1, 0), IPos(0, 1)), listOf(IPos(-1, 1))),
            Pair(listOf(IPos(1, 0), IPos(0, 1)), listOf(IPos(1, 1))),
            Pair(listOf(IPos(0, -1), IPos(1, 0)), listOf(IPos(1, -1))),
        )
        var solution = 0
        val closed = mutableSetOf<IPos>()
        map.forEachIndexed { rowI, row ->
            row.forEachIndexed { colI, field ->
                val startingPos = IPos(rowI, colI)
                val sideMap = mutableMapOf<IPos, MutableList<IPos>>()
                if (startingPos !in closed) {
                    val open = mutableListOf(startingPos)
                    var size = 0
                    while (open.isNotEmpty()) {
                        val currentPos = open.removeFirst()
                        ++size
                        closed.add(currentPos)
                        directions.forEach { dir ->
                            val neiPos = currentPos.add(dir)
                            val isOnMap = map.isValidIndex(neiPos)
                            if (!isOnMap || map.get(neiPos) != map.get(startingPos)) {
                                sideMap.getOrPut(currentPos) {mutableListOf()}.add(dir)
                            } else {
                                if (neiPos !in closed && neiPos !in open) {
                                    open.add(neiPos)
                                }
                            }
                        }
                        checkDirections.forEach { dir ->
                            val neiPos = currentPos.add(dir)
                            val isOnMap = map.isValidIndex(neiPos)
                            if (!isOnMap || map.get(neiPos) != map.get(startingPos)) {
                                sideMap.getOrPut(currentPos) {mutableListOf()}.add(dir)
                            }
                        }
                    }
                    var cornerCount = 0
                    sideMap.forEach { (pos, diffs) ->
                        var tmpCornerCount = 0
                        cornerDirections.forEach { cornerDirection ->
                            if (diffs.containsAll(cornerDirection.first) && !diffs.containsAny(cornerDirection.second)) {
                                ++tmpCornerCount
                            }
                        }
//                        if (tmpCornerCount > 0) {
//                            println("Corner: $pos count: $tmpCornerCount")
//                        }
                        cornerCount += tmpCornerCount
                    }
//                    println("region ${map.get(startingPos)}: $size & $cornerCount")
                    solution += size * cornerCount
                }
            }
        }
        solution.println()
    }

    val test = readInput("inputt")
    val input = readInput("input12")
    part1(input)
    part2(input)
}