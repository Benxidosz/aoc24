fun main() {
    fun part1(input: List<String>) {
        val line = input[0]
        val idList = mutableListOf<Int>()
        var isFile = true
        var currentId = 0
        line.forEach {
            val num = it.digitToInt()
            for (i in 0 until num) {
                if (isFile) {
                    idList.add(currentId)
                } else {
                    idList.add(-1)
                }
            }
            if (isFile) ++currentId
            isFile = !isFile
        }
        var begin = 0
        var end = idList.size - 1
        while (begin <= end) {
            while (idList[begin] != -1) ++begin
            while (idList[end] == -1) --end

            if (begin >= end) break

            idList[begin] = idList[end]
            idList[end] = -1
        }
        var checksum = 0.toLong()
        idList.forEachIndexed { i, id ->
            if (id != -1) {
                checksum += i * id
            }
        }
        checksum.println()
    }

    fun part2(input: List<String>){
        val line = input[0]
        val idList = mutableListOf<Pair<Int, Int>>()
        var isFile = true
        var currentId = 0
        line.forEach {
            if (isFile) {
                idList.add(Pair(currentId, it.digitToInt()))
            } else {
                idList.add(Pair(-1, it.digitToInt()))
            }
            if (isFile) ++currentId
            isFile = !isFile
        }
        var end = idList.size - 1
        val triedIds = mutableSetOf<Int>()
        while (end > 0) {
            while ((end >= 0 && (end >= idList.size || idList[end].first == -1)) || triedIds.contains(idList[end].first)) --end

            if (end <= 0) break

            triedIds.add(idList[end].first)

            var begin = 0
            var success = false
            while (begin in 0 until end) {
                while (begin in idList.indices && idList[begin].first != -1) ++begin

                if (begin !in 0 until end) break

                val beginSpan = idList[begin]
                val endSpan = idList[end]

                if (beginSpan.second >= endSpan.second) {
                    idList[end] = Pair(-1, endSpan.second)
                    if (beginSpan.second > endSpan.second) {
                        idList[begin] = Pair(beginSpan.first, beginSpan.second - endSpan.second)
                    } else {
                        idList.remove(beginSpan)
                    }
                    idList.add(begin, endSpan)
                    success = true
                    break
                } else {
                    ++begin
                }
            }
            if (!success) {
                --end
            } else {
                var removed = 0
                idList.windowed(2).forEachIndexed() { i, pair ->
                    if (pair[0].first == pair[1].first) {
                        idList[i - removed + 1] = Pair(pair[0].first, idList[i - removed].second + idList[i - removed + 1].second)
                        idList.removeAt(i - removed)
                        ++removed
                    }
                }
            }
        }

        var checksum = 0.toLong()
        var pos = 0
        idList.forEach {
            for (i in 0 until it.second) {
                if (it.first != -1) {
                    checksum += pos * it.first
                }
                ++pos
            }
        }
        checksum.println()
    }

    val test = readInput("inputt")
    val input = readInput("input09")
    part1(input)
    part2(input)
}