package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Position


class Day23 {

    fun dayDirectory() = "day23"

    fun part1(input: List<String>): Int {
        val positions = input.flatMapIndexed { row, line ->
            line.mapIndexed { column, c ->
                Pair(Position(row, column), c)
            }
        }.toMap().toMutableMap().withDefault { '.' }
        val directionsToLook = mutableListOf(
            listOf(Position(-1, -1), Position(-1, 0), Position(-1, 1)),
            listOf(Position(1, -1), Position(1, 0), Position(1, 1)),
            listOf(Position(-1, -1), Position(0, -1), Position(1, -1)),
            listOf(Position(-1, 1), Position(0, 1), Position(1, 1))
        )
        for (i in 0 until 10) {
            val elfPositions = calculateElvesToMove(positions)
            calculateElfMovements(elfPositions, directionsToLook).forEach { (oldPosition, newPosition) ->
                positions[newPosition] = '#'
                positions[oldPosition] = '.'
            }
            directionsToLook.add(directionsToLook.removeAt(0))
        }
        val elves = positions.filter { it.value == '#' }
        return (elves.minOf { it.key.row }..elves.maxOf { it.key.row }).sumOf { row ->
            (elves.minOf { it.key.column }..elves.maxOf { it.key.column }).count { column ->
                positions.getValue(Position(row, column)) == '.'
            }
        }
    }

    fun part2(input: List<String>): Int {
        val positions = input.flatMapIndexed { row, line ->
            line.mapIndexed { column, c ->
                Pair(Position(row, column), c)
            }
        }.toMap().toMutableMap().withDefault { '.' }
        val directionsToLook = mutableListOf(
            listOf(Position(-1, -1), Position(-1, 0), Position(-1, 1)),
            listOf(Position(1, -1), Position(1, 0), Position(1, 1)),
            listOf(Position(-1, -1), Position(0, -1), Position(1, -1)),
            listOf(Position(-1, 1), Position(0, 1), Position(1, 1))
        )
        generateSequence(1) { it + 1 }.forEach { round ->
            val elfPositions = calculateElvesToMove(positions)
            if (elfPositions.isEmpty()) {
                return round
            }
            calculateElfMovements(elfPositions, directionsToLook).forEach { (oldPosition, newPosition) ->
                positions[newPosition] = '#'
                positions[oldPosition] = '.'
            }
            directionsToLook.add(directionsToLook.removeAt(0))
        }
        return -1
    }

    private fun calculateElfMovements(
        elfPositions: Set<Position>,
        directionsToLook: MutableList<List<Position>>
    ): List<Pair<Position, Position>> {
        val futureElfPositions = mutableSetOf<Position>()
        val duplicatePositions = mutableSetOf<Position>()
        return elfPositions.map { elfPosition ->
            val newPosition = elfPosition.calculateNewPosition(elfPositions, directionsToLook)
            if (futureElfPositions.contains(newPosition)) {
                duplicatePositions.add(newPosition)
            } else {
                futureElfPositions.add(newPosition)
            }
            Pair(elfPosition, newPosition)
        }.filter { !duplicatePositions.contains(it.second) }
            .filter { it.first != it.second }
    }

    private fun calculateElvesToMove(
        positions: MutableMap<Position, Char>
    ): Set<Position> {
        val elfPositions = positions
            .filter { it.value == '#' }
            .filter { (elfPosition, _) ->
                NEIGHBORING_POSITIONS
                    .map { it.translate(elfPosition) }
                    .map { positions.getValue(it) }
                    .any { it == '#' }
            }.map { it.key }
            .toSet()
        return elfPositions
    }

    companion object {
        private val NEIGHBORING_POSITIONS = listOf(
            Position(-1, -1),
            Position(-1, 0),
            Position(-1, 1),
            Position(0, -1),
            Position(0, 1),
            Position(1, -1),
            Position(1, 0),
            Position(1, 1)
        )
    }
}

private fun Position.calculateNewPosition(
    elfPositions: Set<Position>,
    directionsToLook: MutableList<List<Position>>
): Position {
    val direction = directionsToLook.find {
        it.map { direction -> this.translate(direction) }
            .all { neighbor -> !elfPositions.contains(neighbor) }
    }
    return if (direction != null) {
        this.translate(direction[1])
    } else {
        this
    }
}
