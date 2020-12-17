package hr.dodomix.advent.solutions

class Day17 {
    fun dayDirectory() = "day17"

    fun part1(input: List<String>): Int {
        var state = emptyMap<Point3D, Char>().toMutableMap().withDefault { '.' }
        input.forEachIndexed { row, line ->
            line.forEachIndexed { column, value ->
                state[Point3D(column, row, 0)] = value
            }
        }
        (0 until 6).forEach { _ ->
            val newState = HashMap(state).withDefault { '.' }
            (state.minOf { it.key.x - 1 }..state.maxOf { it.key.x + 1 })
                .forEach { x ->
                    (state.minOf { it.key.y - 1 }..state.maxOf { it.key.y + 1 })
                        .forEach { y ->
                            (state.minOf { it.key.z - 1 }..state.maxOf { it.key.z + 1 })
                                .forEach { z ->
                                    newState[Point3D(x, y, z)] = calculateNew3DValue(state, Point3D(x, y, z))
                                }
                        }
                }
            state = newState
        }
        return state.values.count { it == '#' }
    }

    fun part2(input: List<String>): Int {
        var state = emptyMap<Point4D, Char>().toMutableMap().withDefault { '.' }
        input.forEachIndexed { row, line ->
            line.forEachIndexed { column, value ->
                state[Point4D(column, row, 0, 0)] = value
            }
        }
        (0 until 6).forEach { _ ->
            val newState = HashMap(state).withDefault { '.' }
            (state.minOf { it.key.x - 1 }..state.maxOf { it.key.x + 1 })
                .forEach { x ->
                    (state.minOf { it.key.y - 1 }..state.maxOf { it.key.y + 1 })
                        .forEach { y ->
                            (state.minOf { it.key.z - 1 }..state.maxOf { it.key.z + 1 })
                                .forEach { z ->
                                    (state.minOf { it.key.w - 1 }..state.maxOf { it.key.w + 1 })
                                        .forEach { w ->
                                            newState[Point4D(x, y, z, w)] = calculateNew4DValue(state, Point4D(x, y, z, w))
                                        }
                                }
                        }
                }
            state = newState
        }
        return state.values.count { it == '#' }
    }

    private fun calculateNew3DValue(
        state: Map<Point3D, Char>,
        position: Point3D
    ): Char {
        val (x, y, z) = position

        val neighborsCount = (-1..1).flatMap { xDiff ->
            (-1..1).flatMap { yDiff ->
                (-1..1).map { zDiff ->
                    if (listOf(xDiff, yDiff, zDiff).any { it != 0 }) {
                        state.getValue(Point3D(x + xDiff, y + yDiff, z + zDiff))
                    } else {
                        '.'
                    }
                }
            }
        }.count { it == '#' }
        return when {
            state.getValue(position) == '#' -> when (neighborsCount) {
                2, 3 -> '#'
                else -> '.'
            }
            else -> when (neighborsCount) {
                3 -> '#'
                else -> '.'
            }
        }
    }

    private fun calculateNew4DValue(
        state: Map<Point4D, Char>,
        position: Point4D
    ): Char {
        val (x, y, z, w) = position

        val neighborsCount = (-1..1).flatMap { xDiff ->
            (-1..1).flatMap { yDiff ->
                (-1..1).flatMap { zDiff ->
                    (-1..1).map { wDiff ->
                        if (listOf(xDiff, yDiff, zDiff, wDiff).any { it != 0 }) {
                            state.getValue(Point4D(x + xDiff, y + yDiff, z + zDiff, w + wDiff))
                        } else {
                            '.'
                        }
                    }
                }
            }
        }.count { it == '#' }
        return when {
            state.getValue(position) == '#' -> when (neighborsCount) {
                2, 3 -> '#'
                else -> '.'
            }
            else -> when (neighborsCount) {
                3 -> '#'
                else -> '.'
            }
        }
    }

    private data class Point3D(val x: Int, val y: Int, val z: Int)

    private data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int)
}
