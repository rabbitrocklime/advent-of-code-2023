import java.io.File

fun main() {
    println(Day07().puzzle1())
    println(Day07().puzzle2())
}

class Day07 {
    private val file = File("inputs/day01.txt")

    private fun readFile(): List<String> {
        return file.readLines();
    }

    fun puzzle1(): Int {
        return 1
    }

    fun puzzle2(): Int {
        return 2
    }
}