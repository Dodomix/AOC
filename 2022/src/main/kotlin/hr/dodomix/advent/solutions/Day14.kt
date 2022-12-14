package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Position


class Day14 {
    fun dayDirectory() = "day14"

    fun part1(input: List<String>): Int {
        val cave = readInputAsCave(input)
        val source = Position(0, 500)
        val lastRow = cave.maxOf { it.key.row }

        while (true) {
            var sandPosition = source
            var newSandPosition = move(cave, sandPosition)
            while (sandPosition != newSandPosition) {
                sandPosition = newSandPosition
                newSandPosition = move(cave, sandPosition)
                if (newSandPosition.row > lastRow) {
                    return cave.count { it.value == 'o' }
                }
            }
            cave[sandPosition] = 'o'
        }
    }

    fun part2(input: List<String>): Int {
        val cave = readInputAsCave(input)
        val source = Position(0, 500)
        val lastRow = cave.maxOf { it.key.row } + 2

        while (true) {
            var sandPosition = source
            var newSandPosition = move2(cave, sandPosition, lastRow)
            while (sandPosition != newSandPosition) {
                sandPosition = newSandPosition
                newSandPosition = move2(cave, sandPosition, lastRow)
            }
            cave[sandPosition] = 'o'
            if (newSandPosition == source) {
                return cave.count { it.value == 'o' }
            }
        }
    }

    private fun readInputAsCave(input: List<String>) = input.fold(mapOf<Position, Char>()) { map, line ->
        val positions = line.split(" -> ").map { position ->
            val (column, row) = position.split(",").map { it.toInt() }
            Position(row, column)
        }
        map + positions.flatMapIndexed { i, position ->
            if (positions.lastIndex != i) {
                val nextPosition = positions[i + 1]
                if (position.row == nextPosition.row) {
                    (position.column..nextPosition.column)
                        .map { column -> Pair(Position(position.row, column), '#') } +
                            (nextPosition.column..position.column)
                                .map { column -> Pair(Position(position.row, column), '#') }
                } else {
                    (position.row..nextPosition.row)
                        .map { row -> Pair(Position(row, position.column), '#') } +
                            (nextPosition.row..position.row)
                                .map { row -> Pair(Position(row, position.column), '#') }
                }
            } else {
                emptyList()
            }
        }
    }.withDefault { '.' }.toMutableMap()

    private fun move(cave: Map<Position, Char>, sandPosition: Position): Position =
        listOf(
            Position(sandPosition.row + 1, sandPosition.column),
            Position(sandPosition.row + 1, sandPosition.column - 1),
            Position(sandPosition.row + 1, sandPosition.column + 1),
            sandPosition
        ).first { cave.getOrDefault(it, '.') == '.' }

    private fun move2(cave: Map<Position, Char>, sandPosition: Position, lastRow: Int): Position =
        listOf(
            Position(sandPosition.row + 1, sandPosition.column),
            Position(sandPosition.row + 1, sandPosition.column - 1),
            Position(sandPosition.row + 1, sandPosition.column + 1),
            sandPosition
        ).first { cave.getOrElse(it) { if (it.row == lastRow) '#' else '.' } == '.' }
}
