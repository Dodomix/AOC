package hr.dodomix.advent.solutions

import kotlin.math.pow

class Day3 {
    fun dayDirectory() = "day3"

    fun part1(input: List<String>): Int {
        val counts = input.map { it.reversed().split("").filter { c -> c != "" } }
            .fold(mapOf<Pair<Int, String>, Int>()) { counts, number ->
                counts + number.mapIndexed { index, value ->
                    Pair(Pair(index, value), counts.getOrDefault(Pair(index, value), 0) + 1)
                }
            }
        return input[0].indices.sumOf { i ->
            if (counts.getOrDefault(Pair(i, "1"), 0) > counts.getOrDefault(Pair(i, "0"), 0)) {
                2.0.pow(i)
            } else {
                0.0
            }
        }.toInt() * input[0].indices.sumOf { i ->
            if (counts.getOrDefault(Pair(i, "1"), 0) < counts.getOrDefault(Pair(i, "0"), 0)) {
                2.0.pow(i)
            } else {
                0.0
            }
        }.toInt()
    }

    fun part2(input: List<String>): Int {
        return getValueWithCommonBits(input.map { it.split("").filter { c -> c != "" } }, 0, true)[0].reversed()
            .mapIndexed { i, c -> if (c == "1") 2.0.pow(i) else 0.0 }.sum().toInt() *
                getValueWithCommonBits(input.map { it.split("").filter { c -> c != "" } }, 0, false)[0].reversed()
                    .mapIndexed { i, c -> if (c == "1") 2.0.pow(i) else 0.0 }.sum().toInt()
    }

    private fun getValueWithCommonBits(input: List<List<String>>, currentBit: Int, mostCommon: Boolean = false): List<List<String>> {
        if (input.size == 1) {
            return input
        }

        return getValueWithCommonBits(if (input.count { it[currentBit] == "1" } >= input.count { it[currentBit] == "0" }) {
            if (mostCommon) {
                input.filter { it[currentBit] == "1" }
            } else {
                input.filter { it[currentBit] == "0" }
            }
        } else {
            if (mostCommon) {
                input.filter { it[currentBit] == "0" }
            } else {
                input.filter { it[currentBit] == "1" }
            }
        }, currentBit + 1, mostCommon)
    }
}
