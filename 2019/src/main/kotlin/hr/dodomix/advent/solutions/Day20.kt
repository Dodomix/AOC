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
                        } else {
                            throw RuntimeException("wtf")
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
                        } else {
                            throw RuntimeException("wtf")
                        }
                        grid[Pair(i, j)] = portal
                        grid[Pair(i + 1, j)] = portal
                    }
                } else {
                    grid[Pair(i, j)] = value.toString()
                }
            }
        }
        return bfs(grid, portals, portals.getValue("AA")[0])
    }

    private fun bfs(
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
            println("$position${grid[position]}$distance")
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
            println(newPositions)
            positions.addAll(newPositions)
        }
        return -1
    }

    private fun bfs2(
        grid: MutableMap<Pair<Int, Int>, String>,
        portals: Map<String, List<Pair<Int, Int>>>,
        initialPosition: Pair<Int, Int>
    ): Int {
        val positions = mutableListOf(Triple(initialPosition, 0, setOf<String>()))
        val seenPositions = mutableSetOf<Pair<Pair<Int, Int>, Set<String>>>()
        val lastPosition = portals.getValue("ZZ")[0]
        while (positions.isNotEmpty()) {
            val element = positions.removeAt(0)
            val position = element.first
            val distance = element.second
            val visitedPortals = element.third
            if (position == lastPosition && visitedPortals.isEmpty()) return distance
            if (seenPositions.contains(Pair(position, visitedPortals))) continue
            seenPositions.add(Pair(position, visitedPortals))
            val newPositions = listOf(
                Pair(position.first + 1, position.second),
                Pair(position.first - 1, position.second),
                Pair(position.first, position.second + 1),
                Pair(position.first, position.second - 1)
            ).filter { grid[it] != " " && grid[it] != "#" && grid[it] != "AA" && (grid[it] != "ZZ" || visitedPortals.isEmpty()) }
                .map { newPosition ->
                    val nextPosition = if (grid.getValue(newPosition).any { it.isUpperCase() }) {
                        portals.getValue(grid.getValue(newPosition)).find { it != position }!!
                    } else {
                        newPosition
                    }
                    val nextVisitedPortals = if (grid.getValue(newPosition).any { it.isUpperCase() }) {
                        if (visitedPortals.contains(grid.getValue(newPosition))) {
                            visitedPortals - grid.getValue(newPosition)
                        } else {
                            visitedPortals + grid.getValue(newPosition)
                        }
                    } else {
                        visitedPortals
                    }
                    Triple(
                        nextPosition, distance + 1, visitedPortals
                    )
                }.filter { !seenPositions.contains(Pair(it.first, it.third)) }
            positions.addAll(newPositions)
        }
        return -1
    }

    fun part2(input: List<String>): Any {
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
                        } else {
                            throw RuntimeException("wtf")
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
                        } else {
                            throw RuntimeException("wtf")
                        }
                        grid[Pair(i, j)] = portal
                        grid[Pair(i + 1, j)] = portal
                    }
                } else {
                    grid[Pair(i, j)] = value.toString()
                }
            }
        }
        return bfs2(grid, portals, portals.getValue("AA")[0])
    }
}
