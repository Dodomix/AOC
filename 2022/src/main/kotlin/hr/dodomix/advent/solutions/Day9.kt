package hr.dodomix.advent.solutions

class Day9 {
    fun dayDirectory() = "day9"

    fun part1(input: List<String>): Int =
        input.fold(State(emptySet(), Position(0, 0), Position(0, 0))) { state, line ->
            val (direction, count) = line.split(" ")
            (0 until count.toInt()).fold(state) { (positions, head, tail), _ ->
                val newHead = when (direction) {
                    "U" -> {
                        Position(head.row - 1, head.column)
                    }

                    "D" -> {
                        Position(head.row + 1, head.column)
                    }

                    "L" -> {
                        Position(head.row, head.column - 1)
                    }

                    "R" -> {
                        Position(head.row, head.column + 1)
                    }

                    else -> throw RuntimeException("Invalid direction")
                }
                val newTail = calculateNewPosition(newHead, tail)
                State(positions + newTail, newHead, newTail)
            }
        }.positions.count()

    fun part2(input: List<String>): Int =
        input.fold(State2(emptySet(), Position(0, 0), (0..8).map { Position(0, 0) })) { state, line ->
            val (direction, count) = line.split(" ")
            (0 until count.toInt()).fold(state) { (positions, head, tails), _ ->
                val newHead = when (direction) {
                    "U" -> {
                        Position(head.row - 1, head.column)
                    }

                    "D" -> {
                        Position(head.row + 1, head.column)
                    }

                    "L" -> {
                        Position(head.row, head.column - 1)
                    }

                    "R" -> {
                        Position(head.row, head.column + 1)
                    }

                    else -> throw RuntimeException("Invalid direction")
                }

                val newTails = tails.drop(1).fold(listOf(calculateNewPosition(newHead, tails[0]))) { newTails, tail ->
                    newTails + calculateNewPosition(newTails.last(), tail)
                }
                State2(positions + newTails.last(), newHead, newTails)
            }
        }.positions.count()

    private fun calculateNewPosition(newPosition1: Position, oldPosition2: Position): Position = when {
        newPosition1.row - oldPosition2.row == 2 && newPosition1.column - oldPosition2.column == 2 ->
            Position(oldPosition2.row + 1, oldPosition2.column + 1)

        newPosition1.row - oldPosition2.row == 2 && newPosition1.column - oldPosition2.column == -2 ->
            Position(oldPosition2.row + 1, oldPosition2.column - 1)

        newPosition1.row - oldPosition2.row == -2 && newPosition1.column - oldPosition2.column == 2 ->
            Position(oldPosition2.row - 1, oldPosition2.column + 1)

        newPosition1.row - oldPosition2.row == -2 && newPosition1.column - oldPosition2.column == -2 ->
            Position(oldPosition2.row - 1, oldPosition2.column - 1)

        newPosition1.row - oldPosition2.row == 2 -> Position(oldPosition2.row + 1, newPosition1.column)
        newPosition1.row - oldPosition2.row == -2 -> Position(oldPosition2.row - 1, newPosition1.column)
        newPosition1.column - oldPosition2.column == 2 -> Position(newPosition1.row, oldPosition2.column + 1)
        newPosition1.column - oldPosition2.column == -2 -> Position(newPosition1.row, oldPosition2.column - 1)
        else -> oldPosition2
    }

    data class Position(val row: Int, val column: Int)

    data class State(val positions: Set<Position>, val head: Position, val tail: Position)
    data class State2(val positions: Set<Position>, val head: Position, val tails: List<Position>)
}
