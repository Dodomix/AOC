package hr.dodomix.advent.solutions

import kotlin.math.abs

class Day12 {
    fun dayDirectory() = "day12"

    fun part1(input: List<String>): Int {
        val directions = listOf('E', 'S', 'W', 'N')
        return input.fold(Pair(Pair(0, 0), 'E')) { (location, currentDirection), instructionDistance ->
            val instruction = instructionDistance[0]
            val distance = instructionDistance.substring(1).toInt()
            when (instruction) {
                'N' -> Pair(Pair(location.first - distance, location.second), currentDirection)
                'S' -> Pair(Pair(location.first + distance, location.second), currentDirection)
                'E' -> Pair(Pair(location.first, location.second + distance), currentDirection)
                'W' -> Pair(Pair(location.first, location.second - distance), currentDirection)
                'L' -> Pair(
                    Pair(location.first, location.second),
                    directions[
                        (directions.indexOf(currentDirection) - distance / 90 + directions.size) % directions.size
                    ]
                )
                'R' -> Pair(
                    Pair(location.first, location.second),
                    directions[
                        (directions.indexOf(currentDirection) + distance / 90) % directions.size
                    ]
                )
                'F' -> {
                    when (currentDirection) {
                        'N' -> Pair(Pair(location.first - distance, location.second), currentDirection)
                        'S' -> Pair(Pair(location.first + distance, location.second), currentDirection)
                        'E' -> Pair(Pair(location.first, location.second + distance), currentDirection)
                        'W' -> Pair(Pair(location.first, location.second - distance), currentDirection)
                        else -> throw RuntimeException("Invalid direction")
                    }
                }
                else -> throw RuntimeException("Invalid instruction")
            }
        }.first.let { (x, y) -> abs(x) + abs(y) }
    }

    fun part2(input: List<String>): Int {
        return input.fold(Pair(Pair(0, 0), Pair(-1, 10))) { (shipLocation, waypointLocation), instructionDistance ->
            val instruction = instructionDistance[0]
            val distance = instructionDistance.substring(1).toInt()
            when (instruction) {
                'N' -> Pair(shipLocation, Pair(waypointLocation.first - distance, waypointLocation.second))
                'S' -> Pair(shipLocation, Pair(waypointLocation.first + distance, waypointLocation.second))
                'E' -> Pair(shipLocation, Pair(waypointLocation.first, waypointLocation.second + distance))
                'W' -> Pair(shipLocation, Pair(waypointLocation.first, waypointLocation.second - distance))
                'L' -> when (distance) {
                    90 -> Pair(shipLocation, Pair(-waypointLocation.second, waypointLocation.first))
                    180 -> Pair(shipLocation, Pair(-waypointLocation.first, -waypointLocation.second))
                    270 -> Pair(shipLocation, Pair(waypointLocation.second, -waypointLocation.first))
                    else -> throw RuntimeException("Invalid rotation")
                }
                'R' -> when (distance) {
                    90 -> Pair(shipLocation, Pair(waypointLocation.second, -waypointLocation.first))
                    180 -> Pair(shipLocation, Pair(-waypointLocation.first, -waypointLocation.second))
                    270 -> Pair(shipLocation, Pair(-waypointLocation.second, waypointLocation.first))
                    else -> throw RuntimeException("Invalid rotation")
                }
                'F' -> {
                    Pair(
                        Pair(
                            shipLocation.first + distance * waypointLocation.first,
                            shipLocation.second + distance * waypointLocation.second
                        ), waypointLocation
                    )
                }
                else -> throw RuntimeException("Invalid instruction")
            }
        }.first.let { (x, y) -> abs(x) + abs(y) }
    }
}
