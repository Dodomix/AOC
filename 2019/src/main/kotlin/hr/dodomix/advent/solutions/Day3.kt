package hr.dodomix.advent.solutions

import kotlin.math.abs

class Day3 {
    fun dayDirectory() = "day3"

    fun part1(input: List<String>): Int {
        val locations = mutableSetOf<Pair<Int, Int>>()
        val wire1 = input[0].split(",")
        wire1.fold(Triple(0, 0, 0)) { currentLocation, wirePath ->
            val direction = wirePath[0]
            val length = wirePath.substring(1).toInt()
            (0 until length).fold(currentLocation) { oldLocation, _ ->
                val location = calculateNewLocation(oldLocation, direction)
                locations.add(Pair(location.first, location.second))
                location
            }
        }
        val crossings = mutableListOf<Int>()
        val wire2 = input[1].split(",")
        wire2.fold(Triple(0, 0, 0)) { currentLocation, wirePath ->
            val direction = wirePath[0]
            val length = wirePath.substring(1).toInt()
            (0 until length).fold(currentLocation) { oldLocation, _ ->
                val location = calculateNewLocation(oldLocation, direction)
                if (locations.contains(Pair(location.first, location.second))) {
                    crossings.add(abs(location.first) + abs(location.second))
                }
                location
            }
        }
        return crossings.min() ?: 0
    }

    fun part2(input: List<String>): Int {
        val locations = mutableMapOf<Pair<Int, Int>, Int>()
        val wire1 = input[0].split(",")
        wire1.fold(Triple(0, 0, 0)) { currentLocation, wirePath ->
            val direction = wirePath[0]
            val length = wirePath.substring(1).toInt()
            (0 until length).fold(currentLocation) { oldLocation, _ ->
                val location = calculateNewLocation(oldLocation, direction)
                locations.putIfAbsent(Pair(location.first, location.second), location.third)
                location
            }
        }
        val crossings = mutableListOf<Int>()
        val wire2 = input[1].split(",")
        wire2.fold(Triple(0, 0, 0)) { currentLocation, wirePath ->
            val direction = wirePath[0]
            val length = wirePath.substring(1).toInt()
            (0 until length).fold(currentLocation) { oldLocation, _ ->
                val location = calculateNewLocation(oldLocation, direction)
                val locationKey = Pair(location.first, location.second)
                if (locations.containsKey(locationKey)) {
                    crossings.add(location.third + locations.getValue(locationKey))
                }
                location
            }
        }
        return crossings.min() ?: 0
    }

    private fun calculateNewLocation(location: Triple<Int, Int, Int>, direction: Char): Triple<Int, Int, Int> = when (direction) {
        'R' -> Triple(location.first + 1, location.second, location.third + 1)
        'U' -> Triple(location.first, location.second + 1, location.third + 1)
        'D' -> Triple(location.first, location.second - 1, location.third + 1)
        'L' -> Triple(location.first - 1, location.second, location.third + 1)
        else -> {
            throw RuntimeException("Invalid direction")
        }
    }
}
