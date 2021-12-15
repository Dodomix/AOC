package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Location

class Day15 {
    fun dayDirectory() = "day15"

    private val fullLocationRisk = mutableMapOf<Location, Int>()

    fun part1(input: List<String>): Int {
        val cave = parseInput(input)
        return buildFullRiskMap(cave).getValue(Location(cave.maxOf { it.key.row }, cave.maxOf { it.key.column }))
    }

    fun part2(input: List<String>): Int {
        val originalCave = parseInput(input)
        val rows = originalCave.maxOf { it.key.row } + 1
        val columns = originalCave.maxOf { it.key.column } + 1
        val cave = mutableMapOf<Location, Int>()
        (0 until 5).forEach { i ->
            (0 until 5).forEach { j ->
                (0 until rows).forEach { row ->
                    (0 until columns).forEach { column ->
                        var newValue = originalCave.getValue(Location(row, column)) + i + j
                        if (newValue > 9) {
                            newValue %= 9
                        }
                        cave[Location(i * rows + row, j * columns + column)] = newValue
                    }
                }
            }
        }

        return buildFullRiskMap(cave).getValue(Location(cave.maxOf { it.key.row }, cave.maxOf { it.key.column }))
    }

    private fun parseInput(input: List<String>): Map<Location, Int> = input.flatMapIndexed { row, line ->
        line.mapIndexed { column, c -> Pair(Location(row, column), "$c".toInt()) }
    }.toMap()

    private fun buildFullRiskMap(cave: Map<Location, Int>): Map<Location, Int> {
        val initialLocation = Location(0, 0)
        val locationsToVisit = mutableMapOf(Pair(initialLocation, 0))
        val fullLocationRisk = mutableMapOf<Location, Int>()
        while (locationsToVisit.isNotEmpty()) {
            val nextLocationData = locationsToVisit.minByOrNull { it.value }!!
            fullLocationRisk[nextLocationData.key] = nextLocationData.value
            locationsToVisit.remove(nextLocationData.key)
            val (row, column) = nextLocationData.key
            locationsToVisit.putAll(listOf(
                Location(row - 1, column),
                Location(row, column - 1),
                Location(row + 1, column),
                Location(row, column + 1)
            ).filter { cave.containsKey(it) && !fullLocationRisk.containsKey(it) }.map {
                Pair(it, nextLocationData.value + cave.getValue(it))
            }.filter { (location, newValue) ->
                !locationsToVisit.containsKey(location) || newValue < locationsToVisit.getValue(location)
            })
        }
        return fullLocationRisk
    }
}
