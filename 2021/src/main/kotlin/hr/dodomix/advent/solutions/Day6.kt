package hr.dodomix.advent.solutions

import java.math.BigInteger
import java.math.BigInteger.ZERO

class Day6 {
    fun dayDirectory() = "day6"

    fun part1(input: List<String>): Int {
        val ages = input[0].split(",").map { it.toInt() }.toList()
        val ageMap = (0 until 9).associateWith { ages.count { age -> age == it } }
        return (0 until 80).fold(ageMap) { currentAgeMap, _ ->
            currentAgeMap.flatMap { ageEntry ->
                if (ageEntry.key == 0) {
                    listOf(Pair(6, ageEntry.value), Pair(8, ageEntry.value))
                } else {
                    listOf(Pair(ageEntry.key - 1, ageEntry.value))
                }
            }.fold(emptyMap()) { newAgeMap, ageEntry ->
                newAgeMap + Pair(ageEntry.first, newAgeMap.getOrDefault(ageEntry.first, 0) + ageEntry.second)
            }
        }.values.sum()
    }

    fun part2(input: List<String>): BigInteger {
        val ages = input[0].split(",").map { it.toInt() }.toList()
        val ageMap = (0 until 9).associateWith { ages.count { age -> age == it }.toBigInteger() }
        return (0 until 256).fold(ageMap) { currentAgeMap, _ ->
            currentAgeMap.flatMap { ageEntry ->
                if (ageEntry.key == 0) {
                    listOf(Pair(6, ageEntry.value), Pair(8, ageEntry.value))
                } else {
                    listOf(Pair(ageEntry.key - 1, ageEntry.value))
                }
            }.fold(emptyMap()) { newAgeMap, ageEntry ->
                newAgeMap + Pair(ageEntry.first, newAgeMap.getOrDefault(ageEntry.first, ZERO) + ageEntry.second)
            }
        }.values.sumOf { it }
    }
}
