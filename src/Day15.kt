import kotlin.math.absoluteValue

fun main() {
    open class Box(var pos: IPos) {
        override fun hashCode(): Int {
            return pos.hashCode()
        }

        override fun toString(): String {
            return "B$pos"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Box

            return pos == other.pos
        }

        open fun move(dir: IPos, map: List<List<Char>>, boxes: Set<Box>): Boolean {
            val newPos = pos.add(dir)
            val isWall = map.get(newPos) == '#'
            if (isWall) return false

            val box = boxes.find { it.pos == newPos }
            if (box != null && !box.move(dir, map, boxes)) return false

            pos = newPos
            return true
        }

        open fun canMove(dir: IPos, map: List<List<Char>>, boxes: Set<Box>): Boolean {
            val newPos = pos.add(dir)
            val isWall = map.get(newPos) == '#'
            if (isWall) return false

            val box = boxes.find { it.pos == newPos }
            return !(box != null && !box.canMove(dir, map, boxes))
        }

        open fun printIntoMap(map: List<MutableList<Char>>) {
            map.set(pos, 'O')
        }

        open fun getGps() = pos.first * 100 + pos.second
    }

    class WideBox(pos: IPos, var pair: Box): Box(pos) {
        constructor(pos: IPos): this(pos, Box(0 to 0)) {

        }

        override fun toString(): String {
            return "WB$pos"
        }

        override fun printIntoMap(map: List<MutableList<Char>>) {
            if (pos.second < pair.pos.second) {
                map.set(pos, '[')
            } else {
                map.set(pos, ']')
            }
        }

        fun simpleMove(dir: IPos, map: List<List<Char>>, boxes: Set<Box>) = super.move(dir, map, boxes)
        fun simpleCanMove(dir: IPos, map: List<List<Char>>, boxes: Set<Box>) = super.canMove(dir, map, boxes)

        override fun canMove(dir: IPos, map: List<List<Char>>, boxes: Set<Box>): Boolean {
            (pair as WideBox).let {
                return super.canMove(dir, map, boxes) && it.simpleCanMove(dir, map, boxes)
            }
        }

        override fun move(dir: IPos, map: List<List<Char>>, boxes: Set<Box>): Boolean {
            (pair as WideBox).let { castedPair ->
                val originalDir = castedPair.pos.minus(pos)
                if (pos.add(dir) == castedPair.pos) {
                    val newPos = castedPair.moveByPair(dir, map, boxes).minus(dir)
                    if (pos == newPos) return false
                    pos = newPos
                    return true
                }

                if (!castedPair.canMove(dir, map, boxes)) return false
                if (!simpleMove(dir, map, boxes)) return false

                return castedPair.simpleMove(dir, map, boxes)
            }
        }

        fun moveByPair(dir: IPos, map: List<List<Char>>, boxes: Set<Box>): IPos {
            super.move(dir, map, boxes)
            return pos
        }

        override fun getGps(): Int = if (pos.second < pair.pos.second) {
            super.getGps()
        } else {
            0
        }
    }

    class Robot(pos: IPos): Box(pos) {
        override fun toString(): String {
            return "R$pos"
        }

        override fun printIntoMap(map: List<MutableList<Char>>) {
            map.set(pos, '@')
        }
    }
    fun part1(input: List<String>) {
        val mapInput = input.take(input.indexOf(""))
        val robot = Robot(IPos(0, 0))
        val boxes = mutableSetOf<Box>()
        val map = List(mapInput.size) {
            mapInput[it].foldIndexed(mutableListOf<Char>()) { colI, acc, c ->
                when (c) {
                    '@' -> {
                        robot.pos = it to colI
                        acc.add('.')
                    }
                    'O' -> {
                        boxes.add(Box(it to colI))
                        acc.add('.')
                    }
                    else -> acc.add(c)
                }

                acc
            }
        }

        input.takeLast(input.size - 1 - input.indexOf("")).map { it.toList() }.flatten().forEach { c ->
            val dir = when(c) {
                '<' -> 0 to -1
                'v' -> 1 to 0
                '>' -> 0 to 1
                '^' -> -1 to 0
                else -> 0 to 0
            }
            robot.move(dir, map, boxes)
            val testMap = map.map { it.toMutableList() }
            boxes.forEach {
                it.printIntoMap(testMap)
            }
            robot.printIntoMap(testMap)
            println("---------------")
            println("$c $dir")
            testMap.forEach { it.println() }
            println("---------------")
        }

        val testMap = map.map { it.toMutableList() }
        boxes.forEach {
            it.printIntoMap(testMap)
        }
        testMap.forEach { it.println() }

        boxes.fold(0) {sum, box -> sum + box.getGps()}.println()
    }

    fun part2(input: List<String>){
        val mapInput = input.take(input.indexOf(""))
        val robot = Robot(IPos(0, 0))
        val boxes = mutableSetOf<WideBox>()
        val wideMap = List(mapInput.size) {
            mapInput[it].mapIndexed { colI, c ->
                when (c) {
                    '@' -> {
                        listOf('@', '.')
                    }
                    'O' -> {
                        listOf('O', '.')
                    }
                    else -> listOf(c, c)
                }
            }.flatten()
        }

        val map = List(wideMap.size) {
            wideMap[it].mapIndexed { colI, c ->
                when (c) {
                    '@' -> {
                        robot.pos = it to colI
                        '.'
                    }
                    'O' -> {
                        val current = WideBox(it to colI, WideBox(it to colI + 1))
                        val pair = WideBox(it to colI + 1, current)
                        current.pair = pair
                        boxes.add(current)
                        boxes.add(pair)
                        '.'
                    }
                    else -> c
                }
            }
        }

        input.takeLast(input.size - 1 - input.indexOf("")).map { it.toList() }.flatten().forEach { c ->
            val dir = when(c) {
                '<' -> 0 to -1
                'v' -> 1 to 0
                '>' -> 0 to 1
                '^' -> -1 to 0
                else -> 0 to 0
            }
            val beforeMap = map.map { it.toMutableList() }
            boxes.forEach {
                it.printIntoMap(beforeMap)
            }
            robot.printIntoMap(beforeMap)
            robot.move(dir, map, boxes)
            var invalidBox = false
            boxes.forEach {
                if (it.pos.minus(it.pair.pos).length().absoluteValue != 1) {
                    invalidBox = true
                    it.pos.println()
                }
            }
            if (invalidBox) {
                val testMap = map.map { it.toMutableList() }
                boxes.forEach {
                    it.printIntoMap(testMap)
                }
                robot.printIntoMap(testMap)
                println("---------------")
                println("$c $dir")
                beforeMap.forEach { it.println() }
                testMap.forEach { it.println() }
                println("---------------")
                return
            }
        }

        val testMap = map.map { it.toMutableList() }
        robot.printIntoMap(testMap)
        boxes.forEach {
            it.printIntoMap(testMap)
        }
        testMap.forEach { it.println() }

        boxes.fold(0) {sum, box -> sum + box.getGps()}.println()
    }

    val test = readInput("inputt")
    val input = readInput("input15")
//    part1(input)
    part2(input)
}