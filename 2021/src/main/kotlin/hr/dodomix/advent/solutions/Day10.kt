package hr.dodomix.advent.solutions

import java.math.BigInteger
import java.math.BigInteger.ZERO
import java.math.BigInteger.valueOf

class Day10 {
    fun dayDirectory() = "day10"

    fun part1(input: List<String>): Int {
        return input.sumOf { findCorruptedCharacterScore(it) }
    }

    private fun findCorruptedCharacterScore(line: String): Int {
        line.fold(emptyList<Char>()) { stack, c ->
            if (c in CORRUPTED_CHARACTER_MAP.values.map { it.first }) {
                stack + c
            } else if (stack.isEmpty() || stack.last() != CORRUPTED_CHARACTER_MAP.getValue(c).first) {
                return CORRUPTED_CHARACTER_MAP.getValue(c).second
            } else {
                stack.dropLast(1)
            }
        }
        return 0
    }

    fun part2(input: List<String>): BigInteger {
        val results = input.map { findIncompleteCharacterScore(it) }.filter { it != INVALID_VALUE }.sorted()
        return results[(results.size - 1) / 2]
    }


    private fun findIncompleteCharacterScore(line: String): BigInteger {
        val endingStack = line.fold(emptyList<Char>()) { stack, c ->
            if (c in CORRUPTED_CHARACTER_MAP.values.map { it.first }) {
                stack + c
            } else if (stack.isEmpty() || stack.last() != CORRUPTED_CHARACTER_MAP.getValue(c).first) {
                return INVALID_VALUE
            } else {
                stack.dropLast(1)
            }
        }
        return endingStack.reversed().map { INCOMPLETE_CHARACTER_VALUE_MAP.getValue(it) }
            .fold(ZERO) { acc, value ->
                acc * MULTIPLICATOR + value
            }
    }

    companion object {
        private val INVALID_VALUE = valueOf(-1)
        private val MULTIPLICATOR = valueOf(5)

        private val CORRUPTED_CHARACTER_MAP = mapOf(
            Pair(')', Pair('(', 3)),
            Pair(']', Pair('[', 57)),
            Pair('}', Pair('{', 1197)),
            Pair('>', Pair('<', 25137))
        )

        private val INCOMPLETE_CHARACTER_VALUE_MAP = mapOf(
            Pair('(', valueOf(1)),
            Pair('[', valueOf(2)),
            Pair('{', valueOf(3)),
            Pair('<', valueOf(4))
        )
    }
}
