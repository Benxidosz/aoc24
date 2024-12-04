fun main() {
    fun multiply(code: String): Long {
        val params = code.removePrefix("mul(").removeSuffix(")").split(",").map(String::toLong)
        return params[0] * params[1]
    }

    val stateMap = mapOf (
        "m" to mapOf('m' to "m", 'u' to "mu"),
        "mu" to mapOf('m' to "m", 'l' to "mul"),
        "mul" to mapOf('m' to "m", '(' to "mul(X"),
        "mul(X" to mapOf(
            'm' to "m",
            ',' to "mul(X,Y",
            '0' to "mul(X",
            '1' to "mul(X",
            '2' to "mul(X",
            '3' to "mul(X",
            '4' to "mul(X",
            '5' to "mul(X",
            '6' to "mul(X",
            '7' to "mul(X",
            '8' to "mul(X",
            '9' to "mul(X"
        ),
        "mul(X,Y" to mapOf(
            'm' to "m",
            ')' to "mul(X,Y)",
            '0' to "mul(X,Y",
            '1' to "mul(X,Y",
            '2' to "mul(X,Y",
            '3' to "mul(X,Y",
            '4' to "mul(X,Y",
            '5' to "mul(X,Y",
            '6' to "mul(X,Y",
            '7' to "mul(X,Y",
            '8' to "mul(X,Y",
            '9' to "mul(X,Y"
        ),
        "d" to mapOf('d' to "d", 'o' to "do"),
        "do" to mapOf('d' to "d", '(' to "do(", 'n' to "don"),
        "do(" to mapOf('d' to "d", ')' to "do()"),
        "don" to mapOf('d' to "d", '\'' to "don'"),
        "don'" to mapOf('d' to "d", 't' to "don't"),
        "don't" to mapOf('d' to "d", '(' to "don't("),
        "don't(" to mapOf('d' to "d", ')' to "don't()"),
    )

    fun part1(input: List<String>) {
        val validCodeState = listOf("mul(X,Y)")
        val codes = mutableListOf<String>()
        var currentCode = ""
        var currentState = ""
        input.forEach { line ->
            line.forEach {
                if (currentState in stateMap.keys) {
                    val valueMap = stateMap.getOrDefault(currentState, mapOf())
                    if (it in valueMap.keys) {
                        currentState = valueMap[it]!!
                        if (currentState == "mul(X" || currentState == "mul(X,Y") {
                            currentCode += it
                        } else if (currentState == "mul") {
                            currentCode = "mul"
                        } else if (currentState in validCodeState) {
                            currentCode += ')'
                            codes.add(currentCode)
                            currentState = ""
                            currentCode = ""
                        }
                    } else if (it != ' ') {
                        currentState = ""
                        currentCode = ""
                    }
                } else if (it == 'm') {
                    currentState = "m"
                    currentCode = "m"
                } else if (it != ' ') {
                    currentState = ""
                    currentCode = ""
                }
            }
        }
        var sum: Long = 0
        codes.forEach {
            sum += multiply(it)
        }
        sum.println()
    }

    fun part2(input: List<String>){
        val validCodeState = listOf("mul(X,Y)", "do()", "don't()")
        val codes = mutableListOf<String>()
        var currentCode = ""
        var currentState = ""
        input.forEach { line ->
            line.forEach {
                if (currentState in stateMap.keys) {
                    val valueMap = stateMap.getOrDefault(currentState, mapOf())
                    if (it in valueMap.keys) {
                        currentState = valueMap[it]!!
                        if (currentState == "mul(X" || currentState == "mul(X,Y") {
                            currentCode += it
                        } else if (currentState == "mul") {
                            currentCode = "mul"
                        } else if (currentState in validCodeState) {
                            if (currentState == "do()" || currentState == "don't()") {
                                currentCode = currentState
                            } else {
                                currentCode += ')'
                            }
                            codes.add(currentCode)
                            currentState = ""
                            currentCode = ""
                        }
                    } else if (it != ' ') {
                        currentState = ""
                        currentCode = ""
                    }
                } else if (it == 'm') {
                    currentState = "m"
                    currentCode = "m"
                } else if (it == 'd') {
                    currentState = "d"
                } else if (it != ' ') {
                    currentState = ""
                    currentCode = ""
                }
            }
        }
        codes.println()
        var sum: Long = 0
        var enabled = true
        codes.forEach {
            when (it) {
                "do()" -> {
                    enabled = true
                }
                "don't()" -> {
                    enabled = false
                }
                else -> {
                    if (enabled) {
                        sum += multiply(it)
                    }
                }
            }
        }
        sum.println()
    }

    val test = readInput("inputt")
    val input = readInput("input03")
    part1(test)
    part2(input)
}
