fun main() {
    fun part1(input: List<String>) {
        var sum = 0.toULong()
        input.forEach { test ->
            val parsed = test.split(':')
            val solution = parsed[0].toULong()
            val operations = parsed[1].trim().split(' ').map { it.toInt() }
            var results = mutableListOf(operations[0].toULong())

            var success = false
            for (i in 1 until operations.size) {
                val prevResults = results.toList()
                results = mutableListOf()
                val currentOperation = operations[i]
                prevResults.forEach {
                    val tmpSum = it + currentOperation.toULong()
                    val tmpMul = it * currentOperation.toULong()
                    if (tmpSum <= solution.toULong()) {
                        results.add(tmpSum)
                    }
                    if (tmpMul <= solution.toULong()) {
                        results.add(tmpMul)
                    }
                }
            }
            if (results.find { it == solution } != null) {
                sum += solution
            }
        }
        sum.println()
    }

    fun part2(input: List<String>){
        var sum = 0.toULong()
        input.forEach { test ->
            val parsed = test.split(':')
            val solution = parsed[0].toULong()
            val operations = parsed[1].trim().split(' ').map { it.toInt() }
            var results = mutableListOf(operations[0].toULong())

            var success = false
            for (i in 1 until operations.size) {
                val prevResults = results.toList()
                results = mutableListOf()
                val currentOperation = operations[i]
                prevResults.forEach {
                    val tmpSum = it + currentOperation.toULong()
                    val tmpMul = it * currentOperation.toULong()
                    val tmpCon = it.con(currentOperation.toULong())
                    if (tmpSum <= solution.toULong()) {
                        results.add(tmpSum)
                    }
                    if (tmpMul <= solution.toULong()) {
                        results.add(tmpMul)
                    }
                    if (tmpCon <= solution.toULong()) {
                        results.add(tmpCon)
                    }
                }
            }
            if (results.find { it == solution } != null) {
                sum += solution
            }
        }
        sum.println()
    }

    val test = readInput("inputt")
    val input = readInput("input07")

    val isTest = false
    val inputFile = if (isTest) test else input

    part1(inputFile)
    part2(inputFile)
}