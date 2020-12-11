package hr.dodomix.advent.solutions

class Day11 {
    fun dayDirectory() = "day11"

    fun part1(input: List<String>): Int {
        generateSequence(0) { it + 1 }
            .fold(input.map { it.toCharArray().toList() }) { fields, _ ->
                val newFields = fields.mapIndexed { i, row ->
                    row.mapIndexed { j, field ->
                        val belowField = fields.getOrNull(i - 1)
                        val aboveField = fields.getOrNull(i + 1)
                        val fieldsAroundTaken = listOf(
                            belowField?.getOrNull(j - 1) == '#',
                            belowField?.getOrNull(j) == '#',
                            belowField?.getOrNull(j + 1) == '#',
                            row.getOrNull(j - 1) == '#',
                            row.getOrNull(j + 1) == '#',
                            aboveField?.getOrNull(j - 1) == '#',
                            aboveField?.getOrNull(j) == '#',
                            aboveField?.getOrNull(j + 1) == '#'
                        )
                        when (field) {
                            'L' -> when {
                                fieldsAroundTaken.none { it } -> '#'
                                else -> 'L'
                            }
                            '#' -> when {
                                fieldsAroundTaken.count { it } >= 4 -> 'L'
                                else -> '#'
                            }
                            else -> '.'
                        }
                    }
                }
                if (fields == newFields) {
                    return fields.sumBy { row -> row.count { it == '#' } }
                }
                newFields
            }
        return 0
    }

    fun part2(input: List<String>): Int {
        val positionIncrements = listOf(
            Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
            Pair(0, -1), Pair(0, 1),
            Pair(1, -1), Pair(1, 0), Pair(1, 1)
        )
        generateSequence(0) { it + 1 }
            .fold(input.map { it.toCharArray().toList() }) { fields, _ ->
                val newFields = fields.mapIndexed { i, row ->
                    row.mapIndexed { j, field ->
                        val seatsTaken = positionIncrements.map { (iIncrement, jIncrement) ->
                            generateSequence(1) { it + 1 }.forEach { multiplier ->
                                val newI = i + multiplier * iIncrement
                                val newJ = j + multiplier * jIncrement
                                if (newI < 0 || newJ < 0 || newI >= fields.size || newJ >= row.size) {
                                    return@map false
                                } else {
                                    val seenPosition = fields[newI][newJ]
                                    if (seenPosition == '#') {
                                        return@map true
                                    } else if (seenPosition == 'L') {
                                        return@map false
                                    }
                                }
                            }
                            false
                        }
                        when (field) {
                            'L' -> when {
                                seatsTaken.none { it } -> '#'
                                else -> 'L'
                            }
                            '#' -> when {
                                seatsTaken.count { it } >= 5 -> 'L'
                                else -> '#'
                            }
                            else -> '.'
                        }
                    }
                }
                if (fields == newFields) {
                    return fields.sumBy { row -> row.count { it == '#' } }
                }
                newFields
            }
        return 0
    }
}
