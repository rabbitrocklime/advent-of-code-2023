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


    /*private fun countCopies(scratchBooks: List<Pair>, initial : Int = 0) :Int {
        var copies = 0;

        for (i in 0..<scratchBooks.size) {
            val calculateHits = calculateHits(scratchBooks[i])

            for (j in initial+i+1..initial+calculateHits) {
                copies+= countCopies(scratchBooks, initial + 1 + j)
            }
        }

        return copies
    }*/

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
        val cards = file.readLines()
            .map {
                it.substringAfter(": ")
                    .split(" | ").map {
                        it.windowed(3, 3, true).map { it.trim().toInt() }
                    }
            }

        val copies = IntArray(cards.size) { 1 }
        cards.forEachIndexed { idx, (winners, mine) ->
            val count = mine.intersect(winners).count()
            (idx + 1..idx + count).forEachIndexed { _, i ->
                if (i < copies.size) copies[i] += copies[idx]
            }
        }
        return copies.sum()
    }
}