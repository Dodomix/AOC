package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.LongPosition
import kotlin.math.abs


class Day17 {
    fun dayDirectory() = "day17"

    fun part1(input: List<String>): Long {
        val wind = input[0]
        return calculateHeight(wind, 2022)
    }

    fun part2(input: List<String>): Long {
        val wind = input[0]
        return calculateHeight(wind, 1000000000000)
    }

    private fun calculateHeight(wind: String, rocksToFall: Long): Long {
        val windIterator = generateSequence(0) { it + 1 }.map { wind[it % wind.length] }.iterator()
        val history = mutableMapOf<List<LongPosition>, MutableMap<Pair<Int, Int>, Pair<Long, Long>>>()
        val heightHistory = mutableMapOf<Long, Long>()
        var windCount = 0
        var height = 0L
        return abs((0 until rocksToFall).fold((0L until 7L)
            .map { LongPosition(0, it) }) { takenPositions, rock ->
            val initialPosition = LongPosition(takenPositions.minOf { it.row } - 4, 2)
            val shape = shapeOrder[(rock % 5).toInt()]
            val currentHeight = abs(takenPositions.minOf { it.row }) + height
            val positionHash = takenPositions.hashCode()
            history.putIfAbsent(shape, mutableMapOf())
            val previousOccurrence = history.getValue(shape)[Pair(positionHash, windCount % wind.length)]
            if (previousOccurrence != null) {
                val (previousRock, previousHeight) = previousOccurrence
                val repetitionInterval = rock - previousRock
                val repetitions = (rocksToFall - rock) / repetitionInterval
                return currentHeight + repetitions * (currentHeight - previousHeight) +
                        heightHistory.getValue(previousRock + ((rocksToFall - rock) % repetitionInterval)) - previousHeight
            }
            history.getValue(shape)[Pair(positionHash, windCount % wind.length)] =
                Pair(rock, currentHeight)
            heightHistory[rock] = currentHeight

            var fallingShape = shape.map { it.translate(initialPosition) }
            while (true) {
                val nextWindPosition = when (windIterator.next()) {
                    '>' -> LongPosition(0, 1)
                    '<' -> LongPosition(0, -1)
                    else -> throw RuntimeException("Unknown wind direction")
                }
                windCount++
                val translatedShape = fallingShape.map { it.translate(nextWindPosition) }
                if (translatedShape.none { it.column < 0 || it.column > 6 || takenPositions.contains(it) }) {
                    fallingShape = translatedShape
                }
                val shapeAfterFall = fallingShape.map { it.translate(LongPosition(1, 0)) }
                if (shapeAfterFall.none { takenPositions.contains(it) }) {
                    fallingShape = shapeAfterFall
                } else {
                    break
                }
            }

            val newTakenPositions = takenPositions + fallingShape
            val minPosition = fallingShape.minOf { it.row }
            val maxPosition = fallingShape.maxOf { it.row }
            val filledRow = (minPosition..maxPosition).find { row ->
                (0L until 7L).all { column -> newTakenPositions.contains(LongPosition(row, column)) }
            }
            if (filledRow != null) {
                height += abs(filledRow)
                newTakenPositions.filter { it.row <= filledRow }.map { it.translate(LongPosition(-filledRow, 0)) }
            } else {
                newTakenPositions
            }
        }.minOf { it.row }) + height
    }


    companion object {
        val shapeOrder = listOf(
            listOf(
                LongPosition(0, 0), LongPosition(0, 1),
                LongPosition(0, 2), LongPosition(0, 3)
            ),
            listOf(
                LongPosition(-2, 1),
                LongPosition(-1, 0), LongPosition(-1, 1), LongPosition(-1, 2),
                LongPosition(0, 1)
            ),
            listOf(
                LongPosition(-2, 2),
                LongPosition(-1, 2),
                LongPosition(0, 0), LongPosition(0, 1), LongPosition(0, 2)
            ),
            listOf(
                LongPosition(-3, 0), LongPosition(-2, 0),
                LongPosition(-1, 0), LongPosition(0, 0)
            ),
            listOf(
                LongPosition(-1, 0), LongPosition(-1, 1),
                LongPosition(0, 0), LongPosition(0, 1)
            )
        )
    }
}
