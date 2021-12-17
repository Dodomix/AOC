package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Location
import kotlin.math.abs
import kotlin.math.max

class Day17 {
    fun dayDirectory() = "day17"

    fun part1(input: List<String>): Int {
        val targetArea = parseInput(input)
        // highest y achieved with lowest x which reaches target area
        generateSequence(1) { it + 1 }.forEach { x ->
            if (doesXReachTargetArea(targetArea, x)) {
                return calculateMaxYForX(targetArea, x)
            }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val targetArea = parseInput(input)
        return (1..targetArea.first.second).sumOf { x ->
            if (doesXReachTargetArea(targetArea, x)) {
                calculateYsForX(targetArea, x)
            } else {
                0
            }
        }
    }

    private fun parseInput(input: List<String>) = input[0].drop("target area: ".length).split(", ").map {
        it.drop(2).split("..").map { value -> value.toInt() }.let { values ->
            Pair(values[0], values[1])
        }
    }.let {
        Pair(it[0], it[1])
    }

    private fun doesXReachTargetArea(targetArea: Pair<Pair<Int, Int>, Pair<Int, Int>>, x: Int): Boolean {
        generateSequence(1) { it + 1 }.fold(Pair(x, 0)) { (speedX, currentX), _ ->
            if (isXInTargetArea(targetArea, currentX)) {
                return true
            }
            if (speedX == 0) {
                return false
            }
            Pair(max(speedX - 1, 0), currentX + speedX)
        }
        return false
    }

    private fun calculateMaxYForX(targetArea: Pair<Pair<Int, Int>, Pair<Int, Int>>, x: Int): Int {
        generateSequence(abs(targetArea.second.first)) { it - 1 }.forEach { y ->
            var locationData = LocationData(Location(0, 0), x, y, 0)
            while (true) {
                val (currentLocation, speedX, speedY, maxY) = locationData
                if (isBelowTargetArea(targetArea, currentLocation.row)) {
                    break
                }
                if (isInTargetArea(targetArea, currentLocation.column, currentLocation.row)) {
                    return maxY
                }
                val newLocation = Location(currentLocation.row + speedY, currentLocation.column + speedX)
                locationData = LocationData(newLocation, max(speedX - 1, 0), speedY - 1, max(newLocation.row, maxY))
            }
        }
        return -1
    }

    private fun calculateYsForX(targetArea: Pair<Pair<Int, Int>, Pair<Int, Int>>, x: Int): Int =
        (targetArea.second.first..abs(targetArea.second.first)).count { y ->
            var locationData = LocationData(Location(0, 0), x, y, 0)
            var targetReached = false
            while (true) {
                val (currentLocation, speedX, speedY, maxY) = locationData
                if (isBelowTargetArea(targetArea, currentLocation.row)) {
                    break
                }
                if (isInTargetArea(targetArea, currentLocation.column, currentLocation.row)) {
                    targetReached = true
                }
                val newLocation = Location(currentLocation.row + speedY, currentLocation.column + speedX)
                locationData = LocationData(newLocation, max(speedX - 1, 0), speedY - 1, max(newLocation.row, maxY))
            }
            targetReached
        }

    private fun isInTargetArea(targetArea: Pair<Pair<Int, Int>, Pair<Int, Int>>, x: Int, y: Int) =
        x >= targetArea.first.first && x <= targetArea.first.second && y >= targetArea.second.first && y <= targetArea.second.second


    private fun isXInTargetArea(targetArea: Pair<Pair<Int, Int>, Pair<Int, Int>>, x: Int) =
        x >= targetArea.first.first && x <= targetArea.first.second

    private fun isBelowTargetArea(targetArea: Pair<Pair<Int, Int>, Pair<Int, Int>>, y: Int) =
        y < targetArea.second.first && y < targetArea.second.second

    private data class LocationData(val currentLocation: Location, val xSpeed: Int, val ySpeed: Int, val maxY: Int)
}
