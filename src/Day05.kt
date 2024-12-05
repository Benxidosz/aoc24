fun main() {
    fun part1(input: List<String>) {
        // Y -> All X prerequisite
        val ruleMap = mutableMapOf<Int, MutableList<Int>>()
        val separateIdx = input.indexOf("")
        val rules = input.take(separateIdx)
        val updates = input.takeLast(input.size - separateIdx - 1)
        rules.forEach {
            val parsed = it.split('|').map(String::toInt)
            ruleMap.getOrPut(parsed[1]) { mutableListOf() }.add(parsed[0])
        }
        val middles = mutableListOf<Int>()
        var sum = 0
        updates.forEach { update ->
            val parsedUpdate = update.split(',').map(String::toInt)
            val processedPages = mutableListOf<Int>()
            var valid = true
            for (page in parsedUpdate) {
                val prerequisites = ruleMap.getOrDefault(page, mutableListOf())
                for (prerequisite in prerequisites) {
                    if (prerequisite in parsedUpdate && prerequisite !in processedPages) {
                        valid = false
                        break
                    }
                }
                if (!valid) break

                processedPages.add(page)
            }
            if (valid) {
                val middle = parsedUpdate[parsedUpdate.size / 2]
                middles.add(middle)
                sum += middle
            }
        }
        sum.println()
    }

    fun part2(input: List<String>){
        val ruleMap = mutableMapOf<Int, MutableList<Int>>()
        val separateIdx = input.indexOf("")
        val rules = input.take(separateIdx)
        val updates = input.takeLast(input.size - separateIdx - 1)
        rules.forEach {
            val parsed = it.split('|').map(String::toInt)
            ruleMap.getOrPut(parsed[1]) { mutableListOf() }.add(parsed[0])
        }
        val middles = mutableListOf<Int>()
        var sum = 0
        updates.forEach { update ->
            val parsedUpdate = update.split(',').map(String::toInt)
            val processedPages = mutableListOf<Int>()
            var valid = true
            val trueRequisiteMap = mutableMapOf<Int,  MutableList<Int>>()
            val neighbourMap = mutableMapOf<Int,  MutableList<Int>>()
            for (page in parsedUpdate) {
                val prerequisites = ruleMap.getOrDefault(page, mutableListOf())
                val truePrerequisites = trueRequisiteMap.getOrPut(page) { mutableListOf() }
                for (prerequisite in prerequisites) {
                    neighbourMap.getOrPut(prerequisite) { mutableListOf() }.add(page)
                    if (prerequisite in parsedUpdate) {
                        truePrerequisites.add(prerequisite)
                        if (prerequisite !in processedPages) {
                            valid = false
                        }
                    }
                }

                processedPages.add(page)
            }
            if (!valid) {
                val startPoints = parsedUpdate.filter { page -> trueRequisiteMap
                    .getOrDefault(page, mutableListOf()).isEmpty() }.toMutableList()
                val sortedList = mutableListOf<Int>()
                while (startPoints.isNotEmpty()) {
                    val currentPage = startPoints.removeAt(0)
                    sortedList.add(currentPage)
                    val neighbours = neighbourMap.getOrDefault(currentPage, mutableListOf())
                    neighbours.forEach {
                        val trueRequisites = trueRequisiteMap.getOrDefault(it, mutableListOf())
                        trueRequisites.remove(currentPage)
                        if (trueRequisites.isEmpty()) {
                            startPoints.add(it)
                        }
                    }
                }
                val middle = sortedList[sortedList.size / 2]
                middles.add(middle)
                sum += middle
            }
        }
        sum.println()
    }

    val test = readInput("inputt")
    val input = readInput("input05")
    part1(input)
    part2(input)
}