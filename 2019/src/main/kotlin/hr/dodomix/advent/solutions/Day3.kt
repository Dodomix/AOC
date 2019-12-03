package hr.dodomix.advent.solutions

import kotlin.math.abs

class Day3 {
    fun dayDirectory() = "day3"

    fun part1(input: List<String>): Int {
        val locations = mutableSetOf<Pair<Int, Int>>()
        val wire1 = input[0].split(",")
        wire1.fold(Pair(0, 0)) { currentLocation, wirePath ->
            val direction = wirePath[0]
            val length = wirePath.substring(1).toInt()
            var newLocation = currentLocation
            for (i in 0 until length) {
                newLocation = when (direction) {
                    'R' -> Pair(newLocation.first + 1, newLocation.second)
                    'U' -> Pair(newLocation.first, newLocation.second + 1)
                    'D' -> Pair(newLocation.first, newLocation.second - 1)
                    'L' -> Pair(newLocation.first - 1, newLocation.second)
                    else -> {
                        throw RuntimeException("Invalid direction")
                    }
                }
                locations.add(newLocation)
            }
            newLocation
        }
        val crossings = mutableListOf<Int>()
        val wire2 = input[1].split(",")
        wire2.fold(Pair(0, 0)) { currentLocation, wirePath ->
            val direction = wirePath[0]
            val length = wirePath.substring(1).toInt()
            var newLocation = currentLocation
            for (i in 0 until length) {
                newLocation = when (direction) {
                    'R' -> Pair(newLocation.first + 1, newLocation.second)
                    'U' -> Pair(newLocation.first, newLocation.second + 1)
                    'D' -> Pair(newLocation.first, newLocation.second - 1)
                    'L' -> Pair(newLocation.first - 1, newLocation.second)
                    else -> {
                        throw RuntimeException("Invalid direction")
                    }
                }
                if (locations.contains(newLocation)) {
                    crossings.add(abs(newLocation.first) + abs(newLocation.second))
                }
            }
            newLocation
        }
        return crossings.min() ?: 0
    }

    fun part2(input: List<String>): Int {
        val locations = mutableMapOf<Pair<Int, Int>, Int>()
        val wire1 = input[0].split(",")
        wire1.fold(Triple(0, 0, 0)) { currentLocation, wirePath ->
            val direction = wirePath[0]
            val length = wirePath.substring(1).toInt()
            var newLocation = currentLocation
            for (i in 0 until length) {
                newLocation = when (direction) {
                    'R' -> Triple(newLocation.first + 1, newLocation.second, newLocation.third + 1)
                    'U' -> Triple(newLocation.first, newLocation.second + 1, newLocation.third + 1)
                    'D' -> Triple(newLocation.first, newLocation.second - 1, newLocation.third + 1)
                    'L' -> Triple(newLocation.first - 1, newLocation.second, newLocation.third + 1)
                    else -> {
                        throw RuntimeException("Invalid direction")
                    }
                }
                locations.putIfAbsent(Pair(newLocation.first, newLocation.second), newLocation.third)
            }
            newLocation
        }
        val crossings = mutableListOf<Int>()
        val wire2 = input[1].split(",")
        wire2.fold(Triple(0, 0, 0)) { currentLocation, wirePath ->
            val direction = wirePath[0]
            val length = wirePath.substring(1).toInt()
            var newLocation = currentLocation
            for (i in 0 until length) {
                newLocation = when (direction) {
                    'R' -> Triple(newLocation.first + 1, newLocation.second, newLocation.third + 1)
                    'U' -> Triple(newLocation.first, newLocation.second + 1, newLocation.third + 1)
                    'D' -> Triple(newLocation.first, newLocation.second - 1, newLocation.third + 1)
                    'L' -> Triple(newLocation.first - 1, newLocation.second, newLocation.third + 1)
                    else -> {
                        throw RuntimeException("Invalid direction")
                    }
                }
                val locationKey = Pair(newLocation.first, newLocation.second)
                if (locations.containsKey(locationKey)) {
                    crossings.add(newLocation.third + locations.getValue(locationKey))
                }
            }
            newLocation
        }
        return crossings.min() ?: 0
    }
}
