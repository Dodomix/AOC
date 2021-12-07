package hr.dodomix.advent.solutions

import kotlin.math.abs

class Day7 {
    fun dayDirectory() = "day7"

    fun part1(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }
        return (positions.minOf { it }..positions.maxOf { it }).minOf { positions.sumOf { position -> abs(position - it) } }
    }

    fun part2(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }
        return (positions.minOf { it }..positions.maxOf { it }).minOf {
            positions.sumOf { position ->
                val difference = abs(position - it)
                ((difference + 1) * difference) / 2
            }
        }
    }
}
