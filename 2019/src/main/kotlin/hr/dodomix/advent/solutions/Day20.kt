package hr.dodomix.advent.solutions

class Day20 {

    fun dayDirectory() = "day20"

    fun part1(input: List<String>): Any {
        val grid = mutableMapOf<Pair<Int, Int>, String>()
        val portals = mutableMapOf<String, List<Pair<Int, Int>>>().withDefault { emptyList() }
        for (i in input.indices) {
            val line = input[i]
            for (j in line.indices) {
                val value = line[j]
                if (value.isUpperCase()) {
                    val rightValue = line.getOrNull(j + 1)
                    val bottomValue = input.getOrNull(i + 1)?.getOrNull(j)
                    if (rightValue?.isUpperCase() == true) {
                        val leftMazeValue = line.getOrNull(j - 1)
                        val rightMazeValue = line.getOrNull(j + 2)
                        val portal = "$value$rightValue"
                        if (leftMazeValue == '.') {
                            portals[portal] = portals.getValue(portal) + Pair(i, j - 1)
                        } else if (rightMazeValue == '.') {
                            portals[portal] = portals.getValue(portal) + Pair(i, j + 2)
                        }
                        grid[Pair(i, j)] = portal
                        grid[Pair(i, j + 1)] = portal
                    } else if (bottomValue?.isUpperCase() == true) {
                        val topMazeValue = input.getOrNull(i - 1)?.getOrNull(j)
                        val bottomMazeValue = input.getOrNull(i + 2)?.getOrNull(j)
                        val portal = "$value$bottomValue"
                        if (topMazeValue == '.') {
                            portals[portal] = portals.getValue(portal) + Pair(i - 1, j)
                        } else if (bottomMazeValue == '.') {
                            portals[portal] = portals.getValue(portal) + Pair(i + 2, j)
                        }
                        grid[Pair(i, j)] = portal
                        grid[Pair(i + 1, j)] = portal
                    }
                } else {
                    grid[Pair(i, j)] = value.toString()
                }
            }
        }
        return findExit(grid, portals, portals.getValue("AA")[0])
    }

    private fun findExit(
        grid: MutableMap<Pair<Int, Int>, String>,
        portals: Map<String, List<Pair<Int, Int>>>,
        initialPosition: Pair<Int, Int>
    ): Int {
        val positions = mutableListOf(Pair(initialPosition, 0))
        val seenPositions = mutableSetOf<Pair<Int, Int>>()
        val lastPosition = portals.getValue("ZZ")[0]
        while (positions.isNotEmpty()) {
            val element = positions.removeAt(0)
            val position = element.first
            val distance = element.second
            if (position == lastPosition) return distance
            if (seenPositions.contains(position)) continue
            seenPositions.add(position)
            val newPositions = listOf(
                Pair(position.first + 1, position.second),
                Pair(position.first - 1, position.second),
                Pair(position.first, position.second + 1),
                Pair(position.first, position.second - 1)
            ).filter { grid[it] != " " && grid[it] != "#" && grid[it] != "AA" }
                .map { newPosition ->
                    Pair(
                        if (grid.getValue(newPosition).any { it.isUpperCase() }) {
                            portals.getValue(grid.getValue(newPosition)).find { it != position }!!
                        } else {
                            newPosition
                        }, distance + 1
                    )
                }.filter { !seenPositions.contains(it.first) }
            positions.addAll(newPositions)
        }
        return -1
    }

    private fun findExitWithLevels(
        grid: MutableMap<Pair<Int, Int>, String>,
        portals: Map<String, List<Pair<Pair<Int, Int>, Boolean>>>,
        initialPosition: Pair<Int, Int>
    ): Int {
        val positions = mutableListOf(Triple(initialPosition, 0, 0))
        val seenPositions = mutableSetOf<Pair<Pair<Int, Int>, Int>>()
        val lastPosition = portals.getValue("ZZ")[0].first
        while (positions.isNotEmpty()) {
            val element = positions.removeAt(0)
            val position = element.first
            val distance = element.second
            val level = element.third
            if (position == lastPosition && level == 0) return distance
            if (seenPositions.contains(Pair(position, level))) continue
            seenPositions.add(Pair(position, level))
            val newPositions = listOf(
                Pair(position.first + 1, position.second),
                Pair(position.first - 1, position.second),
                Pair(position.first, position.second + 1),
                Pair(position.first, position.second - 1)
            ).filter { grid[it] != " " && grid[it] != "#" && grid[it] != "AA" && (grid[it] != "ZZ" || level == 0) }
                .map { newPosition ->
                    val gridValue = grid.getValue(newPosition)
                    val nextPosition = if (gridValue.any { it.isUpperCase() }) {
                        portals.getValue(gridValue).find { it.first != position }!!
                    } else {
                        Pair(newPosition, false)
                    }
                    val nextLevel = if (gridValue.any { it.isUpperCase() }) {
                        if (nextPosition.second) {
                            level + 1
                        } else {
                            level - 1
                        }
                    } else {
                        level
                    }
                    Triple(nextPosition.first, distance + 1, nextLevel)
                }
                .filter { !seenPositions.contains(Pair(it.first, it.third)) }
                .filter { it.third >= 0 }
            positions.addAll(newPositions)
        }
        return -1
    }

    fun part2(input: List<String>): Any {
        val grid = mutableMapOf<Pair<Int, Int>, String>()
        val portals = mutableMapOf<String, List<Pair<Pair<Int, Int>, Boolean>>>().withDefault { emptyList() }
        for (i in input.indices) {
            val line = input[i]
            for (j in line.indices) {
                val value = line[j]
                if (value.isUpperCase()) {
                    val rightValue = line.getOrNull(j + 1)
                    val bottomValue = input.getOrNull(i + 1)?.getOrNull(j)
                    if (rightValue?.isUpperCase() == true) {
                        val leftMazeValue = line.getOrNull(j - 1)
                        val rightMazeValue = line.getOrNull(j + 2)
                        val portal = "$value$rightValue"
                        if (leftMazeValue == '.') {
                            portals[portal] = portals.getValue(portal) + Pair(Pair(i, j - 1), rightMazeValue == null)
                        } else if (rightMazeValue == '.') {
                            portals[portal] = portals.getValue(portal) + Pair(Pair(i, j + 2), leftMazeValue == null)
                        }
                        grid[Pair(i, j)] = portal
                        grid[Pair(i, j + 1)] = portal
                    } else if (bottomValue?.isUpperCase() == true) {
                        val topMazeValue = input.getOrNull(i - 1)?.getOrNull(j)
                        val bottomMazeValue = input.getOrNull(i + 2)?.getOrNull(j)
                        val portal = "$value$bottomValue"
                        if (topMazeValue == '.') {
                            portals[portal] = portals.getValue(portal) + Pair(Pair(i - 1, j), bottomMazeValue == null)
                        } else if (bottomMazeValue == '.') {
                            portals[portal] = portals.getValue(portal) + Pair(Pair(i + 2, j), topMazeValue == null)
                        }
                        grid[Pair(i, j)] = portal
                        grid[Pair(i + 1, j)] = portal
                    }
                } else {
                    grid[Pair(i, j)] = value.toString()
                }
            }
        }
        return findExitWithLevels(grid, portals, portals.getValue("AA")[0].first)
    }
}
