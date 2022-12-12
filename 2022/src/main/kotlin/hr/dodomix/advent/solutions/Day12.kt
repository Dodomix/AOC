package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Position


class Day12 {
    fun dayDirectory() = "day12"

    fun part1(input: List<String>): Int {
        val (startingPosition, endPosition, areaMap) = createAreaMap(input)

        return executeBfs(startingPosition, areaMap, endPosition).getValue(endPosition)
    }

    fun part2(input: List<String>): Int {
        val (_, endPosition, areaMap) = createAreaMap(input)

        return areaMap.filter { it.value == 'a' }.minOf { (startingPosition, _) ->
            executeBfs(startingPosition, areaMap, endPosition)[endPosition] ?: Integer.MAX_VALUE
        }
    }

    private fun createAreaMap(input: List<String>): Triple<Position, Position, Map<Position, Char>> {
        var startingPosition = Position(0, 0)
        var endPosition = Position(0, 0)
        val areaMap = input.flatMapIndexed { row, line ->
            line.mapIndexed { column, value ->
                when (value) {
                    'S' -> {
                        startingPosition = Position(row, column)
                        Pair(Position(row, column), 'a')
                    }

                    'E' -> {
                        endPosition = Position(row, column)
                        Pair(Position(row, column), 'z')
                    }

                    else -> Pair(Position(row, column), value)
                }
            }
        }.toMap()
        return Triple(startingPosition, endPosition, areaMap)
    }

    private fun executeBfs(
        startingPosition: Position,
        areaMap: Map<Position, Char>,
        endPosition: Position
    ): Map<Position, Int> {
        val positionDistance: MutableMap<Position, Int> = mutableMapOf(Pair(startingPosition, 0))
        var currentPosition: Position
        val positionsToVisit = mutableListOf(startingPosition)

        do {
            positionsToVisit.sortBy { positionDistance.getValue(it) }
            if (positionsToVisit.isEmpty()) {
                return positionDistance
            }
            currentPosition = positionsToVisit.first()
            positionsToVisit.remove(currentPosition)
            val currentPositionDistance = positionDistance.getValue(currentPosition)
            listOf(
                Position(currentPosition.row - 1, currentPosition.column),
                Position(currentPosition.row + 1, currentPosition.column),
                Position(currentPosition.row, currentPosition.column - 1),
                Position(currentPosition.row, currentPosition.column + 1)
            )
                .filter {
                    areaMap.containsKey(it) && !positionDistance.containsKey(it) &&
                            areaMap.getValue(it) - areaMap.getValue(currentPosition) <= 1
                }
                .forEach {
                    positionDistance[it] = currentPositionDistance + 1
                    positionsToVisit.add(it)
                }
        } while (currentPosition != endPosition)

        return positionDistance
    }
}
