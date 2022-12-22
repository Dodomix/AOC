package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Position


class Day22 {

    fun dayDirectory() = "day22"

    fun part1(input: List<String>): Int {
        val directions = listOf('>', 'v', '<', '^')
        val positions = mutableMapOf<Position, Char>().withDefault { ' ' }
        input.forEachIndexed { row, line ->
            if (line.isNotEmpty() && line.first() in listOf(' ', '.', '#')) {
                line.forEachIndexed { column, char ->
                    if (char != ' ') {
                        positions[Position(row, column)] = char
                    }
                }
            }
        }
        var instructions = input.last()
        var position = positions.minBy { it.key.row * 1000 + it.key.column }.key
        var direction = '>'
        while (instructions.isNotEmpty()) {
            if (instructions.first().isDigit()) {
                val distanceToMove = instructions.takeWhile { it.isDigit() }.toInt()
                instructions = instructions.dropWhile { it.isDigit() }
                val movePosition = calculateMoveDirection(direction)
                for (i in 0 until distanceToMove) {
                    val nextPosition = position.translate(movePosition)
                    when (positions.getValue(nextPosition)) {
                        '#' -> break
                        '.' -> position = nextPosition
                        ' ' -> {
                            val potentialNextPosition = when (direction) {
                                '>' -> positions.filter { it.key.row == position.row }.minBy { it.key.column }.key
                                'v' -> positions.filter { it.key.column == position.column }.minBy { it.key.row }.key
                                '<' -> positions.filter { it.key.row == position.row }.maxBy { it.key.column }.key
                                '^' -> positions.filter { it.key.column == position.column }.maxBy { it.key.row }.key
                                else -> throw RuntimeException("Invalid direction")
                            }
                            if (positions.getValue(potentialNextPosition) != '#') {
                                position = potentialNextPosition
                            }
                        }
                    }
                }
            } else {
                val rotation = instructions.first()
                instructions = instructions.drop(1)
                direction = if (rotation == 'R') {
                    directions[(directions.indexOf(direction) + 1) % directions.size]
                } else {
                    directions[(directions.indexOf(direction) + 3) % directions.size]
                }
            }
        }
        return (position.row + 1) * 1000 + (position.column + 1) * 4 + directions.indexOf(direction)
    }

    fun part2(input: List<String>): Int {
        val directions = listOf('>', 'v', '<', '^')
        val positions = mutableMapOf<Position, Char>().withDefault { ' ' }
        input.forEachIndexed { row, line ->
            if (line.isNotEmpty() && line.first() in listOf(' ', '.', '#')) {
                line.forEachIndexed { column, char ->
                    if (char != ' ') {
                        positions[Position(row, column)] = char
                    }
                }
            }
        }
        var instructions = input.last()
        var position = positions.minBy { it.key.row * 1000 + it.key.column }.key
        var direction = '>'
        while (instructions.isNotEmpty()) {
            if (instructions.first().isDigit()) {
                val distanceToMove = instructions.takeWhile { it.isDigit() }.toInt()
                instructions = instructions.dropWhile { it.isDigit() }
                var movePosition = calculateMoveDirection(direction)
                for (i in 0 until distanceToMove) {
                    val nextPosition = position.translate(movePosition)
                    when (positions.getValue(nextPosition)) {
                        '#' -> break
                        '.' -> position = nextPosition
                        ' ' -> {
                            var potentialDirection: Char
                            val potentialNextPosition = when (direction) {
                                '>' -> {
                                    if (position.row < 50) {
                                        potentialDirection = '<'
                                        Position(149 - position.row, 99)
                                    } else if (position.row < 100) {
                                        potentialDirection = '^'
                                        Position(49, 50 + position.row)
                                    } else if (position.row < 150) {
                                        potentialDirection = '<'
                                        Position(149 - position.row, 149)
                                    } else {
                                        potentialDirection = '^'
                                        Position(149, position.row - 100)
                                    }
                                }

                                'v' -> {
                                    if (position.column < 50) {
                                        potentialDirection = 'v'
                                        Position(0, position.column + 100)
                                    } else if (position.column < 100) {
                                        potentialDirection = '<'
                                        Position(position.column + 100, 49)
                                    } else {
                                        potentialDirection = '<'
                                        Position(position.column - 50, 99)
                                    }
                                }

                                '<' -> {
                                    if (position.row < 50) {
                                        potentialDirection = '>'
                                        Position(149 - position.row, 0)
                                    } else if (position.row < 100) {
                                        potentialDirection = 'v'
                                        Position(100, position.row - 50)
                                    } else if (position.row < 150) {
                                        potentialDirection = '>'
                                        Position(149 - position.row, 50)
                                    } else {
                                        potentialDirection = 'v'
                                        Position(0, position.row - 100)
                                    }
                                }

                                '^' -> {
                                    if (position.column < 50) {
                                        potentialDirection = '>'
                                        Position(position.column + 50, 50)
                                    } else if (position.column < 100) {
                                        potentialDirection = '>'
                                        Position(position.column + 100, 0)
                                    } else {
                                        potentialDirection = '^'
                                        Position(199, position.column - 100)
                                    }
                                }

                                else -> throw RuntimeException("Invalid direction")
                            }
                            if (positions.getValue(potentialNextPosition) == ' ') {
                                throw RuntimeException("Invalid position")
                            }
                            if (positions.getValue(potentialNextPosition) != '#') {
                                position = potentialNextPosition
                                direction = potentialDirection
                                movePosition = calculateMoveDirection(direction)
                            }
                        }
                    }
                }
            } else {
                val rotation = instructions.first()
                instructions = instructions.drop(1)
                direction = if (rotation == 'R') {
                    directions[(directions.indexOf(direction) + 1) % directions.size]
                } else {
                    directions[(directions.indexOf(direction) + 3) % directions.size]
                }
            }
        }
        return (position.row + 1) * 1000 + (position.column + 1) * 4 + directions.indexOf(direction)
    }

    private fun calculateMoveDirection(direction: Char) = when (direction) {
        '>' -> Position(0, 1)
        'v' -> Position(1, 0)
        '<' -> Position(0, -1)
        '^' -> Position(-1, 0)
        else -> throw RuntimeException("Invalid direction")
    }
}
