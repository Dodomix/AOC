package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util

class Day1 {
    fun dayDirectory() = "day1"

    fun part1(input: List<String>): Int =
        input.map { it.toInt() }
            .map { it / 3 }
            .map { it - 2 }
            .sum()

    private fun calculateFuel(value: Int): Int =
        (value / 3 - 2)
            .takeIf { it > 0 }
            ?.let { it + calculateFuel(it) }
            ?: 0

    fun part2(input: List<String>): Int = input
        .map { it.toInt() }
        .map { calculateFuel(it) }
        .sum()
}