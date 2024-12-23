import com.sun.jdi.Value
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

typealias IPos = Pair<Int, Int>

fun IPos.add(other: IPos) = IPos(first + other.first, second + other.second)
fun IPos.minus(other: IPos) = IPos(first - other.first, second - other.second)
fun IPos.mul(other: Int) = IPos(first * other, second * other)
fun IPos.rotate90CW() = IPos(second, first * -1)
fun IPos.rotate90CCW() = IPos(second * -1, first)
fun IPos.length() = first + second

fun <T> List<T>.containsAny(other: List<T>): Boolean {
    other.forEach {
        if (contains(it)) {
            return true
        }
    }
    return false
}

fun <T> List<List<T>>.isValidIndex(pos: IPos): Boolean {
    return pos.first in indices && pos.second in get(pos.first).indices
}

fun <T> List<List<T>>.get(pos: IPos) = get(pos.first)[pos.second]

fun <T> List<MutableList<T>>.set(pos: IPos, value: T) {
    get(pos.first)[pos.second] = value
}

fun ULong.con(other: ULong) = (toString() + other.toString()).toULong()

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