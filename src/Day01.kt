import java.io.File

fun main() {
    println(Day01().puzzle1())
    println(Day01().puzzle2())
}

class Day01 {
    private val file = File("inputs/day01.txt")

    private fun readFile(): List<String> {
        return file.readLines();
    }

    fun puzzle1(): Int {
        return this.readFile()
            .map { it.filter { it.isDigit() } }
            .sumOf { (it.first() + "" + it.last()).toInt() }
    }

    fun puzzle2(): Int {
        return this.readFile()
            .map { str ->
                str.windowed(5, 1, true) {
                    when {
                        it[0].isDigit() -> it[0].toString()
                        it.startsWith("one") -> "1"
                        it.startsWith("two") -> "2"
                        it.startsWith("three") -> "3"
                        it.startsWith("four") -> "4"
                        it.startsWith("five") -> "5"
                        it.startsWith("six") -> "6"
                        it.startsWith("seven") -> "7"
                        it.startsWith("eight") -> "8"
                        it.startsWith("nine") -> "9"
                        else -> ""
                    }
                }
            }
            .map { it.filter { c -> c.isNotBlank() } }
            .sumOf { (it.first() + it.last()).toInt() }
    }
}