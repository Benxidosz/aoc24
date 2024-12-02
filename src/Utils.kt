import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

typealias IPos = Pair<Int, Int>

fun IPos.add(other: IPos) = IPos(first + other.first, second + other.second)

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("resources", name).readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun Any?.println() {
    println(this)
}

fun gcd(a: Long, b: Long): Long {
    var tmpA = a
    var tmpB = b
    while (tmpB > 0) {
        val tmp = tmpB
        tmpB = tmpA % tmpB
        tmpA = tmp
    }
    return tmpA
}

fun lcm(a: Long, b: Long): Long {
    return a * (b / gcd(a, b))
}