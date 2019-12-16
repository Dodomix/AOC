package hr.dodomix.advent.solutions

import kotlin.math.abs

class Day16 {

    fun dayDirectory() = "day16"

    fun part1(input: List<String>): Int {
        val digits = input[0].split("").filter { it.isNotEmpty() }.map { it.toInt() }
        val pattern = listOf(1, 0, -1, 0)
        return (0 until 100).fold(digits) { currentDigits, _ ->
            currentDigits.mapIndexed { i, _ ->
                var newValue = 0
                for (j in currentDigits.indices) {
                    if (j >= i) {
                        val patternValue = pattern[((j - i) / (i + 1)) % 4]
                        newValue += patternValue * currentDigits[j]
                    }
                }
                abs(newValue % 10)
            }
        }.take(8).joinToString(separator = "") { it.toString() }.toInt()
    }

    fun part2(input: List<String>): Int {
        val digits = input[0].split("").filter { it.isNotEmpty() }.map { it.toLong() }
        val fullDigits = (0 until 10000).fold(emptyList<Long>()) { acc, _ ->
            acc + digits
        }
        val offset = digits.take(7).fold("") { acc, digit -> acc + digit }.toInt()
        return (0 until 100).fold(fullDigits.drop(offset)) { currentDigits, _ ->
            var sum = currentDigits.sum()
            currentDigits.map { value ->
                val result = abs(sum % 10)
                sum -= value
                result
            }
        }.take(8).joinToString(separator = "") { it.toString() }.toInt()
    }
}
