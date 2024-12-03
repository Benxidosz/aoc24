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
    )
)

fun main() {
    fun part1(input: List<String>) {
        val line = input[0]
        val codes = mutableListOf<String>()
        var currentCode = ""
        var currentState = ""
        line.forEach {
            if (currentState in stateMap.keys) {
                val valueMap = stateMap.getOrDefault(currentState, mapOf())
                if (it in valueMap.keys) {
                    currentState = valueMap[it]!!
                    if (currentState == "mul(X" || currentState == "mul(X,Y") {
                        currentCode += it
                    } else if (currentState == "mul") {
                        currentCode = "mul"
                    } else if (currentState == "mul(X,Y)") {
                        currentCode += ')'
                        codes.add(currentCode)
                        currentState = ""
                        currentCode = ""
                    }
                }
            } else if (it == 'm') {
                currentState = "m"
                currentCode = "m"
            }
        }
        var sum: Long = 0
        codes.println()
        codes.forEach {
            sum += multiply(it)
        }
        sum.println()
    }

    fun part2(input: List<String>){

    }

    val test = readInput("inputt")
    val input = readInput("input03")
    part1(input)
    part2(test)
}
