fun main() {
    class Guard(var pos: IPos) : Cloneable {
        override fun toString(): String {
            return "Guard(position=$pos)"
        }
        fun simulate(map: List<List<Char>>): List<List<Char>> {
            val outputMap = MutableList(map.size) { map[it].toMutableList() }

            var direction = IPos(-1, 0)

            while (outputMap.isValidIndex(pos)) {
                var searchPos = pos
                while (outputMap.isValidIndex(searchPos) && outputMap.get(searchPos) != '#') {
                    outputMap.set(searchPos, 'X')
                    searchPos = searchPos.add(direction)
                }
                if (outputMap.isValidIndex(searchPos)) {
                    pos = searchPos.minus(direction)
                } else {
                    pos = searchPos
                }
                direction = direction.rotate90CW()
            }

            return outputMap
        }

        fun detectCycleWithChange(map: List<List<Char>>, changePos: IPos): Boolean {
            var result = false
            val copyMap = MutableList(map.size) { map[it].toMutableList() }
            copyMap.set(changePos, '#')

            var direction = IPos(-1, 0)

            val usedObstacle = mutableListOf<Pair<IPos, IPos>>()
            while (copyMap.isValidIndex(pos)) {
                var searchPos = pos
                while (copyMap.isValidIndex(searchPos) && copyMap.get(searchPos) != '#') {
                    copyMap.set(searchPos, 'X')
                    searchPos = searchPos.add(direction)
                }
                if (copyMap.isValidIndex(searchPos)) {
                    pos = searchPos.minus(direction)
                    if (Pair(searchPos, direction) in usedObstacle) {
                        result = true
                        break
                    } else {
                        usedObstacle.add(Pair(searchPos, direction))
                    }
                } else {
                    pos = searchPos
                }
                direction = direction.rotate90CW()
            }

            return result
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Guard

            return pos == other.pos
        }

        override fun hashCode(): Int {
            return pos.hashCode()
        }

        public override fun clone(): Guard {
            return Guard(pos)
        }
    }

    fun part1(input: List<String>) {
        val map = List(input.size) { input[it].toList() }
        var guard: Guard? = null
        map.forEachIndexed scan@ { rowI, row ->
            val colI = row.indexOf('^')
            if (colI != -1) {
                guard = Guard(IPos(rowI, colI))
                return@scan
            }
        }
        guard?.let {
            val resultMap = it.simulate(map)
            resultMap.forEach { line -> line.println() }
            resultMap.sumOf { line -> line.count { c -> c == 'X' } }.println()
        }
    }

    fun part2(input: List<String>){
        val map = List(input.size) { input[it].toList() }
        var guard: Guard? = null
        map.forEachIndexed scan@ { rowI, row ->
            val colI = row.indexOf('^')
            if (colI != -1) {
                guard = Guard(IPos(rowI, colI))
                return@scan
            }
        }
        var count = 0
        guard?.let {
            map.forEachIndexed { rowI, row ->
                row.forEachIndexed { colI, _ ->
                    val copyGuard = it.clone()
                    if (copyGuard.detectCycleWithChange(map, IPos(rowI, colI))) ++count
                }
            }
        }
        count.println()
    }

    val test = readInput("inputt")
    val input = readInput("input06")
    part1(input)
    part2(input)
}