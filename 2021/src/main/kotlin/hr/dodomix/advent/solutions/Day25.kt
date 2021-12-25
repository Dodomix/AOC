package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Location

class Day25 {
    fun dayDirectory() = "day25"

    fun part1(input: List<String>): Int {
        val locations = input.foldIndexed(mapOf<Location, Char>()) { row, locations, line ->
            line.foldIndexed(locations) { column, currentLocations, c ->
                currentLocations + Pair(Location(row, column), c)
            }
        }
        val rows = locations.maxOf { it.key.row } + 1
        val columns = locations.maxOf { it.key.column } + 1

        generateSequence(1) { it + 1 }.fold(locations) { currentLocations, step ->
            val newLocations = mutableMapOf<Location, Char>().withDefault { '.' }
            val movingEastCucumbers = currentLocations.filter { it.value == '>' }.map {
                val nextLocation = Location(it.key.row, (it.key.column + 1) % columns)
                if (currentLocations.getValue(nextLocation) == '.') {
                    Pair(nextLocation, it.value)
                } else {
                    Pair(it.key, it.value)
                }
            }
            newLocations += movingEastCucumbers
            val movingSouthCucumbers = currentLocations.filter { it.value == 'v' }.map {
                val nextLocation = Location((it.key.row + 1) % rows, it.key.column)
                if (newLocations.getValue(nextLocation) == '.' && currentLocations.getValue(nextLocation) != 'v') {
                    Pair(nextLocation, it.value)
                } else {
                    Pair(it.key, it.value)
                }
            }
            newLocations += movingSouthCucumbers
            if (newLocations == currentLocations) return step
            newLocations.toMap().withDefault { '.' }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        return -1
    }
}
