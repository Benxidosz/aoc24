fun main() {
    class Guard(var pos: IPos) {
        override fun toString(): String {
            return "Guard(position=$pos)"
        }
        fun simulate(map: List<List<Char>>): List<List<Char>> {
            val outputMap = MutableList(map.size) { map[it].toMutableList() }

            var direction = IPos(-1, 0)
            var done = true

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

    }

    val test = readInput("inputt")
    val input = readInput("input06")
    part1(input)
    part2(test)
}