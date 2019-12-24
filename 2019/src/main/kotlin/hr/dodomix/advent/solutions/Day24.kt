package hr.dodomix.advent.solutions

import kotlin.math.pow

class Day24 {

    fun dayDirectory() = "day24"

    fun part1(input: List<String>): Any {
        val initialGrid = mutableMapOf<Pair<Int, Int>, Char>().withDefault { '.' }
        input.forEachIndexed { row, line ->
            line.forEachIndexed { column, char ->
                initialGrid[Pair(row, column)] = char
            }
        }
        val grids = mutableSetOf(initialGrid)
        var currentGrid = initialGrid
        while (true) {
            currentGrid = currentGrid.mapValues {
                val key = it.key
                val adjacentBugCount = listOf(
                    currentGrid.getValue(Pair(key.first - 1, key.second)),
                    currentGrid.getValue(Pair(key.first + 1, key.second)),
                    currentGrid.getValue(Pair(key.first, key.second - 1)),
                    currentGrid.getValue(Pair(key.first, key.second + 1))
                ).filter { value -> value == '#' }.size
                if (it.value == '#') {
                    if (adjacentBugCount == 1) '#'
                    else '.'
                } else {
                    if (adjacentBugCount == 1 || adjacentBugCount == 2) '#'
                    else '.'
                }
            }.toMutableMap().withDefault { '.' }
            if (grids.contains(currentGrid)) {
                return currentGrid.map {
                    if (it.value == '#') {
                        2.0.pow(it.key.first * 5.0 + it.key.second)
                    } else {
                        0.0
                    }
                }.sum().toLong()
            } else {
                grids.add(currentGrid)
            }
        }
    }

    fun part2(input: List<String>): Any {
        val initialGrid = mutableMapOf<Pair<Int, Int>, Char>()
        input.forEachIndexed { row, line ->
            line.forEachIndexed { column, char ->
                initialGrid[Pair(row, column)] = char
            }
        }
        val emptyGrid = initialGrid.mapValues { '.' }
        var allGrids = (0 until 200).map { emptyGrid } +
                initialGrid +
                (0 until 200).map { emptyGrid }
        for (i in 0 until 200) {
            allGrids = allGrids.mapIndexed { index, currentGrid ->
                val outsideGrid = if (index == 0) emptyGrid
                else allGrids[index - 1]
                val insideGrid = if (index == allGrids.lastIndex) emptyGrid
                else allGrids[index + 1]
                currentGrid.mapValues { entry ->
                    val key = entry.key
                    if (key != Pair(2, 2)) {
                        val adjacentBugCount = listOf(
                            Pair(key.first - 1, key.second),
                            Pair(key.first + 1, key.second),
                            Pair(key.first, key.second - 1),
                            Pair(key.first, key.second + 1)
                        ).flatMap {
                            if (it.first == -1) listOf(outsideGrid.getValue(Pair(1, 2)))
                            else if (it.first == 5) listOf(outsideGrid.getValue(Pair(3, 2)))
                            else if (it.second == -1) listOf(outsideGrid.getValue(Pair(2, 1)))
                            else if (it.second == 5) listOf(outsideGrid.getValue(Pair(2, 3)))
                            else if (it.first == 2 && it.second == 2) {
                                if (key.first == 1 && key.second == 2) insideGrid.filter { entry -> entry.key.first == 0 }.values
                                else if (key.first == 3 && key.second == 2) insideGrid.filter { entry -> entry.key.first == 4 }.values
                                else if (key.first == 2 && key.second == 1) insideGrid.filter { entry -> entry.key.second == 0 }.values
                                else insideGrid.filter { entry -> entry.key.second == 4 }.values
                            } else listOf(currentGrid.getValue(it))
                        }.filter { value -> value == '#' }.size
                        if (entry.value == '#') {
                            if (adjacentBugCount == 1) '#'
                            else '.'
                        } else {
                            if (adjacentBugCount == 1 || adjacentBugCount == 2) '#'
                            else '.'
                        }
                    } else '.'
                }
            }
        }
        return allGrids.map { it.values.filter { value -> value == '#' }.size }.sum()
    }
}
