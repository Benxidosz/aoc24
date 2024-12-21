fun main() {
    fun part1(input: List<String>) {
        val antennaMap = mutableMapOf<Char, MutableSet<IPos>>()
        val map = MutableList(input.size) {input[it].toMutableList()}
        input.forEachIndexed { rowI, row ->
            row.forEachIndexed { colI, antenna ->
                if (antenna != '.') {
                    antennaMap.getOrPut(antenna) { mutableSetOf() }.add(IPos(rowI, colI))
                }
            }
        }
        val antiNodes = mutableSetOf<IPos>()
        antennaMap.forEach { (_, antennas) ->
            antennas.forEach { currentAntenna ->
                antennas.forEach { otherAntenna ->
                    if (currentAntenna != otherAntenna) {
                        val d = otherAntenna.minus(currentAntenna)
                        val antiPos = otherAntenna.add(d)
                        if (map.isValidIndex(antiPos)) {
                            antiNodes.add(antiPos)
                            if (map.get(antiPos) == '.') {
                                map.set(antiPos, '#')
                            }
                        }
                    }
                }
            }
        }
        antiNodes.size.println()
    }

    fun part2(input: List<String>){
        val antennaMap = mutableMapOf<Char, MutableSet<IPos>>()
        val map = MutableList(input.size) {input[it].toMutableList()}
        input.forEachIndexed { rowI, row ->
            row.forEachIndexed { colI, antenna ->
                if (antenna != '.') {
                    antennaMap.getOrPut(antenna) { mutableSetOf() }.add(IPos(rowI, colI))
                }
            }
        }
        val antiNodes = mutableSetOf<IPos>()
        antennaMap.forEach { (_, antennas) ->
            antennas.forEach { currentAntenna ->
                antennas.forEach { otherAntenna ->
                    if (currentAntenna != otherAntenna) {
                        val d = otherAntenna.minus(currentAntenna)
                        var antiPos = otherAntenna.add(d)
                        while (map.isValidIndex(antiPos)) {
                            antiNodes.add(antiPos)
                            if (map.get(antiPos) == '.') {
                                map.set(antiPos, '#')
                            }
                            antiPos = antiPos.add(d)
                        }
                    }
                }
            }
        }
        //map.forEach { it.println() }
        antennaMap.forEach { (_, antennas) ->
            antennas.forEach { currentAntenna ->
                if (antennas.size > 1) {
                    antiNodes.add(currentAntenna)
                }
            }
        }
        antiNodes.size.println()
    }

    val test = readInput("inputt")
    val input = readInput("input08")

    val isTest = false
    val inputFile = if (isTest) test else input

    part1(inputFile)
    part2(inputFile)
}