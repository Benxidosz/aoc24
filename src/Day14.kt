fun main() {
    fun part1(input: List<String>) {
        val robots = mutableListOf<Pair<IPos, IPos>>()
        val h = 103
        val w = 101
        input.forEach {
            val split = it.split(" ")
            val startPos = split[0].removePrefix("p=").split(",")
            val velocity = split[1].removePrefix("v=").split(",")
            robots.add(Pair(IPos(startPos[0].toInt(), startPos[1].toInt()), IPos(velocity[0].toInt(), velocity[1].toInt())))
        }
        val finishedRobotPositions = mutableListOf<IPos>()
        val seconds = 100
        robots.forEach { robot ->
            val tmpX = (robot.first.first + robot.second.first * seconds) % w
            val tmpY = (robot.first.second + robot.second.second * seconds) % h
            val finishedX = if (tmpX < 0) tmpX + w else tmpX
            val finishedY = if (tmpY < 0) tmpY + h else tmpY
            finishedRobotPositions.add(IPos(finishedX, finishedY))
        }
        finishedRobotPositions.sortBy { it.first * 10 + it.second }
//        val map = List<MutableList<Int>>(7) {MutableList<Int>(11){0} }
        val quadMap = mutableMapOf<IPos, Int>()
        finishedRobotPositions.forEach {
            val x = if (0 <= it.first && it.first < (w-1)/2) 0 else if ((w-1)/2 < it.first && it.first < w) 1 else 2
            val y = if (0 <= it.second && it.second < (h-1)/2) 0 else if ((h-1)/2 < it.second && it.second < h) 1 else 2
            if (x < 2 && y < 2) {
                quadMap[IPos(x, y)] = quadMap.getOrDefault(IPos(x, y), 0) + 1
            }
        }
        var solution = 1
        quadMap.forEach {
            if (it.value > 0) {
                solution *= it.value
            }
        }
        solution.println()
    }

    fun part2(input: List<String>){
        val robots = mutableListOf<Pair<IPos, IPos>>()
        val h = 103
        val w = 101
        input.forEach {
            val split = it.split(" ")
            val startPos = split[0].removePrefix("p=").split(",")
            val velocity = split[1].removePrefix("v=").split(",")
            robots.add(Pair(IPos(startPos[0].toInt(), startPos[1].toInt()), IPos(velocity[0].toInt(), velocity[1].toInt())))
        }
        val map = List(h) { MutableList(w) { '.' } }
//        for (seconds in 1000000..10000000) {
//            val finishedRobotPositions = mutableListOf<IPos>()
//            robots.forEach { robot ->
//                val tmpX = (robot.first.first + robot.second.first * seconds) % w
//                val tmpY = (robot.first.second + robot.second.second * seconds) % h
//                val finishedX = if (tmpX < 0) tmpX + w else tmpX
//                val finishedY = if (tmpY < 0) tmpY + h else tmpY
//                finishedRobotPositions.add(IPos(finishedX, finishedY))
//            }
//            finishedRobotPositions.sortBy { it.first * 10 + it.second }
//            val map = List(h) { MutableList(w) { '.' } }
//            finishedRobotPositions.forEach { robot ->
//                map[robot.second][robot.first] = '#'
//            }
//            map.forEach { it.println() }
//        }
    }

    val test = readInput("inputt")
    val input = readInput("input14")
    part1(input)
    part2(input)
}