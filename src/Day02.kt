import java.io.File

fun main() {
    println(Day02().puzzle1())
    println(Day02().puzzle2())
}

class Day02 {
    private val file = File("inputs/day02.txt")

    data class Game(val id: Int, val rgbs: List<IntArray>)

    private fun readFile(): List<String> {
        return file.readLines();
    }

    private fun setUpGame(line: String): Game {
        val id = line.drop(5).takeWhile { it != ':' }.toInt() // id
        val rgbs = line.substringAfter(": ").split("; ").map { cubes ->
            var r = 0
            var g = 0
            var b = 0

            cubes.split(", ").forEach { cube ->
                val num = cube.substringBefore(" ").toInt()
                if (cube.endsWith("blue")) b += num
                else if (cube.endsWith("green")) g += num
                else if (cube.endsWith("red")) r += num
            }

            arrayOf(r, g, b).toIntArray()

        }
        return Game(id, rgbs)
    }

    fun puzzle1(): Int {
        return this.readFile()
            .map(this::setUpGame)
            .filter { it.rgbs.all { rgb -> rgb[0] <= 12 && rgb[1] <= 13 && rgb[2] <= 14 } }
            .sumOf { it.id }
    }


    fun puzzle2(): Int {
        return this.readFile()
            .map(this::setUpGame)
            .sumOf { game -> game.rgbs.maxOf { it[0] } * game.rgbs.maxOf { it[1] } * game.rgbs.maxOf { it[2] } }
    }
}