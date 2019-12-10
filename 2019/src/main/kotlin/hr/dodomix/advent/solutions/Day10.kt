package hr.dodomix.advent.solutions

import java.lang.Math.toDegrees
import kotlin.math.atan2
import kotlin.math.max

class Day10 {

    fun dayDirectory() = "day10"

    fun part1(input: List<String>): Int {
        val map = input.foldIndexed(mutableMapOf<Pair<Int, Int>, Char>()) { row, rowMap, s ->
            s.toCharArray().foldIndexed(rowMap) { column, columnMap, c ->
                columnMap[Pair(row, column)] = c
                columnMap
            }
        }

        val maxRow = map.keys.maxBy { it.first }!!.first
        val maxColumn = map.keys.maxBy { it.second }!!.second
        val directions = getAllDirections(maxRow, maxColumn)

        var maxAsteroids = 0
        for (row in 0..maxRow) {
            for (column in 0..maxColumn) {
                var asteroids = 0
                val field = map.getValue(Pair(row, column))
                if (field == '#') {
                    for (direction in directions) {
                        for (k in 1..max(maxRow, maxColumn)) {
                            val newDirection = Pair(direction.first * k, direction.second * k)
                            val newRow = row + newDirection.first
                            val newColumn = column + newDirection.second
                            if (newRow > maxRow || newColumn > maxColumn) {
                                break
                            }
                            val otherField = map[Pair(newRow, newColumn)]
                            if (otherField == '#') {
                                asteroids++
                                break
                            }
                        }
                    }
                    if (asteroids > maxAsteroids) {
                        maxAsteroids = asteroids
                    }
                }
            }
        }
        return maxAsteroids
    }

    fun part2(input: List<String>): Int {
        val map = input.foldIndexed(mutableMapOf<Pair<Int, Int>, Char>()) { row, rowMap, s ->
            s.toCharArray().foldIndexed(rowMap) { column, columnMap, c ->
                columnMap[Pair(row, column)] = c
                columnMap
            }
        }

        val maxRow = map.keys.maxBy { it.first }!!.first
        val maxColumn = map.keys.maxBy { it.second }!!.second
        val directions = getAllDirections(maxRow, maxColumn)
        directions.sortBy { -((toDegrees(atan2(it.second.toDouble(), it.first.toDouble())) + 90) % 360) }

        val row = 22
        val column = 17
        var asteroids = 0
        while (true) {
            for (direction in directions) {
                for (k in 1..max(maxRow, maxColumn)) {
                    val newDirection = Pair(direction.first * k, direction.second * k)
                    val newRow = row + newDirection.first
                    val newColumn = column + newDirection.second
                    if (newRow > maxRow || newColumn > maxColumn || newRow < 0 || newColumn < 0) {
                        break
                    }
                    val otherField = map[Pair(newRow, newColumn)]
                    if (otherField == '#') {
                        map[Pair(newRow, newColumn)] = '.'
                        if (++asteroids == 200) {
                            return newColumn * 100 + newRow
                        }
                        break
                    }
                }
            }
        }
        return 0
    }

    private fun getAllDirections(maxRow: Int, maxColumn: Int): MutableList<Pair<Int, Int>> {
        val directions = mutableListOf<Pair<Int, Int>>()
        for (row in 0..maxRow) {
            for (column in 0..maxColumn) {
                val direction = Pair(row, column)
                if (row != 0 || column != 0) {
                    if (directions.none {
                            (row == 0 && it.first == 0 && column % it.second == 0) ||
                                (column == 0 && it.second == 0 && row % it.first == 0) ||
                                (row != 0 && column != 0 && it.first != 0 && it.second != 0 && row % it.first == 0 && column % it.second == 0 &&
                                    row / it.first == column / it.second)
                        }) {
                        directions.add(direction)
                        directions.add(Pair(-direction.first, -direction.second))
                        if (direction.first != 0 && direction.second != 0) {
                            directions.add(Pair(-direction.first, direction.second))
                            directions.add(Pair(direction.first, -direction.second))
                        }
                    }
                }
            }
        }
        return directions
    }
}
