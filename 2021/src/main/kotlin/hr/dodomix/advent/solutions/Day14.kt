package hr.dodomix.advent.solutions

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO
import java.math.BigInteger.valueOf

class Day14 {
    fun dayDirectory() = "day14"

    fun part1(input: List<String>): Int {
        val (initialPolymer, insertionRules) = parseInput(input)
        val finalPolymer = (0 until 10).fold(initialPolymer) { polymer, _ ->
            polymer.zip(polymer.drop(1)).joinToString(separator = "") { (c1, c2) ->
                c1 + if (insertionRules.containsKey("$c1$c2")) {
                    insertionRules.getValue("$c1$c2")
                } else {
                    ""
                }
            } + polymer.last()
        }
        val charCounts = calculateOccurrences(finalPolymer).values.map { it.toInt() }
        return charCounts.maxOf { it } - charCounts.minOf { it }
    }

    fun part2(input: List<String>): BigInteger {
        val (initialPolymer, insertionRules) = parseInput(input)
        val initialPairs = initialPolymer.zip(initialPolymer.drop(1)).map { (c1, c2) -> "$c1$c2" }
            .fold(mapOf<String, BigInteger>()) { pairCount, pair ->
                pairCount + Pair(pair, pairCount.getOrDefault(pair, ZERO) + ONE)
            }
        val finalPairs = (0 until 40).fold(initialPairs) { pairs, _ ->
            pairs.flatMap { (pair, count) ->
                if (insertionRules.containsKey(pair)) {
                    val insertion = insertionRules.getValue(pair)
                    listOf(Pair(pair[0] + insertion, count), Pair(insertion + pair[1], count))
                } else {
                    listOf(Pair(pair, count))
                }
            }.fold(mapOf()) { newPairCounts, (pair, count) ->
                newPairCounts + Pair(pair, newPairCounts.getOrDefault(pair, ZERO) + count)
            }
        }
        val charCounts = finalPairs.entries.fold(mapOf<Char, BigInteger>()) { counts, (pair, count) ->
            if (pair[0] == pair[1]) {
                counts + Pair(pair[0], counts.getOrDefault(pair[0], ZERO) + count * valueOf(2))
            } else {
                counts + Pair(pair[0], counts.getOrDefault(pair[0], ZERO) + count) +
                        Pair(pair[1], counts.getOrDefault(pair[1], ZERO) + count)
            }
        }.values.map { it / valueOf(2) }
        return charCounts.maxOf { it } - charCounts.minOf { it } + ONE
    }

    private fun parseInput(input: List<String>) = input.fold(Pair("", mapOf<String, String>())) { (currentPolymer, currentInsertionRules), line ->
        if (line.isEmpty()) {
            Pair(currentPolymer, currentInsertionRules)
        } else if (line.contains("->")) {
            val (pair, insertion) = line.split(" -> ")
            Pair(currentPolymer, currentInsertionRules + Pair(pair, insertion))
        } else {
            Pair(line, currentInsertionRules)
        }
    }

    private fun calculateOccurrences(finalPolymer: String) = finalPolymer.fold(mapOf<Char, BigInteger>()) { counts, c ->
        counts + Pair(c, counts.getOrDefault(c, ZERO) + ONE)
    }
}
