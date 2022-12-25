package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Position

class Day24 {

    fun dayDirectory() = "day24"

    fun part1(input: List<String>): Int {
        val startPosition = Position(0, input.first().indexOf('.'))
        val endPosition = Position(input.size - 1, input.last().indexOf('.'))
        val minRow = 0
        val maxRow = input.size - 1
        val minColumn = 0
        val maxColumn = input.first().length - 1
        val blizzards = parseBlizzards(input, minRow, maxRow, minColumn, maxColumn)

        return calculateTimeToPosition(blizzards, startPosition, endPosition, minRow, maxRow, minColumn, maxColumn)
    }

    fun part2(input: List<String>): Int {
        val startPosition = Position(0, input.first().indexOf('.'))
        val endPosition = Position(input.size - 1, input.last().indexOf('.'))
        val minRow = 0
        val maxRow = input.size - 1
        val minColumn = 0
        val maxColumn = input.first().length - 1
        val blizzards = parseBlizzards(input, minRow, maxRow, minColumn, maxColumn)

        return calculateTimeToPosition(blizzards, startPosition, endPosition, minRow, maxRow, minColumn, maxColumn) +
                calculateTimeToPosition(blizzards, endPosition, startPosition, minRow, maxRow, minColumn, maxColumn) +
                calculateTimeToPosition(blizzards, startPosition, endPosition, minRow, maxRow, minColumn, maxColumn)
    }

    private fun parseBlizzards(
        input: List<String>,
        minRow: Int,
        maxRow: Int,
        minColumn: Int,
        maxColumn: Int
    ) = input.foldIndexed(listOf<Blizzard>()) { row, blizzards, line ->
        line.foldIndexed(blizzards) { column, currentBlizzards, c ->
            when (c) {
                '^' -> currentBlizzards + Blizzard(
                    Position(row, column),
                    Position(-1, 0),
                    minRow,
                    maxRow,
                    minColumn,
                    maxColumn
                )

                '<' -> currentBlizzards + Blizzard(
                    Position(row, column),
                    Position(0, -1),
                    minRow,
                    maxRow,
                    minColumn,
                    maxColumn
                )

                '>' -> currentBlizzards + Blizzard(
                    Position(row, column),
                    Position(0, 1),
                    minRow,
                    maxRow,
                    minColumn,
                    maxColumn
                )

                'v' -> currentBlizzards + Blizzard(
                    Position(row, column),
                    Position(1, 0),
                    minRow,
                    maxRow,
                    minColumn,
                    maxColumn
                )

                else -> currentBlizzards
            }
        }
    }

    private fun calculateTimeToPosition(
        blizzards: List<Blizzard>,
        startPosition: Position,
        endPosition: Position,
        minRow: Int,
        maxRow: Int,
        minColumn: Int,
        maxColumn: Int
    ): Int {
        var currentPositions = listOf(startPosition)
        var time = 0
        while (true) {
            time++
            val newPositions = mutableSetOf<Position>()
            blizzards.forEach { it.move() }
            currentPositions.forEach { position ->
                newPositions += moves
                    .map { position.translate(it) }
                    .filter { newPosition -> blizzards.none { blizzard -> blizzard.position == newPosition } }
                    .filter { newPosition ->
                        if (newPosition == endPosition) {
                            return time
                        }
                        (newPosition.row in (minRow + 1) until maxRow &&
                                newPosition.column in (minColumn + 1) until maxColumn) || newPosition == startPosition
                    }
            }
            currentPositions = newPositions.toList()
        }
    }

    private data class Blizzard(
        var position: Position,
        val direction: Position,
        val minRow: Int,
        val maxRow: Int,
        val minColumn: Int,
        val maxColumn: Int
    ) {
        fun move() {
            val nextPosition = position.translate(direction)
            position = if (nextPosition.row == minRow) {
                Position(maxRow - 1, position.column)
            } else if (nextPosition.row == maxRow) {
                Position(minRow + 1, position.column)
            } else if (nextPosition.column == minColumn) {
                Position(position.row, maxColumn - 1)
            } else if (nextPosition.column == maxColumn) {
                Position(position.row, minColumn + 1)
            } else {
                nextPosition
            }
        }
    }

    companion object {
        private val moves = listOf(
            Position(0, 0), Position(-1, 0),
            Position(1, 0), Position(0, -1), Position(0, 1)
        )
    }
}