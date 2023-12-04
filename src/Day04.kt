import java.io.File
import kotlin.math.pow

fun main() {
    println(Day04().puzzle1())
    println(Day04().puzzle2())
}

class Day04 {
    private val file = File("inputs/day04.txt")

    data class Pair(val winningNumbers: List<Int>, val numbers: List<Int>)


    private fun readFile(): List<String> {
        return file.readLines();
    }

    private fun toIntList(number: String): List<Int> {
        return number
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt() }
    }

    private fun calculatePrize(pair: Pair) = 2.0.pow(calculateHits(pair) - 1).toInt()

    private fun calculateHits(pair: Pair) = pair.numbers.count { pair.winningNumbers.contains(it) }


    private fun countCopies(scratchBooks: List<Pair>, initial : Int, finish : Int = scratchBooks.size) :Int {
        var copies = 0;
        for ((index, s) in scratchBooks.withIndex()) {
            val calculateHits = calculateHits(s)
            if (calculateHits == 0) {
                break
            }
            if (calculateHits > 0) {
                copies += countCopies(scratchBooks, index + initial, finish - index)
            }
        }
        return copies
    }

    fun puzzle1(): Int {
        return buildScratchBooks()
            .sumOf { calculatePrize(it) }
    }

    private fun buildScratchBooks() = readFile()
        .map { it.drop(10) }
        .filter { it.isNotBlank() }
        .map {

            val split = it.trim().split(" | ")

            Pair(toIntList(split[0]), toIntList(split[1]))
        }

    fun puzzle2(): Int {
       return  countCopies(buildScratchBooks(), 0)

    }
}