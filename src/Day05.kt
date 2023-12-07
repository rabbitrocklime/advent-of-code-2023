import java.io.File

fun main() {
    Day05().puzzle1()
    Day05().puzzle2()
}

class Day05 {

    data class RatioRange(val source: LongRange, val target: LongRange, val step: Long)

    private val file = File("inputs/day05.txt")
    private val lines = file.readLines().split { it.isBlank() }
    private val seeds = lines[0][0].substringAfter(": ").split(" ").map { it.toLong() }
    private val ratios = lines.drop(1).map {
        it.drop(1).map {
            val (target, source, rangeSize) = it.split(" ").map { it.toLong() }
            RatioRange(source until source+rangeSize, target until target+rangeSize, target-source)
        }
    }

    private fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> = fold(mutableListOf(mutableListOf<T>())) { acc, t ->
        if (predicate(t)) acc.add(mutableListOf())
        else acc.last().add(t)
        acc
    }

    fun Any.printAnswer() = println("Answer: ${this}")

    private fun convertSeedToLocation(seed: Long) = ratios.fold(seed) { numToConvert, ratioRanges ->
        (ratioRanges.firstOrNull { seedToSoil ->
            seedToSoil.source.contains(numToConvert)
        }?.step ?: 0) + numToConvert
    }

    fun puzzle1() {
        seeds.minOf { convertSeedToLocation(it) }.printAnswer()
    }

    // brute force, but still completes quickly enough
    fun puzzle2() {
        seeds.windowed(2, 2).map { (start, length) -> start until start+length }.minOf { seedRange ->
            seedRange.minOf { convertSeedToLocation(it) }
        }.printAnswer()
    }
}

/*
fun main() {
   // println(Day05().puzzle1())
    println(Day05().puzzle2())
}

class Day05 {
    private val file = File("inputs/day05.txt")

    private data class GardenMap(val nextValue: Long, val sourceValue: Long, val range: Long)

    private fun readFile(): List<String> {
        return file.readLines();
    }

    data class RatioRange(val source: LongRange, val target: LongRange, val step: Long)

    private val lines = readFile()
    private val seeds = lines[0][0].substringAfter(": ").split(" ").map { it.toLong() }
    private val ratios = lines.drop(1).map {
        it.drop(1).map {
            val (target, source, rangeSize) = it.split(" ").map { it.toLong() }
            RatioRange(source until source+rangeSize, target until target+rangeSize, target-source)
        }
    }

    private fun prepareData(lines: List<String>): List<MutableList<GardenMap>> {


        val maps: MutableList<MutableList<GardenMap>> = mutableListOf(mutableListOf())
        var innerMap: MutableList<GardenMap> = mutableListOf()

        lines.drop(2).plus(":").forEachIndexed { i, line ->
            if (line.endsWith(":")) {
                maps.add(innerMap)
                innerMap = mutableListOf()
            } else if (line.isNotBlank()) {
                val values = line.split(" ").map { it.toLong() }
                innerMap.add(GardenMap(values[0], values[1], values[2]))
            }
        }

        return maps.drop(2)
    }

    private fun getSeeds(lines: List<String>): List<Long> {
        return lines.take(2)[0].substringAfter(": ").split(" ").map { it.toLong() }
    }

    private fun getSeedsRange(lines: List<String>): List<Long> {
        val pairs = lines
            .take(2)[0]
            .substringAfter(": ")
            .split(" ")
            .map { it.toLong() }
            .zipWithNext()

        val mutableList: MutableList<Long> = mutableListOf()

        pairs.forEach { (init, range) ->
            for (i in 0..range) {
                mutableList.add(init + i)
            }
        }

        return mutableList
    }

    fun puzzle1(): Long {
        val seeds = getSeeds(readFile()).toMutableList()
        val seedsCopy = seeds.toMutableList();
        val maps = prepareData(readFile())

        seeds.forEachIndexed { index, _ ->
            for (group in maps) {
                for ((target, source, range) in group) {
                    if (seedsCopy[index] >= source && seedsCopy[index] < (source + range)) {
                        seedsCopy[index] = target + (seedsCopy[index] - source)
                        break
                    }
                }
            }
        }

        return seedsCopy.min()
    }

    fun aux(mutableList: List<Long>) : Long {
        val seeds = mutableList
        val seedsCopy = seeds.toMutableList();
        val maps = prepareData(readFile())

        seeds.forEachIndexed { index, _ ->
            for (group in maps) {
                for ((target, source, range) in group) {
                    if (seedsCopy[index] >= source && seedsCopy[index] < (source + range)) {
                        seedsCopy[index] = target + (seedsCopy[index] - source)
                        break
                    }
                }
            }
        }

        return seedsCopy.min()
    }

    //272850765
    fun puzzle2(): Long {
        val subList = getSeedsRange(readFile()).subList(0, 5)
        return aux(subList)

    }
}*/
