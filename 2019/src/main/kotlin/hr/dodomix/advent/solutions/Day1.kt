package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util

fun part1(input: List<String>): Int =
    input.map { it.toInt() }
        .map { it / 3 }
        .map { it - 2 }
        .sum()

fun calculateFuel(value: Int): Int =
    (value / 3 - 2)
        .takeIf { it > 0 }
        ?.let { it + calculateFuel(it) }
        ?: 0

fun part2(input: List<String>): Int = input
    .map { it.toInt() }
    .map { calculateFuel(it) }
    .sum()

fun main() {
    val day = "day1"
    println("Part 1 result: " + part1(Util.readFileLines("$day/input1")))
    println("Part 1 result: " + part2(Util.readFileLines("$day/input1")))
}
