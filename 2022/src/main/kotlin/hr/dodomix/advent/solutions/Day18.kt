package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Position3D
import hr.dodomix.advent.util.Util.Position3D.Companion.fromString


class Day18 {
    fun dayDirectory() = "day18"

    fun part1(input: List<String>): Int {
        val cubes = input.map { line -> fromString(line) }.toSet()
        return cubes.sumOf { position ->
            val (x, y, z) = position
            listOf(
                Position3D(x - 1, y, z), Position3D(x + 1, y, z),
                Position3D(x, y - 1, z), Position3D(x, y + 1, z),
                Position3D(x, y, z - 1), Position3D(x, y, z + 1)
            ).filterNot { cubes.contains(it) }.size
        }
    }

    fun part2(input: List<String>): Int {
        val cubes = input.map { line -> fromString(line) }.toHashSet()
        return getTouchingWater(cubes)
    }

    private fun getTouchingWater(cubes: Set<Position3D>): Int {
        val seenWaterPositions = mutableSetOf<Position3D>()
        val waterPositionsToVisit = mutableListOf(Position3D(-1, -1, -1))
        val maxWaterPosition = Position3D(cubes.maxOf { it.x } + 1, cubes.maxOf { it.y } + 1, cubes.maxOf { it.z } + 1)
        var sum = 0
        while (waterPositionsToVisit.isNotEmpty()) {
            val waterPosition = waterPositionsToVisit.first()
            waterPositionsToVisit.removeAt(0)
            if (waterPosition in seenWaterPositions) {
                continue
            }
            seenWaterPositions.add(waterPosition)
            val (x, y, z) = waterPosition
            val neighbors = listOf(
                Position3D(x - 1, y, z), Position3D(x + 1, y, z),
                Position3D(x, y - 1, z), Position3D(x, y + 1, z),
                Position3D(x, y, z - 1), Position3D(x, y, z + 1)
            )
            sum += neighbors.filter { cubes.contains(it) }.size
            waterPositionsToVisit.addAll(neighbors.filterNot {
                it.x < -1 || it.y < -1 || it.z < -1 ||
                        it.x > maxWaterPosition.x || it.y > maxWaterPosition.y ||
                        it.z > maxWaterPosition.z || it in cubes || it in seenWaterPositions
            })
        }
        return sum
    }
}
